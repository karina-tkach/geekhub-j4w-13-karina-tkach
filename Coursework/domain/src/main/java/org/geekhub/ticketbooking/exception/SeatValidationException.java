package org.geekhub.ticketbooking.exception;

public class SeatValidationException extends RuntimeException {
    public SeatValidationException(String message) {
        super(message);
    }
}
