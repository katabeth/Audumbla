package com.sparta.audumbla.audumblaworldjpa;

import com.sparta.audumbla.audumblaworldjpa.entities.Country;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculateTests {

    @Autowired
    private WorldService worldService;

    @Test
    public void testCalculatePopulationPercentageInLargestCityByCountryName(){
        String countryName = "Slovakia";
        System.out.println(
                worldService.calculatePopulationPercentageInLargestCityByCountryName(countryName) +
                "% of the population lives in " + countryName +
                "'s largest city");
    }

    @Test
    public void testFindCountryWithMostCities() {
        System.out.println(
                "The country with the most cities in the database is : "
                + worldService.findCountryWithMostCities().getKey().nameToString() +
                "\nAnd there are : " + worldService.findCountryWithMostCities().getValue() +
                " cities");
    }

    @Test
    void testGetCountriesWithoutHeadOfState() {
        List<Country> result = worldService.getCountriesWithoutHeadOfState();
        assertEquals(3, result.size());
    }

}
