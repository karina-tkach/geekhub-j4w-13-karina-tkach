package org.geekhub.ticketbooking.cinema;

import org.geekhub.ticketbooking.exception.CinemaValidationException;
import org.geekhub.ticketbooking.exception.CityValidationException;
import org.geekhub.ticketbooking.city.City;
import org.geekhub.ticketbooking.city.CityValidator;
import org.springframework.stereotype.Component;

@Component
public class CinemaValidator {
    private final CityValidator cityValidator;

    public CinemaValidator(CityValidator cityValidator) {
        this.cityValidator = cityValidator;
    }

    public void validate(Cinema cinema) {
        if (cinema == null) {
            throw new CinemaValidationException("Cinema was null");
        }

        validateName(cinema.getName());
        validateCity(cinema.getCity());
        validateName(cinema.getStreet());
    }

    private void validateCity(City city) {
        try {
            cityValidator.validate(city);
        } catch (CityValidationException exception) {
            throw new CinemaValidationException(exception.getMessage());
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank() || name.length() > 50) {
            throw new CinemaValidationException("Cinema name or street cannot be null or empty and must be less than 50 characters");
        }
    }
}
