package org.geekhub.ticketbooking.show_seat;

import org.geekhub.ticketbooking.exception.SeatValidationException;
import org.springframework.stereotype.Component;

@Component
public class ShowSeatValidator {
    public void validate(ShowSeat seat) {
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
