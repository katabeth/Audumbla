package com.sparta.audumbla.audumblaworldjpa.service;

import com.sparta.audumbla.audumblaworldjpa.entities.*;
import com.sparta.audumbla.audumblaworldjpa.repositories.CityRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryLanguageRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorldService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final CountryLanguageRepository countryLanguageRepository;

    @Autowired
    public WorldService(CityRepository cityRepository,
                        CountryRepository countryRepository,
                        CountryLanguageRepository countryLanguageRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.countryLanguageRepository = countryLanguageRepository;
    }
    // READ and UPDATE for all Tables
    // READ all cities, countries, languages
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
    public List<Countrylanguage> getAllCountryLanguages() {
        return countryLanguageRepository.findAll();
    }
    // READ individual columns
    //City - ID, Name, CountryCode, District, Population
    public List<City> getCitiesByName(String name) {
        return cityRepository.findAll().stream()
                .filter(city -> name.equalsIgnoreCase(city.getName()))
                .collect(Collectors.toList());
    }
    public Optional<City> getCitiesByID(int id) {
        return cityRepository.findById(id);
    }
    public List<City> getCitiesByDistrict(String district) {
        return cityRepository.findAll().stream()
                .filter(city -> district.equalsIgnoreCase(city.getDistrict()))
                .collect(Collectors.toList());
    }
    public List<City> getCitiesByCountryCode(String countryCode) {
        return cityRepository.findAll().stream()
                .filter(city -> countryCode.equalsIgnoreCase(city.getCountryCode().getCode()))
                .collect(Collectors.toList());
    }
    public List<City> getCitiesByPopulationBound(int populationLowerBound, int populationUpperBound) {
        return cityRepository.findAll().stream()
                .filter(city -> city.getPopulation()>=populationLowerBound)
                .filter(city -> city.getPopulation()<=populationUpperBound)
                .collect(Collectors.toList());
    }
    //Country - Code, Name, Continent, Region, Surface Area,
    // IndepYear, Population, LifeExepectancy, GNP, GNPOld, LocalName, Government Form

    public Optional<Country> getCountryByCountryCode(String countryCode) {
        return countryRepository.findAll().stream()
                .filter(country -> countryCode.equalsIgnoreCase(country.getCode()))
                .findFirst();
    }
    public List<Country> getCountryByPopulationBound
            (int populationLowerBound, int populationUpperBound) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getPopulation()>=populationLowerBound)
                .filter(country -> country.getPopulation()<=populationUpperBound)
                .collect(Collectors.toList());
    }
    public List<Country> getCountryByName(String name) {
        return countryRepository.findAll().stream()
                .filter(country -> name.equalsIgnoreCase(country.getName()))
                .collect(Collectors.toList());
    }
    public List<Country> getCountryByDistrict(String district) {
        return countryRepository.findAll().stream()
                .filter(country -> district.equalsIgnoreCase(country.getRegion()))
                .collect(Collectors.toList());
    }
    public List<Country> getCountryByContinent(String continent) {
        return countryRepository.findAll().stream()
                .filter(country -> continent.equalsIgnoreCase(country.getContinent()))
                .collect(Collectors.toList());
    }
    public List<Country> getCountryBySurfaceArea
            (double surfaceAreaLowerBoundary, double surfaceAreaUpperBoundary) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getSurfaceArea()
                        .compareTo(BigDecimal.valueOf(surfaceAreaLowerBoundary))>=0)
                .filter(country -> country.getSurfaceArea()
                        .compareTo(BigDecimal.valueOf(surfaceAreaLowerBoundary))<=0)
                .toList();
    }
    public List<Country> getCountryByIndepYear(short indepYear) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getIndepYear() != null)
                .filter(country -> country.getIndepYear()==indepYear)
                .toList();
    }
    public List<Country> getCountryByLifeExpectancy
            (double lifeExpectancyLowerBoundary, double lifeExpectancyUpperBoundary) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getLifeExpectancy() != null)
                .filter(country -> country.getLifeExpectancy()
                        .compareTo(BigDecimal.valueOf(lifeExpectancyLowerBoundary))>=0)
                .filter(country -> country.getLifeExpectancy()
                        .compareTo(BigDecimal.valueOf(lifeExpectancyUpperBoundary))<=0)
                .toList();
    }
    
}
