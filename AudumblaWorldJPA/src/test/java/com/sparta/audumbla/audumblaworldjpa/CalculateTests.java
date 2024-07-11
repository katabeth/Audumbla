package com.sparta.audumbla.audumblaworldjpa;

import com.sparta.audumbla.audumblaworldjpa.service.WorldService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
                "s the largest city");
    }

    @Test
    public void testFindCountryWithMostCities() {
        System.out.println(
                "The country with the most cities in the database is: "
                + worldService.findCountryWithMostCities().getKey().NameToString() +
                "\n And there are : " + worldService.findCountryWithMostCities().getValue() +
                " cities");
    }

}
