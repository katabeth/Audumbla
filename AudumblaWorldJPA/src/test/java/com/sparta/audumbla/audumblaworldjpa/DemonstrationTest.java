package com.sparta.audumbla.audumblaworldjpa;


import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.entities.Country;
import com.sparta.audumbla.audumblaworldjpa.entities.Countrylanguage;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemonstrationTest extends WorldServiceTest {

    @Test
    @DisplayName("Z Getting all Countries from The database")
    void getAllCountriesDemonstration() {
        List<Country> countries = worldService.getAllCountries();
        for(Country country : countries){
            System.out.println(country);
        }
        System.out.println("found " + countries.size() + " Countries");
    }

    @Transactional
    @Test
    @DisplayName("Z Getting all Cities from The database")
    void getAllCitiesDemonstration() {
        List<City> cities = worldService.getAllCities();
        for(City city : cities){
            System.out.println(city);
        }
        System.out.println("found " + cities.size() + " Cities");
    }


    @Test
    @DisplayName("Z Getting all CountryLanguages from The database")
    void getAllCountriesLanguagesDemonstration() {
        List<Countrylanguage> languages = worldService.getAllCountryLanguages();
        for(Countrylanguage language : languages){
            System.out.println(language);
        }
        System.out.println("found " + languages.size() + " Languages");
    }

    @Test
    @Order(1)
    @DisplayName("A Adding NewLand")
    void addingNewCountryDemonstration(){
        System.out.println("Adding country newland");
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
    @DisplayName("B Finding our new country")
    void findingCountryByIdDemo(){
        Country newLand = worldService.getCountryByCountryCode("NLE").orElseThrow();
        System.out.println(newLand);
    }

    @Test
    @Order(3)
    @DisplayName("C Updating our New Country")
    void updatingCountryDemo(){
        System.out.println("Updating newLand");
        Country newLand = worldService.getCountryByCountryCode("NLE").orElseThrow();
        newLand.setHeadOfState("Kat");
        worldService.updateCountryTable("NLE",newLand);

    }

    @Test
    @Order(4)
    @DisplayName("D Finding our updated country")
    void findingCountryByIdUpdated(){
        Country newLand = worldService.getCountryByCountryCode("NLE").orElseThrow();
        System.out.println(newLand);
    }

    @Test
    @Order(5)
    @DisplayName("E Deleting our New Country")
    void deletingCountryByIdDemo(){
        System.out.println("Deleting Newland");
        worldService.deleteCountryByCountryCode("NLE");
    }

    @Test
    @Order(6)
    @DisplayName("F Checking our country is deleted")
    void findingCountryByIdDeleted(){
        try{
            Country newLand = worldService.getCountryByCountryCode("NLE").orElseThrow();
        } catch (NoSuchElementException e){
            System.out.println("This country doesnt exist");
        }
    }

    @Transactional
    @Test
    @DisplayName("Z Getting Paris By Name")
    void getAllCountriesDemo(){
        City paris = worldService.getCitiesByName("Paris").getFirst();
        System.out.println(paris);
    }

    @Test
    @DisplayName("Z Getting the 5 smallest districts")
    void getSmallestDistricts(){
        List<String> districts = worldService.getFiveSmallestDistricts();
        System.out.println(districts);
    }

    @Test
    @DisplayName("Z Getting the Main Language Speakers")
    void getMainLanguageSpeaker(){
        int speakers = worldService.getCountriesMainLanguageSpeakers("BRA");
        System.out.println("brazil main language speakers " + speakers);
    }

    @Test
    @DisplayName("Z Getting the country with most cities")
    void getMostCitiesCount(){
        Map.Entry<Country,Long> output = worldService.findCountryWithMostCities();
        System.out.println(output.getValue());
        System.out.println(output.getKey().getName());
    }

    @Test
    @DisplayName("Z Getting percentage of population in a countries main city")
    void getPopulationPercentage(){
        BigDecimal pop = worldService.calculatePopulationPercentageInLargestCityByCountryName("France");
        System.out.println("percentage in largest city in france : " + pop);
    }

    @Test
    @DisplayName("Z Getting the countries with no head of state")
    void getNoHeadOfStateCountries(){
        List<Country> headless = worldService.getCountriesWithoutHeadOfState();
        for(Country country : headless){
            System.out.println(country);
        }
    }

}
