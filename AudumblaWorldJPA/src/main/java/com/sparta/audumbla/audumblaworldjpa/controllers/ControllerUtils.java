package com.sparta.audumbla.audumblaworldjpa.controllers;

import com.sparta.audumbla.audumblaworldjpa.entities.Countrylanguage;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ControllerUtils {

    static public EntityModel<Countrylanguage> languageMapping(Countrylanguage language){
        {
            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(LanguageController.class).getLanguageByKey(language.getId().getCountryCode(),language.getId().getLanguage())).withSelfRel();
            Link relLink =  WebMvcLinkBuilder.linkTo(methodOn(CountryController.class).getCountryByCountryCode(language.getCountryCode().getCode())).withRel("country");

            return EntityModel.of(language, selfLink, relLink);
        }
    }

    static public CollectionModel<EntityModel<Countrylanguage>> languagesMapping(List<Countrylanguage> worldService) {
        List<EntityModel<Countrylanguage>> languages = worldService
                .stream()
                .map(ControllerUtils::languageMapping)
                .collect(Collectors.toList());
        return CollectionModel.of(languages);
    }
}
