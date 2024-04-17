package org.geekhub.ticketbooking.seat;

import org.geekhub.ticketbooking.exception.SeatValidationException;
import org.springframework.stereotype.Component;

@Component
public class SeatValidator {
    public void validate(Seat seat) {
        if (seat == null) {
            throw new SeatValidationException("Seat was null");
        }
        validateNumber(seat.getNumber());
    }

    private void validateNumber(int number) {
        if (number < 1) {
            throw new SeatValidationException("Seat number was negative or zero");
        }
    }
}
