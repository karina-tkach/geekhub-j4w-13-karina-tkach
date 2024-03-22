package org.geekhub.ticketbooking.validator;

import org.geekhub.ticketbooking.exception.CinemaValidationException;
import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.model.City;
import org.geekhub.ticketbooking.model.Hall;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CinemaValidator {
    private final CityValidator cityValidator;
    private final HallValidator hallValidator;
    public CinemaValidator(CityValidator cityValidator, HallValidator hallValidator) {
        this.cityValidator = cityValidator;
        this.hallValidator = hallValidator;
    }
    public void validate(Cinema cinema) {
        if (cinema == null) {
            throw new CinemaValidationException("Cinema was null");
        }

        /*validateCity(cinema.getCity());
        validateHalls(cinema.getHalls());*/
    }
    /*private void validateHalls(List<Hall> halls) {
        for(Hall hall : halls) {
            hallValidator.validate(hall);
        }
    }
    private void validateCity(City city) {
        cityValidator.validate(city);
    }*/
}
