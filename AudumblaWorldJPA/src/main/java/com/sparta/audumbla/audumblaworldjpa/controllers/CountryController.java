package com.sparta.audumbla.audumblaworldjpa.controllers;

import com.sparta.audumbla.audumblaworldjpa.entities.*;
import com.sparta.audumbla.audumblaworldjpa.repositories.CityRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryLanguageRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryRepository;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    WorldService worldService;
    @Autowired
    private CountryRepository countryRepository;


    @GetMapping
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }
    @GetMapping("/{countryCode}")
    public ResponseEntity<EntityModel<Country>> getCountriesByCountryCode(@PathVariable String countryCode) {

        Optional<Country> country = worldService.getCountryByCountryCode(countryCode);
        if (country.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

//        List<Link> citiesLinks = country.get().getCities().stream()
//                .map(city -> WebMvcLinkBuilder.linkTo(
//                                methodOn(CityController.class).getCitiesById(city.getId()).orElseThrow().get)
//                        .withRel(city.getName()))
//                .toList();
//        List<Link> languagesLinks = country.get().getLanguages().stream()
//                .map(language -> WebMvcLinkBuilder.linkTo(
//                        methodOn(LanguageController.class).getCountryLanguageByKey(countryCode,language.getId().getLanguage()))
//                        .withRel(language.getId().getLanguage()))
//                .toList();
        Link selfLink = WebMvcLinkBuilder.linkTo(
                methodOn(CountryController.class).getCountriesByCountryCode(country.get().getCode())).withSelfRel();
        Link relLink = WebMvcLinkBuilder.linkTo(
                methodOn(CountryController.class).getCountries()).withRel("Countries");
        return new ResponseEntity<>(EntityModel.of(country.get(), selfLink, relLink), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<EntityModel<Country>> createCountry(@RequestBody @Valid Country country, HttpServletRequest request) {
        //does exist?
        if (worldService.getCountryByCountryCode(country.getCode()).isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        worldService.createCountry(country);
        URI location = URI.create(request.getRequestURL().toString()+"/"+country.getCode());
        return ResponseEntity.created(location).body(EntityModel.of(country));
    }
//    @PutMapping("/{countryCode}")
//    public ResponseEntity<EntityModel<Country>> updateCountry(@PathVariable String countryCode, @RequestBody @Valid Country country) {
//
//    }

}
