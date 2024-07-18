package com.sparta.audumbla.audumblaworldjpa.controllers;

import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryRepository;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    //hateoas
    private final WorldService worldService;

    public CityController(WorldService worldService, CountryRepository countryRepository) {
        this.worldService = worldService;
    }

    @GetMapping
    public CollectionModel<EntityModel<City>> getAllCities() {
        List<City> cities = worldService.getAllCities();

        List<EntityModel<City>> cityModels = cities.stream()
                .map(city -> EntityModel.of(city,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                                .methodOn(CityController.class)
                                .getCityById(city.getId()))
                                .withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                                .methodOn(CityController.class)
                                .getAllCities())
                                .withRel("cities"),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                                .methodOn(CountryController.class)
                                .getCountryByCountryCode(city.getCountryCode().getCode()))
                                .withRel("country")))
                .collect(Collectors.toList());

        return CollectionModel.of(cityModels,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CityController.class).getAllCities()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Optional<City>> getCityById(@PathVariable Integer id) {
        Optional<City> city = worldService.getCitiesByID(id);
        return EntityModel.of(city,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(CityController.class)
                        .getCityById(id))
                        .withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(CityController.class)
                        .getAllCities())
                        .withRel("cities"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(CountryController.class)
                        .getCountryByCountryCode(city.orElseThrow().getCountryCode().getCode()))
                        .withRel("country"));
    }

    @PostMapping
    public EntityModel<City> addCities(@RequestBody City city) {
        if(city.getCountryCode().toString().isEmpty()) {
            worldService.createCountry(city.getCountryCode());
        }
        worldService.createCity(city);
        return EntityModel.of(city,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(CityController.class)
                        .getCityById(city.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(CityController.class)
                        .getAllCities()).withRel("cities"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(CountryController.class)
                        .getCountryByCountryCode(city.getCountryCode().getCode()))
                        .withRel("country"));
    }

    @PutMapping("/{id}")
    public EntityModel<City> putCity(@PathVariable Integer id, @RequestBody City city) {

        if (worldService.getCitiesByID(id).isEmpty()){
            return addCities(city);
        }
        worldService.updateCityTable(city.getId(), city);
        return EntityModel.of(city,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(CityController.class)
                        .getCityById(city.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(CityController.class)
                        .getAllCities()).withRel("cities"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                        .methodOn(CountryController.class)
                        .getCountryByCountryCode(city.getCountryCode().getCode()))
                        .withRel("country"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Integer id) {
        if (worldService.getCitiesByID(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        worldService.deleteCityById(id);
        return ResponseEntity.noContent().build();
    }
}
