package com.sparta.audumbla.audumblaworldjpa;


import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.entities.Country;
import com.sparta.audumbla.audumblaworldjpa.entities.Countrylanguage;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
public class DemonstrationTest extends WorldServiceTest {

    @Disabled
    @Test
    @DisplayName("Getting all Countries from The database")
    void getAllCountriesDemonstration() {
        List<Country> countries = worldService.getAllCountries();
        for(Country country : countries){
            System.out.println(country);
        }
        System.out.println("found " + countries.size() + " Countries");
    }

    @Disabled
    @Transactional
    @Test
    @DisplayName("Getting all Cities from The database")
    void getAllCitiesDemonstration() {
        List<City> cities = worldService.getAllCities();
        for(City city : cities){
            System.out.println(city);
        }
        System.out.println("found " + cities.size() + " Cities");
    }


    @Disabled
    @Test
    @DisplayName("Getting all CountryLanguages from The database")
    void getAllCountriesLanguagesDemonstration() {
        List<Countrylanguage> languages = worldService.getAllCountryLanguages();
        for(Countrylanguage language : languages){
            System.out.println(language);
        }
        System.out.println("found " + languages.size() + " Languages");
    }

    @Test
    @Order(1)
    @DisplayName("Adding NewLand")
    void addingNewCountryDemonstration(){
        Country newLand = new Country();
        newLand.setCode("NLE");
        newLand.setName("NewLand");
        newLand.setContinent("Oceania");
        newLand.setRegion("Test Region");
        newLand.setSurfaceArea(BigDecimal.valueOf(100000.00));
        newLand.setIndepYear((short) 2000);
        newLand.setPopulation(1000000);
        newLand.setLifeExpectancy(BigDecimal.valueOf(75.5));
        newLand.setGnp(BigDecimal.valueOf(2000000.00));
        newLand.setGNPOld(BigDecimal.valueOf(1500000.00));
        newLand.setLocalName("Locale");
        newLand.setGovernmentForm("Dictatorship");
        newLand.setHeadOfState("Gilbert");
        newLand.setCapital(1);
        newLand.setCode2("NL");
        worldService.createCountry(newLand);
    }

    @Test
    @Order(2)
    @DisplayName("Finding our new country")
    void findingCountryByIdDemo(){
        Country newLand = worldService.getCountryByCountryCode("NLE").orElseThrow();
        System.out.println(newLand);
    }

    @Test
    @Order(3)
    @DisplayName("Deleting our New Country")
    void deletingCountryByIdDemo(){
        worldService.deleteCountryByCountryCode("NLE");
    }

    @Test
    @Order(4)
    @DisplayName("Checking our new country is deleted")
    void findingCountryByIdShouldFailDemo(){
        try{
            Country newLand = worldService.getCountryByCountryCode("NLE").orElseThrow();
        } catch (NoSuchElementException e){
            System.out.println("This country doesnt exist");
        }
    }

}
