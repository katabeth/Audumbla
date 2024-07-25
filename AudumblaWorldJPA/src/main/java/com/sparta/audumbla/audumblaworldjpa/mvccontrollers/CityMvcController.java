package com.sparta.audumbla.audumblaworldjpa.mvccontrollers;

import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.repositories.CityRepository;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cities")
public class CityMvcController {
    private final WorldService worldService;

    @Autowired
    public CityMvcController(CityRepository cityRepository, WorldService worldService) {
        this.worldService = worldService;
    }

    @GetMapping()
    public String getCities(Model model) {
        List<City> cityList = worldService.getAllCities();
        model.addAttribute("cityList", cityList);
        return "fragments/cityView";
    }

    @GetMapping("/search")
    public String searchCityByCityName(@RequestParam("name") String name, Model model) {
        List<City> city = worldService.getCitiesByPartialName(name);
        model.addAttribute("cityList", city);
        return "fragments/cityView";
    }


}
