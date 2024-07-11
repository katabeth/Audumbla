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
                .filter(city -> city.getName().contains(name))
                .collect(Collectors.toList());
    }
    public Optional<City> getCitiesByID(int id) {
        return cityRepository.findById(id);
    }
    public List<City> getCitiesByDistrict(String district) {
        return cityRepository.findAll().stream()
                .filter(city -> city.getDistrict().contains(district))
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
    // HeadOfState, Capital, Code2

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
    public List<Country> getCountryByDistrict(String district) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getRegion().contains(district))
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
    public List<Country> getCountryByCapital(int capital){
        return countryRepository.findAll().stream()
                .filter(country -> country.getCapital() != null)
                .filter(country -> country.getCapital().equals(capital))
                .toList();
    }
    public Optional<Country> getCountryByShortCode(String shortCode){
        return countryRepository.findAll().stream()
                .filter(country -> shortCode.equalsIgnoreCase(country.getCode2())).findFirst();
    }
    // Languages
    // CountryCode, Language, IsOfficial,Percentage


    //Create methods
    public City createCity(City city) {
        return cityRepository.save(city);
    }

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Countrylanguage createCountryLanguage(Countrylanguage countryLanguage) {
        return countryLanguageRepository.save(countryLanguage);
    }

    //Delete methods
    public void deleteCityById(Integer id) {
        cityRepository.deleteById(id);
    }

    public void deleteCountryById(Integer code) {
        countryRepository.deleteById(code);
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
}
