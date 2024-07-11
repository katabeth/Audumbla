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

    public Optional<Country> getCountryByCountryCode(String countryCode) {
        return countryRepository.findAll().stream()
                .filter(country -> countryCode.equalsIgnoreCase(country.getCode()))
                .findFirst();
    }
    public List<Country> getCountryByPopulationBound(int populationLowerBound, int populationUpperBound) {
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

    //Create methods
    public City createCity(City city) {
        nullCheck(city);
        return cityRepository.save(city);
    }

    public Country createCountry(Country country) {
        nullCheck(country);
        return countryRepository.save(country);
    }

    public Countrylanguage createCountryLanguage(Countrylanguage countryLanguage) {
        nullCheck(countryLanguage);
        return countryLanguageRepository.save(countryLanguage);
    }

    //Delete methods
    public void deleteCityById(Integer id) {
        cityRepository.deleteById(id);
    }

    public void deleteCountryByCountryCode(String countryCode) {
        countryRepository.deleteByCode(countryCode);
    }

    public void deleteCountryLanguageById(CountrylanguageId id) {
        countryLanguageRepository.deleteById(id);
    }

    //Specifically required CRUD methods
    public List<Country> getCountriesWithoutHeadOfState() {
        List<Country> allCountries = countryRepository.findAll();
        return allCountries.stream()
                .filter(country -> country.getHeadOfState() == null || country.getHeadOfState().isEmpty())
                .collect(Collectors.toList());
    }

    private void nullCheck(Object input) {
        if (input == null){
            throw new IllegalArgumentException("input cannot be null");
        }
    }
  
    public List<String> getAllSmallestDistricts() {
        return cityRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        City::getDistrict,
                        Collectors.summingInt(City::getPopulation)
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .toList();
    }

    public List<String> getFiveSmallestDistricts() {
        return getSmallestDistrictsLimitedTo(5);
    }

    public List<String> getSmallestDistrictsLimitedTo(Integer limit){
        return getAllSmallestDistricts().stream()
                .limit(5).toList();
    }
}
