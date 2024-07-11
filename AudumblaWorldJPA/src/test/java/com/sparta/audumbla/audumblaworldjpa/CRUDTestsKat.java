package com.sparta.audumbla.audumblaworldjpa;

import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.entities.Country;
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


    @Test
    void test(){
        List<City> cities = worldService.getAllCities();
    }
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
    @Transactional
    void testSearchCitiesByDistrict(){
        List<City> test = worldService.getCitiesByDistrict("British Colombia");
        Assertions.assertEquals(9, test.size());
    }
    @Test
    @DisplayName("Test I can Search Cities by PopulationBound")
    @Transactional
    void testSearchCitiesByPopulationBound(){
        List<City> test = worldService.getCitiesByPopulationBound(90000,100000);
        Assertions.assertEquals(391, test.size());
    }
    @Test
    @DisplayName("Test I can Search Cities by Country Code")
    void testSearchCitiesByCountryCode(){
        List<City> test = worldService.getCitiesByCountryCode("USA");
        Assertions.assertEquals(274, test.size());
    }

    @Test
    @DisplayName("Test I can search country by Surface Area")
    void testSearchCitiesBySurfaceArea(){
        List<Country> test = worldService.getCountryBySurfaceArea(200.00,300);
        Assertions.assertEquals(7, test.size());
    }
    @Test
    @DisplayName("Test I can search country by Independace Year")
    void testSearchCountryByIndepYear(){
        List<Country> test = worldService.getCountryByIndepYear((short) 1919);
        Assertions.assertEquals(1, test.size());
    }
    @Test
    @DisplayName("Test I can search country by Life Expectancy")
    void testSearchCountryByLifeExpectancy(){
        List<Country> test = worldService.getCountryByLifeExpectancy(70,72);
        Assertions.assertEquals(25, test.size());
    }

    @Test
    @DisplayName("GetCountryByGNP")
    void GetCountryByGNP() {
        List<Country> test = worldService.getCountryByGNP(100,150);
        Assertions.assertEquals(5, test.size());
    }

    @Test
    @DisplayName("GetCountryByGNPOld")
    void GetCountryByGNPOld() {
        List<Country> test = worldService.getCountryByGNPOld(1000,1500);
        Assertions.assertEquals(9, test.size());
    }

    @Test
    @DisplayName("GetCountryByLocalName")
    void GetCountryByLocalName() {
        List<Country> test = worldService.getCountryByLocalName("France");
        Assertions.assertEquals(1, test.size());
    }

    @Test
    @DisplayName("GetCountryByGovernmentForm")
    void GetCountryByGovernmentForm() {
        List<Country> test = worldService.getCountryByGovernmentForm("Republic");
        Assertions.assertEquals(122, test.size());
    }

    @Test
    @DisplayName("TestI can search country by Head of State")
    void TestSearchCountryByHeadOfState() {
        List<Country> test = worldService.getCountryByHeadOfState("Elisabeth");
        Assertions.assertEquals(35, test.size());
    }
    @Test
    @DisplayName("Test getCountryByCountryCode")
    void getCountryByCountryCode() {

    }
    @Test
    @DisplayName("Test getCountryByPopulationBound")
            void getCountryByPopulationBound() {
        
    }


    getCountryByPopulationBound
    getCountryByName
    getCountryByDistrict
    getCountryByContinent
    getCountryByCapital
    getCountryByShortCode
}
