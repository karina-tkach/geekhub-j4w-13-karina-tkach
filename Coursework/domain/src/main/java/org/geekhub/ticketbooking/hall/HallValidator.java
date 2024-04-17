package org.geekhub.ticketbooking.hall;

import org.geekhub.ticketbooking.exception.HallValidationException;
import org.geekhub.ticketbooking.exception.SeatValidationException;
import org.geekhub.ticketbooking.seat.Seat;
import org.geekhub.ticketbooking.seat.SeatValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HallValidator {
    private final SeatValidator seatValidator;

    public HallValidator(SeatValidator seatValidator) {
        this.seatValidator = seatValidator;
    }

    public void validate(Hall hall) {
        if (hall == null) {
            throw new HallValidationException("Hall was null");
        }

        validateSeats(hall, hall.getSeats());
    }

    private void validateSeats(Hall hall, List<Seat> seats) {
        if (seats.size() > hall.getRows() * hall.getColumns()) {
            throw new HallValidationException("Too much seats for hall");
        }

        try {
            for (Seat seat : seats) {
                seatValidator.validate(seat);
            }
        } catch (SeatValidationException exception) {
            throw new HallValidationException(exception.getMessage());
        }
    }
}
