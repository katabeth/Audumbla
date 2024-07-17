package com.sparta.audumbla.audumblaworldjpa;

import com.sparta.audumbla.audumblaworldjpa.entities.City;
import com.sparta.audumbla.audumblaworldjpa.entities.Country;

import java.math.BigDecimal;

public class TestUtils {

    public static City createCity(Integer id, String name, String district, Integer population) {
        City city = new City();
        city.setId(id);
        city.setName(name);
        city.setDistrict(district);
        city.setPopulation(population);
        return city;
    }

    public static Country createCountry(
            String code,
            String name,
            String continent,
            String region,
            BigDecimal surfaceArea,
            Short indepYear,
            Integer population,
            BigDecimal lifeExpectancy,
            BigDecimal gnp,
            BigDecimal gNPOld,
            String localName,
            String governmentForm,
            String headOfState,
            Integer capital,
            String code2) {

        Country country = new Country();
        country.setCode(code);
        country.setName(name);
        country.setContinent(continent);
        country.setRegion(region);
        country.setSurfaceArea(surfaceArea);
        country.setIndepYear(indepYear);
        country.setPopulation(population);
        country.setLifeExpectancy(lifeExpectancy);
        country.setGnp(gnp);
        country.setGNPOld(gNPOld);
        country.setLocalName(localName);
        country.setGovernmentForm(governmentForm);
        country.setHeadOfState(headOfState);
        country.setCapital(capital);
        country.setCode2(code2);

        return country;
    }
}
