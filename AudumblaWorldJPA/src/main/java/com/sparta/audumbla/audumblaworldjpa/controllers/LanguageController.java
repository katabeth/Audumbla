package com.sparta.audumbla.audumblaworldjpa.controllers;

import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.entities.Countrylanguage;
import com.sparta.audumbla.audumblaworldjpa.entities.CountrylanguageId;
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
            Link relLink =  WebMvcLinkBuilder.linkTo(methodOn(CountryController.class).getCountryByCountryCode(language.getCountryCode().getCode())).withRel("country");

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

    //todo : add better error handling
    @GetMapping("/{countryCode}/{language}")
    public ResponseEntity<EntityModel<Countrylanguage>> getLanguageByKey(@PathVariable String countryCode, @PathVariable String language) {
        Optional<Countrylanguage> countryLanguage = worldService.getLanguageByCodeAndLanguage(countryCode,language);
        return countryLanguage
                .map(this::languageMapping)
                .map(l -> new ResponseEntity<>(l,HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    //todo : add hateoas
    //todo: add check to make sure the country language doesn't already exist
    @PostMapping
    public ResponseEntity<Countrylanguage> addLanguage(@RequestBody @Valid Countrylanguage language, HttpServletRequest request){
        language.setCountryCode(worldService.getCountryByCountryCode(language.getId().getCountryCode()).orElseThrow());
        worldService.createCountryLanguage(language);
        URI location = URI.create(request.getRequestURL().toString()+"/"+language.getId().getCountryCode()+"/"+language.getId().getLanguage());
        return ResponseEntity.created(location).body(language);
    }

    //todo : add better error handling
    @DeleteMapping("/{countryCode}/{language}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable String countryCode,@PathVariable String language){
        Optional<Countrylanguage> foundLanguage = worldService.getLanguageByCodeAndLanguage(countryCode,language);
        if(foundLanguage.isEmpty()){
            return ResponseEntity.notFound().build();
        } else{
            CountrylanguageId deleteKey = new CountrylanguageId();
            deleteKey.setLanguage(language);
            deleteKey.setCountryCode(countryCode);
            worldService.deleteCountryLanguageById(deleteKey);
            return ResponseEntity.ok(null);
        }
    }

    //todo : add hateoas
    @PutMapping("/{countryCode}/{language}")
    public ResponseEntity<Countrylanguage> updateLanguage(@PathVariable String countryCode,@PathVariable String language, @RequestBody Countrylanguage countrylanguage){
        if(!countryCode.equals(countrylanguage.getId().getCountryCode()) || !language.equals(countrylanguage.getId().getLanguage())){
            return ResponseEntity.badRequest().body(null);
        }
        Optional<Countrylanguage> foundLanguage = worldService.getLanguageByCodeAndLanguage(countryCode,language);
        if(foundLanguage.isEmpty()){
            worldService.createCountryLanguage(countrylanguage);
            return ResponseEntity.ok(countrylanguage);
        }
        worldService.updateCountryLanguageTable(language,countryCode,countrylanguage);

        return ResponseEntity.ok(countrylanguage);
    }

}