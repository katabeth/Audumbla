package com.sparta.audumbla.audumblaworldjpa.controllers;

import com.sparta.audumbla.audumblaworldjpa.entities.*;
import com.sparta.audumbla.audumblaworldjpa.exceptions.AlreadyExistsException;
import com.sparta.audumbla.audumblaworldjpa.exceptions.DataMismatchException;
import com.sparta.audumbla.audumblaworldjpa.exceptions.HasDependantsException;
import com.sparta.audumbla.audumblaworldjpa.exceptions.ResourceNotFoundException;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    final WorldService worldService;

    @Autowired
    public CountryController(WorldService worldService) {
        this.worldService = worldService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Country>> getAllCountries() {
        List<Country> countries = worldService.getAllCountries();
        // Link to the country
        // Link to the cities of said country
        // Link to the languages of said country
        List<EntityModel<Country>> countryModels = countries.stream()
                .map(country -> EntityModel.of(country,
                        WebMvcLinkBuilder.linkTo(
                                methodOn(CountryController.class).getCountryByCountryCode(country.getCode()))
                                .withSelfRel(),
//                        WebMvcLinkBuilder.linkTo(
//                                methodOn(CityController.class).getCitiesByCountry(country.getCode()))
//                                .withRel("cities"),
                        WebMvcLinkBuilder.linkTo(methodOn(LanguageController.class)
                                        .getLanguagesByCountry(country.getCode()))
                                .withRel("languages")


                ))
                .toList();

        return CollectionModel.of(countryModels,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CountryController.class).getAllCountries()).withSelfRel());
    }

    @GetMapping("/{countryCode}")
    public ResponseEntity<EntityModel<Country>> getCountryByCountryCode(@PathVariable String countryCode) {

        Optional<Country> country = worldService.getCountryByCountryCode(countryCode);
        if (country.isEmpty()){
            throw new ResourceNotFoundException("Country with code " + countryCode + " does not exist");
        }

        List<Link> citiesLinks = country.get().getCities().stream()
                .map(city -> WebMvcLinkBuilder.linkTo(
                                methodOn(CityController.class).getCityById(city.getId()))
                        .withRel(city.getName()))
                .toList();
        List<Link> languagesLinks = country.get().getLanguages().stream()
                .map(language -> WebMvcLinkBuilder.linkTo(
                        methodOn(LanguageController.class).getLanguageByKey(countryCode,language.getId().getLanguage()))
                        .withRel(language.getId().getLanguage()))
                .toList();
        Link selfLink = WebMvcLinkBuilder.linkTo(
                methodOn(CountryController.class).getCountryByCountryCode(country.get().getCode())).withSelfRel();
        Link relLink = WebMvcLinkBuilder.linkTo(
                methodOn(CountryController.class).getAllCountries()).withRel("Countries");
        return new ResponseEntity<>(EntityModel.of(country.get(), selfLink, relLink).add(citiesLinks).add(languagesLinks), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Country>> createCountry(@RequestBody @Valid Country country, HttpServletRequest request) {
        if (worldService.getCountryByCountryCode(country.getCode()).isPresent()){
            throw new AlreadyExistsException("Country with code " + country.getCode() + " already exists");
        }
        worldService.createCountry(country);
        URI location = URI.create(request.getRequestURL().toString()+"/"+country.getCode());
        return ResponseEntity.created(location).body(EntityModel.of(country));
    }

    @PutMapping("/{countryCode}")
    public ResponseEntity<Void> updateCountry(@PathVariable String countryCode, @RequestBody @Valid Country country) {

        if(!countryCode.equalsIgnoreCase(country.getCode())){
            throw new DataMismatchException("countryCode does not match countryCode in path");
        } else if (worldService.getCountryByCountryCode(countryCode).isEmpty()){
            worldService.createCountry(country);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        worldService.updateCountryTable(country.getCode(),country);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{countryCode}")
    public ResponseEntity<Void> deleteCountry(@PathVariable String countryCode) {

        if (worldService.getCountryByCountryCode(countryCode).isEmpty()){
            throw new ResourceNotFoundException("Country with code "+countryCode+" does not exist to be deleted");
        } else if (!worldService.getCitiesByCountryCode(countryCode).isEmpty()){
            throw new HasDependantsException("The country " + countryCode +" cannot be deleted because cities depend on it");
        } else if (!worldService.getCountryLanguagesByCountryCode(countryCode).isEmpty()){
            worldService.deleteCountryLanguagesByCountryCode(countryCode);
        }
        worldService.deleteCountryByCountryCode(countryCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
