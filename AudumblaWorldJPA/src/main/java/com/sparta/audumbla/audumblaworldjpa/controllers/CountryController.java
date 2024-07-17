package com.sparta.audumbla.audumblaworldjpa.controllers;

import com.sparta.audumbla.audumblaworldjpa.entities.*;
import com.sparta.audumbla.audumblaworldjpa.repositories.CityRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryLanguageRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryRepository;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final CountryLanguageRepository countryLanguageRepository;
    private final WorldService worldService;

    public CountryController(CityRepository cityRepository, CountryRepository countryRepository, CountryLanguageRepository countryLanguageRepository, WorldService worldService) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.countryLanguageRepository = countryLanguageRepository;
        this.worldService = worldService;
    }

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
        // Get cities by country
        // Get Languages by country
        List<Link> citiesLinks = country.get().getCities().stream()
                .map(city -> WebMvcLinkBuilder.linkTo(
                                methodOn(CityController.class).getCitiesByID(city.getId()).orElseThrow().get)
                        .withRel(city.getName()))
                .toList();
        List<Link> languagesLinks = country.get().getLanguages().stream()
                .map(language -> WebMvcLinkBuilder.linkTo(
                        methodOn(LanguageController.class).getCountryLanguageByKey(countryCode,language.getId().getLanguage()))
                        .withRel(language.getId().getLanguage()))
                .toList();
        Link selfLink = WebMvcLinkBuilder.linkTo(
                methodOn(CountryController.class).getCountriesByCountryCode(country.get().getCode())).withSelfRel();
        Link relLink = WebMvcLinkBuilder.linkTo(
                methodOn(CountryController.class).getCountries()).withRel("Countries");
        return new ResponseEntity<>(EntityModel.of(country.get(), selfLink, relLink).add(citiesLinks), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<EntityModel<Country>> createCountry(@RequestBody @Valid Country country, HttpServletResponse response) {
        
    }

}
