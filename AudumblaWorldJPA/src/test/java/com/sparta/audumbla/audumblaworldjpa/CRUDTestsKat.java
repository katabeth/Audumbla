package com.sparta.audumbla.audumblaworldjpa;

import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.repositories.CityRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryLanguageRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryRepository;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CRUDTestsKat {

    @Autowired
    WorldService worldService;
    @Autowired
    private CityRepository cityRepository;

    @Test
    @DisplayName("Test I can Search cities by name")
    void testSearchCitiesByName() {
        List<City> test = worldService.getCitiesByName("London");
        Assertions.assertEquals(2, test.size());
    }
    @Test
    @DisplayName("Test I can Search cities by ID")
    void testSearchCitiesById() {

        String test = worldService.getCitiesByID(2912).orElseThrow().getName();
        Assertions.assertEquals("Adamstown", test);
    }
    @Test
    @DisplayName("Test I can Search cities by District")
    void testSearchCitiesByDistrict(){
        List<City> test = worldService.getCitiesByDistrict("British Colombia");
        Assertions.assertEquals(9, test.size());
    }
}
