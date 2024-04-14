package org.geekhub.ticketbooking.validator;

import org.geekhub.ticketbooking.exception.CityValidationException;
import org.geekhub.ticketbooking.model.City;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@SuppressWarnings("java:S5998")
public class CityValidator {
    public void validate(City city) {
        if (city == null) {
            throw new CityValidationException("City was null");
        }

        validateName(city.getName());
    }

    public void validateName(String name) {
        Pattern letters = Pattern.compile("^(?!-)[a-zA-Z]+(?:\\s?[a-zA-Z]+)*(?:\\s?-[a-zA-Z]+)*(?<!-)$");
        if (name == null || !letters.matcher(name).find() || name.length() > 21 || name.length() < 3) {
            throw new CityValidationException("City name must contain only letters and spaces or '-'");
        }
    }
}
