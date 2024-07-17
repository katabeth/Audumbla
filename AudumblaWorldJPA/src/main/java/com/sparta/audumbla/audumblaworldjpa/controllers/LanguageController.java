package com.sparta.audumbla.audumblaworldjpa.controllers;

import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.entities.Countrylanguage;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryRepository;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final WorldService worldService;

    @Autowired
    public LanguageController(WorldService worldService) {
        this.worldService = worldService;
    }


    private EntityModel<Countrylanguage> languageMapping(Countrylanguage language){
        {
            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(LanguageController.class).getLanguageByKey(language.getId().getCountryCode(),language.getId().getLanguage())).withSelfRel();
            //todo : add support for countryController code once it can be done
            Link relLink =  WebMvcLinkBuilder.linkTo(methodOn(LanguageController.class).getAllLanguages()).withSelfRel();//WebMvcLinkBuilder.linkTo(methodOn(CountryController.class).getCountry(language.getCountryCode())).withSelfRel().withRel(language.getCountryCode().getName());

            return EntityModel.of(language, selfLink, relLink);
        }
    }

    @GetMapping
    public CollectionModel<EntityModel<Countrylanguage>> getAllLanguages() {
        List<EntityModel<Countrylanguage>> languages = worldService.getAllCountryLanguages()
                .stream()
                .map(this::languageMapping)
                .collect(Collectors.toList());
        return CollectionModel.of(languages,
                WebMvcLinkBuilder.linkTo(methodOn(LanguageController.class).getAllLanguages()).withSelfRel());
    }

    @GetMapping("/{countryCode}/{language}")
    public ResponseEntity<EntityModel<Countrylanguage>> getLanguageByKey(@PathVariable String countryCode, @PathVariable String language) {
        Optional<Countrylanguage> countryLanguage = worldService.getLanguageByCodeAndLanguage(countryCode,language);
        return countryLanguage
                .map(this::languageMapping)
                .map(l -> new ResponseEntity<>(l,HttpStatus.OK))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<Countrylanguage> addLanguage(@RequestBody @Valid Countrylanguage language, HttpServletRequest request){
        System.out.println(language);
        worldService.createCountryLanguage(language);
        URI location = URI.create(request.getRequestURL().toString()+"/"+language.getCountryCode()+"/"+language.getId());
        return ResponseEntity.created(location).body(language);
    }

}