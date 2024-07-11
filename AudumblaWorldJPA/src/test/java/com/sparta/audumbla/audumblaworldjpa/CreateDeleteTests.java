package com.sparta.audumbla.audumblaworldjpa;

import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.entities.Country;
import com.sparta.audumbla.audumblaworldjpa.entities.Countrylanguage;
import com.sparta.audumbla.audumblaworldjpa.entities.CountrylanguageId;
import com.sparta.audumbla.audumblaworldjpa.repositories.CityRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryLanguageRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryRepository;
import com.sparta.audumbla.audumblaworldjpa.service.WorldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CreateDeleteTests {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryLanguageRepository countryLanguageRepository;

    @InjectMocks
    private WorldService worldService;

    private City city;

    @Autowired
    private WorldService worldServiceReal;

//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }


    @Test
    void testCreateCity() {
        City city = new City();
        city.setId(1);
        city.setName("Test City");
        city.setDistrict("Test District");
        city.setPopulation(100000);

        when(cityRepository.save(city)).thenReturn(city);

        City createdCity = worldService.createCity(city);

        assertEquals(city.getId(), createdCity.getId());
        assertEquals(city.getName(), createdCity.getName());
        assertEquals(city.getDistrict(), createdCity.getDistrict());
        assertEquals(city.getPopulation(), createdCity.getPopulation());

        verify(cityRepository, times(1)).save(city);
    }

    @Test
    void testCreateCountry() {
        // Create a sample Country object
        Country country = new Country();
        country.setCode("TST");
        country.setName("Test Country");
        country.setContinent("Asia");
        country.setRegion("Test Region");
        country.setSurfaceArea(BigDecimal.valueOf(100000.00));
        country.setIndepYear((short) 2000);
        country.setPopulation(1000000);
        country.setLifeExpectancy(BigDecimal.valueOf(75.5));
        country.setGnp(BigDecimal.valueOf(2000000.00));
        country.setGNPOld(BigDecimal.valueOf(1500000.00));
        country.setLocalName("Test Local Name");
        country.setGovernmentForm("Test Government Form");
        country.setHeadOfState("Test Head Of State");
        country.setCapital(1);
        country.setCode2("TC");

        // Mock the behavior of countryRepository.save()
        when(countryRepository.save(country)).thenReturn(country);

        // Call the service method to create the country
        Country createdCountry = worldService.createCountry(country);

        // Verify the saved country object matches the expected values
        assertEquals(country.getCode(), createdCountry.getCode());
        assertEquals(country.getName(), createdCountry.getName());
        assertEquals(country.getContinent(), createdCountry.getContinent());
        assertEquals(country.getRegion(), createdCountry.getRegion());
        assertEquals(country.getSurfaceArea(), createdCountry.getSurfaceArea());
        assertEquals(country.getIndepYear(), createdCountry.getIndepYear());
        assertEquals(country.getPopulation(), createdCountry.getPopulation());
        assertEquals(country.getLifeExpectancy(), createdCountry.getLifeExpectancy());
        assertEquals(country.getGnp(), createdCountry.getGnp());
        assertEquals(country.getGNPOld(), createdCountry.getGNPOld());
        assertEquals(country.getLocalName(), createdCountry.getLocalName());
        assertEquals(country.getGovernmentForm(), createdCountry.getGovernmentForm());
        assertEquals(country.getHeadOfState(), createdCountry.getHeadOfState());
        assertEquals(country.getCapital(), createdCountry.getCapital());
        assertEquals(country.getCode2(), createdCountry.getCode2());

        // Verify that countryRepository.save() was called exactly once with any Country object
        verify(countryRepository, times(1)).save(any(Country.class));
    }

    @Test
    void testCreateCountryLanguage() {
        Country country = new Country();
        country.setCode("TST");

        CountrylanguageId countrylanguageId = new CountrylanguageId();
        countrylanguageId.setCountryCode("TST"); // Ensure this matches the countryCode set above
        countrylanguageId.setLanguage("Test Language");

        Countrylanguage countrylanguage = new Countrylanguage();
        countrylanguage.setId(countrylanguageId);
        countrylanguage.setCountryCode(country);
        countrylanguage.setIsOfficial("T");
        countrylanguage.setPercentage(BigDecimal.valueOf(50.0));

        when(countryLanguageRepository.save(any(Countrylanguage.class))).thenReturn(countrylanguage);

        Countrylanguage createdCountryLanguage = worldService.createCountryLanguage(countrylanguage);

        assertEquals(countrylanguage.getId(), createdCountryLanguage.getId());
        assertEquals(countrylanguage.getCountryCode(), createdCountryLanguage.getCountryCode());
        assertEquals(countrylanguage.getIsOfficial(), createdCountryLanguage.getIsOfficial());
        assertEquals(countrylanguage.getPercentage(), createdCountryLanguage.getPercentage());

        verify(countryLanguageRepository, times(1)).save(any(Countrylanguage.class));
    }

    @Test
    void testDeleteCityById() {
        Integer cityId = 1;

        worldService.deleteCityById(cityId);

        verify(cityRepository, times(1)).deleteById(cityId);
    }

    @Test
    void testDeleteCountryById() {
        Integer countryCode = 1;

        worldService.deleteCountryById(countryCode);

        verify(countryRepository, times(1)).deleteById(countryCode);
    }

    @Test
    void testDeleteCountryLanguageById() {
        CountrylanguageId countrylanguageId = new CountrylanguageId();

        worldService.deleteCountryLanguageById(countrylanguageId);

        verify(countryLanguageRepository, times(1)).deleteById(countrylanguageId);
    }

    @Test
    void testGetCountriesWithoutHeadOfState() {
        List<Country> result = worldServiceReal.getCountriesWithoutHeadOfState();
        assertEquals(3, result.size());
    }
}
