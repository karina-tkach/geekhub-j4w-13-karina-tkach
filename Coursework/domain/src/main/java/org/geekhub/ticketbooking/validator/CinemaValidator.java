package org.geekhub.ticketbooking.validator;

import org.geekhub.ticketbooking.exception.CinemaValidationException;
import org.geekhub.ticketbooking.exception.CityValidationException;
import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.model.City;
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

        validateCity(cinema.getCity());
    }

    private void validateCity(City city) {
        try {
            cityValidator.validate(city);
        } catch (CityValidationException exception) {
            throw new CinemaValidationException(exception.getMessage());
        }
    }
}
