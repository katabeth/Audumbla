package com.sparta.audumbla.audumblaworldjpa;

import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.entities.Country;

public class testUtils {

    public static City createCity(Integer id, String name, String district, Integer population) {
        City city = new City();
        city.setId(id);
        city.setName(name);
        city.setDistrict(district);
        city.setPopulation(population);
        return city;
    }
}
