package com.sparta.audumbla.audumblaworldjpa.service;

import com.sparta.audumbla.audumblaworldjpa.entities.*;
import com.sparta.audumbla.audumblaworldjpa.repositories.CityRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryLanguageRepository;
import com.sparta.audumbla.audumblaworldjpa.repositories.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public List<Country> getCountryByPopulationBound
            (int populationLowerBound, int populationUpperBound) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getPopulation()>=populationLowerBound)
                .filter(country -> country.getPopulation()<=populationUpperBound)
                .collect(Collectors.toList());
    }
    public List<Country> getCountryByName(String name) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getName().contains(name))
                .collect(Collectors.toList());
    }
    public List<Country> getCountryByRegion(String region) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getRegion().contains(region))
                .collect(Collectors.toList());
    }
    public List<Country> getCountryByContinent(String continent) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getContinent().contains(continent))
                .collect(Collectors.toList());
    }
    public List<Country> getCountryBySurfaceArea
            (double surfaceAreaLowerBoundary, double surfaceAreaUpperBoundary) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getSurfaceArea()
                        .compareTo(BigDecimal.valueOf(surfaceAreaLowerBoundary))>=0)
                .filter(country -> country.getSurfaceArea()
                        .compareTo(BigDecimal.valueOf(surfaceAreaUpperBoundary))<=0)
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
    public List<Country> getCountryByGNP(double gnpLowerBoundary, double gnpUpperBoundary) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getGnp()
                        .compareTo(BigDecimal.valueOf(gnpLowerBoundary))>=0)
                .filter(country -> country.getGnp()
                        .compareTo(BigDecimal.valueOf(gnpUpperBoundary))<=0)
                .toList();
    }
    public List<Country> getCountryByGNPOld(double gnpLowerBoundary, double gnpUpperBoundary) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getGNPOld() != null)
                .filter(country -> country.getGNPOld()
                        .compareTo(BigDecimal.valueOf(gnpLowerBoundary))>=0)
                .filter(country -> country.getGNPOld()
                        .compareTo(BigDecimal.valueOf(gnpUpperBoundary))<=0)
                .toList();
    }
    public List<Country> getCountryByLocalName(String localName) {
        return countryRepository.findAll().stream()
                .filter(country -> localName.equalsIgnoreCase(country.getLocalName()))
                .toList();
    }
    public List<Country> getCountryByGovernmentForm(String governmentForm) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getGovernmentForm().contains(governmentForm))
                .toList();
    }
    public List<Country> getCountryByHeadOfState(String headOfState){
        return countryRepository.findAll().stream()
                .filter(country -> country.getHeadOfState() != null)
                .filter(country -> country.getHeadOfState().contains(headOfState))
                .toList();
    }
    public Optional<Country> getCountryByCapital(int capital){
        return countryRepository.findAll().stream()
                .filter(country -> country.getCapital() != null)
                .filter(country -> country.getCapital().equals(capital)).findFirst();
    }
    public Optional<Country> getCountryByShortCode(String shortCode){
        return countryRepository.findAll().stream()
                .filter(country -> shortCode.equalsIgnoreCase(country.getCode2())).findFirst();
    }
    // Languages
    // CountryCode, Language, IsOfficial,Percentage
    public List<Countrylanguage> getCountryLanguageByCountryCode(String countryCode){
        return countryLanguageRepository.findAll().stream()
                .filter(language-> countryCode.equalsIgnoreCase(language.getCountryCode().getCode()))
                .toList();
    }
    public List<Countrylanguage> getCountryLanguageByLanguage(String language){
        return countryLanguageRepository.findAll().stream()
                .filter(countrylanguage -> countrylanguage.getId().getLanguage().contains(language))
                .toList();
    }
    public List<Countrylanguage> getCountryLanguageByIsOfficial(boolean isOfficial){
        String condition = "";
        if (isOfficial) {
            condition = "T";
        } else {
            condition = "F";
        }
        String finalCondition = condition;
        return countryLanguageRepository.findAll().stream()
                .filter(countrylanguage -> countrylanguage.getIsOfficial().equalsIgnoreCase(finalCondition))
                .toList();
    }
    public List<Countrylanguage> getCountryLanguageByPercentage
            (double percentageLowerBoundary, double percentageUpperBoundary){
        return countryLanguageRepository.findAll().stream()
                .filter(countrylanguage -> countrylanguage.getPercentage()
                        .compareTo(BigDecimal.valueOf(percentageLowerBoundary))>=0)
                .filter(countrylanguage -> countrylanguage.getPercentage()
                        .compareTo(BigDecimal.valueOf(percentageUpperBoundary))<=0)
                .toList();

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

    public List<String> getFiveSmallestDistricts() {
        return getSmallestDistrictsLimitedTo(5);
    }

    public List<String> getAllSmallestDistrictsInOrder() {

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


    public List<String> getSmallestDistrictsLimitedTo(Integer limit){
        return getAllSmallestDistrictsInOrder().stream()
                .limit(5).toList();
    }

    @Transactional
    public Object getCountriesMainLanguageSpeakers(String countryCode) {
        Country country = getCountryByCountryCode(countryCode).orElseThrow();
        BigDecimal population = BigDecimal.valueOf(country.getPopulation());
        var test = getLanguagesByCountry(country).stream()
                .map(
                        language -> (language.getPercentage()
                                .divide(BigDecimal.valueOf(100))
                                .multiply(population))
                                .intValue()

                )
                .sorted()
                .toList();
        return test.getLast();
    }

    @Transactional
    public List<Countrylanguage> getLanguagesByCountry(Country country){
        return getAllCountryLanguages().stream()
                .filter(language -> language.getCountryCode().equals(country))
                .toList();

    public List<String> getFiveSmallestDistricts() {
        return getSmallestDistrictsLimitedTo(5);
    }

    public List<String> getSmallestDistrictsLimitedTo(Integer limit){
        return getAllSmallestDistricts().stream()
                .limit(5).toList();

    }
}
