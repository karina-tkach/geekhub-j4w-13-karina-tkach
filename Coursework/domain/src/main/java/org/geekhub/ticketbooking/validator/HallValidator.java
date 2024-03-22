package org.geekhub.ticketbooking.validator;

import org.geekhub.ticketbooking.exception.HallValidationException;
import org.geekhub.ticketbooking.model.Hall;
import org.geekhub.ticketbooking.model.Seat;
import org.geekhub.ticketbooking.model.Show;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HallValidator {
    private final SeatValidator seatValidator;
    private final ShowValidator showValidator;
    public HallValidator(SeatValidator seatValidator, ShowValidator showValidator) {
        this.seatValidator = seatValidator;
        this.showValidator = showValidator;
    }
    public void validate(Hall hall) {
        if (hall == null) {
            throw new HallValidationException("Hall was null");
        }

        validateSeats(hall.getSeats());
        validateShows(hall.getShows());
    }
    private void validateSeats(List<Seat> seats){
        for(Seat seat : seats) {
            seatValidator.validate(seat);
        }
    }
    private void validateShows(List<Show> shows){
        for(Show show : shows) {
            showValidator.validate(show);
        }
    }
}
