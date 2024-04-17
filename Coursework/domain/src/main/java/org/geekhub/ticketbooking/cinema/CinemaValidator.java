package org.geekhub.ticketbooking.cinema;

import org.geekhub.ticketbooking.exception.CinemaValidationException;
import org.geekhub.ticketbooking.exception.CityValidationException;
import org.geekhub.ticketbooking.city.City;
import org.geekhub.ticketbooking.hall.Hall;
import org.geekhub.ticketbooking.city.CityValidator;
import org.springframework.stereotype.Component;

import java.util.List;

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
        validateHalls(cinema.getHalls());
    }

    private void validateCity(City city) {
        try {
            cityValidator.validate(city);
        } catch (CityValidationException exception) {
            throw new CinemaValidationException(exception.getMessage());
        }
    }

    private void validateHalls(List<Hall> halls) {
        if (halls == null || halls.isEmpty()) {
            throw new CinemaValidationException("Halls were null or empty");
        }
    }
}
