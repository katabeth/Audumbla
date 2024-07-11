package com.sparta.audumbla.audumblaworldjpa;

import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.repositories.*;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.sparta.audumbla.audumblaworldjpa.testUtils.createCity;


@SpringBootTest
public class fiveSmallestDistrictsWorldServiceTests {
    @Autowired
    WorldService worldService;

    private final List<City> testCities = Arrays.asList(
            createCity(1, "CityA", "DistrictA", 1000),
            createCity(2, "CityF", "DistrictD", 1800),
            createCity(3, "CityG", "DistrictA", 2500),
            createCity(4, "CityH", "DistrictE", 1200),
            createCity(5, "CityI", "DistrictF", 2300),
            createCity(6, "CityJ", "DistrictB", 3200),
            createCity(7, "CityK", "DistrictG", 2900),
            createCity(8, "CityL", "DistrictC", 2100),
            createCity(9, "CityM", "DistrictH", 3400),
            createCity(10, "CityN", "DistrictI", 1500)
    );

    @Test
    @DisplayName("Test get five smallest districts returns five")
    void testGetFiveSmallestDistrictsReturnsFive() {
        Integer outputSize = worldService.getFiveSmallestDistricts().size();
        Assertions.assertEquals(5, outputSize);
    }

    @Test
    @DisplayName("Test get five smallest districts returns the five smallest districts")
    void testGetFiveSmallestDistrictsReturnsTheFive() {
        CityRepository mockCityRepo = Mockito.mock(CityRepository.class);
        CountryRepository mockCountryRepo = Mockito.mock(CountryRepository.class);
        CountryLanguageRepository mockCountryLanguageRepo = Mockito.mock(CountryLanguageRepository.class);
        Mockito.when(mockCityRepo.findAll())
                .thenReturn(testCities);
        WorldService worldServiceForTest = new WorldService(mockCityRepo,mockCountryRepo,mockCountryLanguageRepo);

        List<String> output = worldServiceForTest.getFiveSmallestDistricts();
        Assertions.assertEquals(List.of("DistrictE","DistrictI","DistrictD","DistrictC","DistrictF"), output);
    }

}
