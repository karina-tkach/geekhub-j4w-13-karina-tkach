package org.geekhub.ticketbooking.exception;

public class MovieValidationException extends RuntimeException {
    public MovieValidationException(String message) {
        super(message);
    }
}
