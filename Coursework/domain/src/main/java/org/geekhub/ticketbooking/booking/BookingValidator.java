package org.geekhub.ticketbooking.booking;

import org.geekhub.ticketbooking.exception.BookingValidationException;
import org.geekhub.ticketbooking.user.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookingValidator {
    public void validate(Booking booking) {
        if (booking == null) {
            throw new BookingValidationException("Booking was null");
        }

        validateUser(booking.getUser());
        validateUUID(booking.getUuid());
        validateShowDetails(booking.getShowDetails());
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new BookingValidationException("User was null");
        }
    }

    private void validateUUID(UUID uuid) {
        if (uuid == null) {
            throw new BookingValidationException("UUID was null");
        }
    }

    private void validateShowDetails(String showDetails) {
        if (showDetails == null || showDetails.isBlank()) {
            throw new BookingValidationException("Show details was null or blank");
        }
    }
}
