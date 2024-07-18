package com.sparta.audumbla.audumblaworldjpa.controllers;

import com.sparta.audumbla.audumblaworldjpa.entities.Countrylanguage;
import com.sparta.audumbla.audumblaworldjpa.entities.CountrylanguageId;
import com.sparta.audumbla.audumblaworldjpa.exceptions.AlreadyExistsException;
import com.sparta.audumbla.audumblaworldjpa.exceptions.DataMismatchException;
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

    private CollectionModel<EntityModel<Countrylanguage>> languagesMapping(List<Countrylanguage> worldService) {
        List<EntityModel<Countrylanguage>> languages = worldService
                .stream()
                .map(this::languageMapping)
                .collect(Collectors.toList());
        return CollectionModel.of(languages);
    }

    @GetMapping
    public CollectionModel<EntityModel<Countrylanguage>> getAllLanguages() {
        return languagesMapping(worldService.getAllCountryLanguages())
                .add(WebMvcLinkBuilder.linkTo(methodOn(LanguageController.class).getAllLanguages()).withSelfRel());
    }

    @GetMapping("/{countryCode}")
    public CollectionModel<EntityModel<Countrylanguage>> getLanguagesByCountry(@PathVariable String countryCode) {
        List<Countrylanguage> languages = worldService.getCountryLanguagesByCountryCode(countryCode);
        if(languages.isEmpty()){
            throw new ResourceNotFoundException("No languages of country"+countryCode+"Exist");
        }
        return languagesMapping(languages)
                .add(WebMvcLinkBuilder.linkTo(methodOn(LanguageController.class).getLanguagesByCountry(countryCode)).withSelfRel());
    }

    @GetMapping("/{countryCode}/{language}")
    public ResponseEntity<EntityModel<Countrylanguage>> getLanguageByKey(@PathVariable String countryCode, @PathVariable String language) {
        Optional<Countrylanguage> countryLanguage = worldService.getLanguageByCodeAndLanguage(countryCode,language);
        if(countryLanguage.isEmpty()){
            throw new ResourceNotFoundException("No languages of key"+countryCode+","+language+"Exist");
        }
        return countryLanguage
                .map(this::languageMapping)
                .map(l -> new ResponseEntity<>(l,HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Countrylanguage>> addLanguage(@RequestBody @Valid Countrylanguage language, HttpServletRequest request){
        language.setCountryCode(worldService.getCountryByCountryCode(language.getId().getCountryCode())
                .orElseThrow(() -> new ResourceNotFoundException("No country of countryCode"+language.getId().getCountryCode()+"exists")));
        if(worldService.getLanguageByCodeAndLanguage(language.getId().getCountryCode(),language.getId().getLanguage()).isPresent()){
            throw new AlreadyExistsException("Country with key of"+language.getId().toString()+"already exists");
        }
        worldService.createCountryLanguage(language);
        URI location = URI.create(request.getRequestURL().toString()+"/"+language.getId().getCountryCode()+"/"+language.getId().getLanguage());
        return ResponseEntity.created(location).body(languageMapping(language));
    }

    @DeleteMapping("/{countryCode}/{language}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable String countryCode,@PathVariable String language){
        Optional<Countrylanguage> foundLanguage = worldService.getLanguageByCodeAndLanguage(countryCode,language);
        if(foundLanguage.isEmpty()){
            throw new ResourceNotFoundException("No languages of key"+countryCode+","+language+"exist to delete");
        } else{
            CountrylanguageId deleteKey = new CountrylanguageId();
            deleteKey.setLanguage(language);
            deleteKey.setCountryCode(countryCode);
            worldService.deleteCountryLanguageById(deleteKey);
            return ResponseEntity.ok(null);
        }
    }

    @PutMapping("/{countryCode}/{language}")
    public ResponseEntity<EntityModel<Countrylanguage>> updateLanguage(@PathVariable String countryCode,@PathVariable String language, @RequestBody Countrylanguage countrylanguage){
        if(!countryCode.equals(countrylanguage.getId().getCountryCode()) || !language.equals(countrylanguage.getId().getLanguage())){
            throw new DataMismatchException("The country codes and languages of your id and body do not match");
        }
        Optional<Countrylanguage> foundLanguage = worldService.getLanguageByCodeAndLanguage(countryCode,language);
        if(foundLanguage.isEmpty()){
            worldService.createCountryLanguage(countrylanguage);
            return ResponseEntity.ok(languageMapping(countrylanguage));
        }
        worldService.updateCountryLanguageTable(countrylanguage);

        return ResponseEntity.ok(languageMapping(countrylanguage));
    }

}
