package com.sparta.audumbla.audumblaworldjpa.service;

import com.sparta.audumbla.audumblaworldjpa.entities.*;
import com.sparta.audumbla.audumblaworldjpa.repositories.CityRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryLanguageRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public List<String> getFiveSmallestDistricts() {
        return cityRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        City::getDistrict,
                        Collectors.summingInt(City::getPopulation)
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .limit(5)
                .toList();
    }
}
