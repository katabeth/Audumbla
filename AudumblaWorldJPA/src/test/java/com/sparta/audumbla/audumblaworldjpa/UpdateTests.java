package com.sparta.audumbla.audumblaworldjpa;

import com.mysql.cj.util.TestUtils;
import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.entities.Country;
import com.sparta.audumbla.audumblaworldjpa.repositories.CityRepository;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UpdateTests {
    @Autowired
    WorldService worldService;
    @Autowired
    private CityRepository cityRepository;

    public static City createCity(Integer id, String name, Country countryCode, String district, Integer population) {
        City city = new City();
        city.setId(id);
        city.setName(name);
        city.setCountryCode(countryCode);
        city.setDistrict(district);
        city.setPopulation(population);
        return city;
    }
    @Order(1)
    @Test
    @DisplayName("Test I can update a city using a city record")
    //@Transactional
    void testUpdateCityWithRecord() {
        City katTown = createCity
                (5,"Amsterdam",worldService.getCountryByCountryCode("NLD").orElseThrow(),
                        "Nord-Holland",731200);
        worldService.updateCityTable(katTown.getId(), katTown);
        System.out.println(worldService.getCitiesByID(5).orElseThrow().getName());
    }
    @Order(2)
    @Test
    @DisplayName("Test I can update a country using a country record")
    void testUpdateCountryWithRecord() {
        Country katLand = new Country();
        katLand.setCode("ARE");
        katLand.setName("Katlandia");
        katLand.setContinent("Asia");
        katLand.setRegion("Test Region");
        katLand.setSurfaceArea(BigDecimal.valueOf(100000.00));
        katLand.setIndepYear((short) 2000);
        katLand.setPopulation(1000000);
        katLand.setLifeExpectancy(BigDecimal.valueOf(75.5));
        katLand.setGnp(BigDecimal.valueOf(2000000.00));
        katLand.setGNPOld(BigDecimal.valueOf(1500000.00));
        katLand.setLocalName("Test Local Name");
        katLand.setGovernmentForm("Test Government Form");
        katLand.setHeadOfState("Test Head Of State");
        katLand.setCapital(1);
        katLand.setCode2("TC");

        worldService.updateCountryTable(katLand.getCode(),katLand);
        System.out.println(worldService.getCountryByCountryCode("ARE").orElseThrow().getName());
    }

}
