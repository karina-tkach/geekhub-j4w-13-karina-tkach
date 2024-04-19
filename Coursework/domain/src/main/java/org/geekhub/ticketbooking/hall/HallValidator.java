package org.geekhub.ticketbooking.hall;

import org.geekhub.ticketbooking.exception.HallValidationException;
import org.springframework.stereotype.Component;

@Component
public class HallValidator {

    public void validate(Hall hall) {
        if (hall == null) {
            throw new HallValidationException("Hall was null");
        }

        validateName(hall.getHallName());
        validateRowsAndCols(hall.getRows(), hall.getColumns());
    }

    private void validateRowsAndCols(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new HallValidationException("Rows or columns were less than 1");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank() || name.length() > 50) {
            throw new HallValidationException("Hall name cannot be null or empty and must be less than 50 characters");
        }
    }
}
