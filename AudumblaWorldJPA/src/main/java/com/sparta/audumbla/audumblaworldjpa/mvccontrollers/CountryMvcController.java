package com.sparta.audumbla.audumblaworldjpa.mvccontrollers;

import com.sparta.audumbla.audumblaworldjpa.entities.Country;
import com.sparta.audumbla.audumblaworldjpa.exceptions.DataMismatchException;
import com.sparta.audumbla.audumblaworldjpa.exceptions.HasDependantsException;
import com.sparta.audumbla.audumblaworldjpa.exceptions.ResourceNotFoundException;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/countries")
public class CountryMvcController {
    private final WorldService worldService;

    @Autowired
    public CountryMvcController(WorldService worldService) {
        this.worldService = worldService;

    }

    @GetMapping
    public String getCountries(@RequestParam(required = false, defaultValue = "") String search, Model model) {
        if (!search.isEmpty()) {
            model.addAttribute("countries", worldService.getCountryByCountryCode(search));
            model.addAttribute("message", "Showing results for: " + search);
        } else {
            model.addAttribute("countries", worldService.getAllCountries());
            model.addAttribute("message", "Showing All Countries");
        }
        return "countries/list";
    }

    @GetMapping("/create")
    public String createCountry(Model model) {
        model.addAttribute("country", new Country());
        return "countries/create";
    }

    @PostMapping("/create")
    public String createCountry(@Valid @ModelAttribute("country") Country country, Errors errors) {
        if (errors.hasErrors()) {
            throw new DataMismatchException("Invalid country: " + errors);
        } else {
            worldService.createCountry(country);
            return "redirect:/countries?search=" + country.getCode();
        }
    }
    @PostMapping("/delete/{countryCode}")
    public String deleteCountry(@PathVariable String countryCode) {
        if (!worldService.getCitiesByCountryCode(countryCode).isEmpty()){
            throw new HasDependantsException("The country " + countryCode +" cannot be deleted because cities depend on it");
        } else if (!worldService.getCountryLanguagesByCountryCode(countryCode).isEmpty()){
            worldService.deleteCountryLanguagesByCountryCode(countryCode);
        }
        worldService.deleteCountryByCountryCode(countryCode);
        return "redirect:/countries";
    }
    @GetMapping("/update")
    public String updateCountry(@RequestParam() String currentCountry, Model model) {
        model.addAttribute("countryToEdit", worldService.getCountryByCountryCode(currentCountry)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found: " + currentCountry)));
        model.addAttribute("continents", worldService.getAllContinents());
        return "countries/update";
    }
    @PostMapping("/update")
    public String updateCountry(@Valid @ModelAttribute("countryToEdit") Country country, Errors errors) {
//        country.setCode();
        if (errors.hasErrors()) {
            throw new DataMismatchException("Invalid country: " + errors);
        } else {
            worldService.updateCountryTable(country.getCode(),country);
            return "redirect:/countries?search=" + country.getCode();
        }

    }
}
