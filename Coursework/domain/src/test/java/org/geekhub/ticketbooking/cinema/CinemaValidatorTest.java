package org.geekhub.ticketbooking.cinema;

import org.geekhub.ticketbooking.city.City;
import org.geekhub.ticketbooking.city.CityValidator;
import org.geekhub.ticketbooking.exception.CinemaValidationException;
import org.geekhub.ticketbooking.exception.CityValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class CinemaValidatorTest {
    @Mock
    private CityValidator cityValidator;
    private CinemaValidator cinemaValidator;

    @BeforeEach
    public void setUp() {
        cinemaValidator = new CinemaValidator(cityValidator);
    }

    @Test
    void validate_shouldNotThrowException_whenValidCinema() {
        Cinema cinema = new Cinema(1, "Cinema Name", new City(1, "City Name"), "Street Name");
        doNothing().when(cityValidator).validate(cinema.getCity());

        assertDoesNotThrow(() -> cinemaValidator.validate(cinema));
    }

    @Test
    void validate_shouldThrowCinemaValidationException_whenNullCinema() {
        Cinema cinema = null;

        assertThrows(CinemaValidationException.class, () -> cinemaValidator.validate(cinema));
    }

    @Test
    void validate_shouldThrowCinemaValidationException_whenNullName() {
        Cinema cinema = new Cinema(1, null, new City(1,"City Name"), "Street Name");

        assertThrows(CinemaValidationException.class, () -> cinemaValidator.validate(cinema));
    }

    @Test
    void validate_shouldThrowCinemaValidationException_whenEmptyName() {
        Cinema cinema = new Cinema(1, "", new City(1, "City Name"), "Street Name");

        assertThrows(CinemaValidationException.class, () -> cinemaValidator.validate(cinema));
    }

    @Test
    void validate_shouldThrowCinemaValidationException_whenNameExceedsMaxLength() {
        String longName = "This is a very long cinema name that exceeds the maximum length allowed";
        Cinema cinema = new Cinema(1, longName, new City(1, "City Name"), "Street Name");

        assertThrows(CinemaValidationException.class, () -> cinemaValidator.validate(cinema));
    }

    @Test
    void validate_shouldThrowCinemaValidationException_whenNullCity() {
        Cinema cinema = new Cinema(1, "Cinema Name", null, "Street Name");
        doThrow(CityValidationException.class).when(cityValidator).validate(cinema.getCity());

        assertThrows(CinemaValidationException.class, () -> cinemaValidator.validate(cinema));
    }

    @Test
    void validate_shouldThrowCinemaValidationException_whenInvalidCity() {
        Cinema cinema = new Cinema(1, "Cinema Name", new City(1, ""), "Street Name");
        doThrow(CityValidationException.class).when(cityValidator).validate(cinema.getCity());

        assertThrows(CinemaValidationException.class, () -> cinemaValidator.validate(cinema));
    }

    @Test
    void validate_shouldThrowCinemaValidationException_whenNullStreet() {
        Cinema cinema = new Cinema(1, "Cinema Name", new City(1, "City Name"), null);
        doNothing().when(cityValidator).validate(cinema.getCity());

        assertThrows(CinemaValidationException.class, () -> cinemaValidator.validate(cinema));
    }

    @Test
    void validate_shouldThrowCinemaValidationException_whenEmptyStreet() {
        Cinema cinema = new Cinema(1, "Cinema Name", new City(1, "City Name"), "");
        doNothing().when(cityValidator).validate(cinema.getCity());

        assertThrows(CinemaValidationException.class, () -> cinemaValidator.validate(cinema));
    }
}
