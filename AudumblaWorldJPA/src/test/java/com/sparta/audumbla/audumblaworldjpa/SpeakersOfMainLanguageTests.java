package com.sparta.audumbla.audumblaworldjpa;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class SpeakersOfMainLanguageTests extends WorldServiceTest {

    @ParameterizedTest
    @DisplayName("Test we get correct estimate for speakers of main language")
    @CsvSource({
            "ABW,         79001",
            "BRA,         165862125",
            "EST,         939797",
    })
    void testWeGetCorrectEstimateForSpeakersOfMainLanguage(String countryCode, Integer expectedOutput) {
        Object actualOutput = worldService.getCountriesMainLanguageSpeakers(countryCode);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    @Transactional
    void testWeGetCorrectLanguagesBackForGivenCountry(){
        List<String> output = worldService.getLanguagesByCountry(worldService.getCountryByCountryCode("BRA").orElseThrow()).stream().map(lang -> lang.getId().getLanguage()).toList();
        List<String> expectedOutput = List.of("German","Indian Languages","Italian","Japanese","Portuguese");
        Assertions.assertEquals(expectedOutput, output);
    }

}
