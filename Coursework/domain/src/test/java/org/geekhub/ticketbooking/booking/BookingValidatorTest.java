package org.geekhub.ticketbooking.booking;

import org.geekhub.ticketbooking.exception.BookingValidationException;
import org.geekhub.ticketbooking.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingValidatorTest {

    @Mock
    private User mockUser;

    private BookingValidator bookingValidator;

    @BeforeEach
    public void setUp() {
        bookingValidator = new BookingValidator();
    }

    @Test
    void validate_shouldThrowNoException_whenValidBooking() {
        Booking booking = new Booking(1, 1, 1, mockUser, UUID.randomUUID(), "Show Details");
        assertDoesNotThrow(() -> bookingValidator.validate(booking), "No exception should be thrown for a valid booking.");
    }

    @Test
    void validate_shouldThrowException_whenNullBooking() {
        assertThrows(BookingValidationException.class, () -> bookingValidator.validate(null), "BookingValidationException should be thrown for a null booking.");
    }

    @Test
    void validate_shouldThrowException_whenNullUser() {
        Booking booking = new Booking(1, 1, 1, null, UUID.randomUUID(), "Show Details");
        assertThrows(BookingValidationException.class, () -> bookingValidator.validate(booking), "BookingValidationException should be thrown for a booking with null user.");
    }

    @Test
    void validate_shouldThrowException_whenNullUUID() {
        Booking booking = new Booking(1, 1, 1, mockUser, null, "Show Details");
        assertThrows(BookingValidationException.class, () -> bookingValidator.validate(booking), "BookingValidationException should be thrown for a booking with null UUID.");
    }

    @Test
    void validate_shouldThrowException_whenNullShowDetails() {
        Booking booking = new Booking(1, 1, 1, mockUser, UUID.randomUUID(), null);
        assertThrows(BookingValidationException.class, () -> bookingValidator.validate(booking), "BookingValidationException should be thrown for a booking with null show details.");
    }

    @Test
    void validate_shouldThrowException_whenEmptyShowDetails() {
        Booking booking = new Booking(1, 1, 1, mockUser, UUID.randomUUID(), "");
        assertThrows(BookingValidationException.class, () -> bookingValidator.validate(booking), "BookingValidationException should be thrown for a booking with blank show details.");
    }

    @Test
    void validate_shouldThrowException_whenWhitespacesShowDetails() {
        Booking booking = new Booking(1, 1, 1, mockUser, UUID.randomUUID(), "   ");
        assertThrows(BookingValidationException.class, () -> bookingValidator.validate(booking), "BookingValidationException should be thrown for a booking with whitespace show details.");
    }
}
