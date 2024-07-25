package com.sparta.audumbla.audumblaworldjpa.mvccontrollers;

import com.sparta.audumbla.audumblaworldjpa.entities.Country;
import com.sparta.audumbla.audumblaworldjpa.entities.Countrylanguage;
import com.sparta.audumbla.audumblaworldjpa.entities.CountrylanguageId;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/languages")
public class LanguageMvcController {

    private final WorldService worldService;

    @Autowired
    public LanguageMvcController(WorldService worldService) {
        this.worldService = worldService;
    }

    @GetMapping
    public String getLanguages(Model model) {
        model.addAttribute("languages", worldService.getAllCountryLanguages());
        return "languages/list";
    }

    @PostMapping("/delete/{countryCode}/{languageText}")
    public String deleteLanguage(@PathVariable String countryCode, @PathVariable String languageText) {
        CountrylanguageId id = new CountrylanguageId();
        id.setCountryCode(countryCode);
        id.setLanguage(languageText);
        worldService.deleteCountryLanguageById(id);
        return "redirect:/languages";
    }

    @GetMapping("/create")
    public String getCreateLanguageForm(Model model) {
        Countrylanguage countryLanguageForModel = new Countrylanguage();
        countryLanguageForModel.setId(new CountrylanguageId());
        model.addAttribute("language", countryLanguageForModel);
        model.addAttribute("countryCodes", worldService.getAllCountries().stream().map(Country::getCode).toList());
        return "languages/create";
    }

    @PostMapping("/create")
    public String createLanguage(Countrylanguage language, @RequestParam("code") String code, @RequestParam("language") String languageText) {
        language.setCountryCode(worldService.getCountryByCountryCode(code).orElseThrow());
        CountrylanguageId id = new CountrylanguageId();
        id.setLanguage(languageText);
        id.setCountryCode(code);
        language.setId(id);
        if(language.getIsOfficial() == null) {
            language.setIsOfficial("F");
        }
        worldService.createCountryLanguage(language);
        return "redirect:/languages";
    }

    @GetMapping("/update/{countryCode}/{languageText}")
    public String getUpdateLanguageForm(@PathVariable String countryCode, @PathVariable String languageText, Model model) {
        model.addAttribute("language", worldService.getLanguageByCodeAndLanguage(countryCode,languageText).orElseThrow());
        model.addAttribute("countryCodes", worldService.getAllCountries().stream().map(Country::getCode).toList());
        return "languages/update";
    }

    @PostMapping("/update/{codeOfCountry}/{languageText}")
    public String updateLanguage(Countrylanguage language,@PathVariable String codeOfCountry, @PathVariable String languageText) {
        CountrylanguageId id = new CountrylanguageId();
        id.setCountryCode(codeOfCountry);
        id.setLanguage(languageText);
        if(language.getIsOfficial() == null) {
            language.setIsOfficial("F");
        }
        language.setId(id);
        worldService.updateCountryLanguageTable(language);
        return "redirect:/languages";
    }



}
