package com.sparta.audumbla.audumblaworldjpa.mvccontrollers;

import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.exceptions.ResourceNotFoundException;
import com.sparta.audumbla.audumblaworldjpa.repositories.CityRepository;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public CityMvcController(WorldService worldService) {
        this.worldService = worldService;
    }

    @GetMapping()
    public String getCities(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "100") int size) {
        Page<City> cityPage = worldService.getCitiesPage(page, size);
        model.addAttribute("cityPage", cityPage);
        return "cities/cityView";
    }

    @GetMapping("/search")
    public String searchCityByCityName(@RequestParam("name") String name, Model model,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "100") int size) {
        Page<City> cityPage = worldService.getCitiesByPartialNamePage(name, page, size);
        model.addAttribute("cityPage", cityPage);
        return "cities/cityView";
    }

    @GetMapping("/edit/{id}")
    public String editCity(@PathVariable int id, Model model) {
        Optional<City> city = worldService.getCitiesByID(id);
        if (city.isPresent()) {
            model.addAttribute("cityToEdit", city.orElseThrow());
        }
        model.addAttribute("countries", worldService.getAllCountries());
        return "cities/updateCity";
    }

    @PostMapping("/update")
    public String updateCity(@ModelAttribute City city, @RequestParam("code") String code, Model model) {

        city.setCountryCode(worldService.getCountryByCountryCode(code).orElseThrow());
        worldService.updateCityTable(city.getId(), city);

        List<City> cityList = worldService.getAllCities();
        model.addAttribute("cityList", cityList);

        return "redirect:/cities";
    }

    @PostMapping("/delete/{id}")
    public String deleteCountry(@PathVariable int id) {
        Optional<City> city = worldService.getCitiesByID(id);
        if (city.isEmpty()) {
            throw new ResourceNotFoundException("Country with id " + id + " not found");
        } else {
            worldService.deleteCityById(id);
        }
        return "redirect:/cities";
    }

    @GetMapping("/add")
    public String showAddCityForm(Model model, String code) {
        model.addAttribute("newCity", new City());
        model.addAttribute("countries", worldService.getAllCountries());
        model.addAttribute("code", code);
        return "cities/addCity";
    }

    @PostMapping("/add")
    public String addCity(@ModelAttribute City city, @RequestParam("code") String code) {

        city.setCountryCode(worldService.getCountryByCountryCode(code).orElseThrow());
        worldService.createCity(city);
        return "redirect:/cities";
    }

}
