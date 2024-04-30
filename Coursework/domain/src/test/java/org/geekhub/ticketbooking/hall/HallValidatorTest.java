package org.geekhub.ticketbooking.hall;

import org.geekhub.ticketbooking.exception.HallValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HallValidatorTest {
    private HallValidator hallValidator;

    @BeforeEach
    public void setUp() {
        hallValidator = new HallValidator();
    }

    @Test
    void validate_shouldThrowNoException_whenValidHall() {
        Hall hall = new Hall(1, "Hall 1", 10, 10, 1);
        assertDoesNotThrow(() -> hallValidator.validate(hall), "Validation failed for a valid Hall object.");
    }

    @Test
    void validate_shouldThrowException_whenNullHall() {
        assertThrows(HallValidationException.class, () -> hallValidator.validate(null), "Validation did not throw an exception for a null Hall object.");
    }

    @Test
    void validate_shouldThrowException_whenNullHallName() {
        Hall hall = new Hall(1, null, 10, 10, 1);
        assertThrows(HallValidationException.class, () -> hallValidator.validate(hall), "Validation did not throw an exception for a Hall object with a null name.");
    }

    @Test
    void validate_shouldThrowException_whenEmptyHallName() {
        Hall hall = new Hall(1, "", 10, 10, 1);
        assertThrows(HallValidationException.class, () -> hallValidator.validate(hall), "Validation did not throw an exception for a Hall object with an empty name.");
    }

    @Test
    void validate_shouldThrowException_whenLongHallName() {
        Hall hall = new Hall(1, "This is a very long hall name that exceeds the maximum character limit", 10, 10, 1);
        assertThrows(HallValidationException.class, () -> hallValidator.validate(hall), "Validation did not throw an exception for a Hall object with a long name.");
    }

    @Test
    void validate_shouldThrowException_whenLessHallRows() {
        Hall hall = new Hall(1, "Hall 1", 0, 10, 1);
        assertThrows(HallValidationException.class, () -> hallValidator.validate(hall), "Validation did not throw an exception for a Hall object with less than 1 row.");
    }

    @Test
    void validate_shouldThrowException_whenLessHallColumns() {
        Hall hall = new Hall(1, "Hall 1", 10, 0, 1);
        assertThrows(HallValidationException.class, () -> hallValidator.validate(hall), "Validation did not throw an exception for a Hall object with less than 1 column.");
    }

    @Test
    void validate_shouldThrowException_whenMoreHallColumns() {
        Hall hall = new Hall(1, "Hall 1", 10, 20, 1);
        assertThrows(HallValidationException.class, () -> hallValidator.validate(hall), "Validation did not throw an exception for a Hall object with more than 15 columns.");
    }

    @Test
    void validate_shouldThrowException_whenMoreHallRows() {
        Hall hall = new Hall(1, "Hall 1", 110, 10, 1);
        assertThrows(HallValidationException.class, () -> hallValidator.validate(hall), "Validation did not throw an exception for a Hall object with more than 100 rows.");
    }

    @Test
    void validate_shouldThrowException_whenMoreHallSeats() {
        Hall hall = new Hall(1, "Hall 1", 10, 100, 1);
        assertThrows(HallValidationException.class, () -> hallValidator.validate(hall), "Validation did not throw an exception for a Hall object with more than 999 total seats.");
    }
}
