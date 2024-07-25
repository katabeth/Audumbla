package com.sparta.audumbla.audumblaworldjpa.mvccontrollers;

import com.sparta.audumbla.audumblaworldjpa.entities.Country;
import com.sparta.audumbla.audumblaworldjpa.exceptions.DataMismatchException;
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
}
