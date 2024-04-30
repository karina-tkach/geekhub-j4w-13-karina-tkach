package org.geekhub.ticketbooking.city;

import org.geekhub.ticketbooking.exception.CityValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CityValidatorTest {

    private CityValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new CityValidator();
    }

    @Test
    void validate_shouldThrowNoException_whenValidCity() throws CityValidationException {
        City city = new City(1, "New York");
        assertDoesNotThrow(() -> validator.validate(city), "The validation failed for a valid city object.");
    }

    @Test
    void validate_shouldThrowException_whenNullCity() {
        assertThrows(CityValidationException.class, () -> validator.validate(null), "Expected a CityValidationException for a null city object.");
    }

    @Test
    void validate_shouldThrowNoException_whenValidCityName() throws CityValidationException {
        String cityName = "Los Angeles";
        assertDoesNotThrow(() -> validator.validateName(cityName), "The validation failed for a valid city name.");
    }

    @Test
    void validate_shouldThrowException_whenInvalidCityName() {
        String cityName = "San Francisco!";
        assertThrows(CityValidationException.class, () -> validator.validateName(cityName), "Expected a CityValidationException for an invalid city name.");
    }

    @Test
    void validate_shouldThrowException_whenShortCityName() {
        String cityName = "LA";
        assertThrows(CityValidationException.class, () -> validator.validateName(cityName), "Expected a CityValidationException for a short city name.");
    }

    @Test
    void validate_shouldThrowException_whenLongCityName() {
        String cityName = "ThisIsAVeryLongCityName";
        assertThrows(CityValidationException.class, () -> validator.validateName(cityName), "Expected a CityValidationException for a long city name.");
    }
}
