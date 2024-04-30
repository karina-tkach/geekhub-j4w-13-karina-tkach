package org.geekhub.ticketbooking.booking;

import org.geekhub.ticketbooking.exception.BookingValidationException;
import org.geekhub.ticketbooking.mail.MailSenderService;
import org.geekhub.ticketbooking.show.Show;
import org.geekhub.ticketbooking.show.ShowService;
import org.geekhub.ticketbooking.show_seat.ShowSeat;
import org.geekhub.ticketbooking.show_seat.ShowSeatService;
import org.geekhub.ticketbooking.user.User;
import org.geekhub.ticketbooking.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private BookingValidator bookingValidator;
    @Mock
    private UserService userService;
    @Mock
    private ShowSeatService showSeatService;
    @Mock
    private ShowService showService;
    @Mock
    private MailSenderService mailSenderService;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(bookingRepository,
            bookingValidator, userService,
            showSeatService, showService, mailSenderService);
    }

    @Test
    void getBookingById_shouldReturnProperBooking_whenValidId() {
        Booking mockBooking = new Booking();

        when(bookingRepository.getBookingById(anyInt())).thenReturn(mockBooking);

        Booking result = bookingService.getBookingById(1);

        verify(bookingRepository).getBookingById(1);

        assertSame(mockBooking, result);
    }

    @Test
    void getBookingById_shouldReturnNull_whenInvalidId() {
        when(bookingRepository.getBookingById(anyInt())).thenReturn(null);

        Booking result = bookingService.getBookingById(0);

        verify(bookingRepository).getBookingById(0);

        assertNull(result);
    }

    @Test
    void getBookingById_shouldReturnNull_whenDataAccessException() {
        when(bookingRepository.getBookingById(anyInt())).thenThrow(new DataAccessException("Data access error") {});

        Booking result = bookingService.getBookingById(1);

        verify(bookingRepository).getBookingById(1);

        assertNull(result);
    }

    @Test
    void getBookingsWithPagination_shouldReturnBookings_whenValidPageNumberAndLimit() {
        int pageNumber = 1;
        int limit = 10;
        List<Booking> expectedBookings = new ArrayList<>();
        expectedBookings.add(new Booking());
        when(bookingRepository.getBookingsWithPagination(pageNumber, limit)).thenReturn(expectedBookings);

        List<Booking> actualBookings = bookingService.getBookingsWithPagination(pageNumber, limit);

        assertEquals(expectedBookings, actualBookings, "The returned bookings do not match the expected bookings.");
    }

    @Test
    void getBookingsWithPagination_shouldReturnEmptyList_whenInvalidPageNumber() {
        int pageNumber = -1;
        int limit = 10;

        List<Booking> result = bookingService.getBookingsWithPagination(pageNumber, limit);

        assertEquals(Collections.emptyList(), result, "The list of bookings should be empty.");
    }

    @Test
    void getBookingsWithPagination_shouldReturnEmptyList_whenInvalidLimit() {
        int pageNumber = 1;
        int limit = -1;

        List<Booking> result = bookingService.getBookingsWithPagination(pageNumber, limit);

        assertEquals(Collections.emptyList(), result, "The list of bookings should be empty.");
    }

    @Test
    void getBookingsWithPagination_shouldReturnEmptyList_whenDataAccessException() {
        int pageNumber = 1;
        int limit = 10;
        when(bookingRepository.getBookingsWithPagination(pageNumber, limit)).thenThrow(new DataAccessException("Database connection error") {});

        List<Booking> actualBookings = bookingService.getBookingsWithPagination(pageNumber, limit);

        assertTrue(actualBookings.isEmpty(), "The returned bookings list should be empty.");
    }

    @Test
    void getBookingsRowsCount_shouldReturnCount_whenValidCount() {
        int expectedCount = 100;
        when(bookingRepository.getBookingsRowsCount()).thenReturn(expectedCount);

        int actualCount = bookingService.getBookingsRowsCount();

        assertEquals(expectedCount, actualCount, "The returned count does not match the expected count.");
    }

    @Test
    void getBookingsRowsCount_shouldReturnNegativeOne_whenDataAccessException() {
        when(bookingRepository.getBookingsRowsCount()).thenThrow(new DataAccessException("Database connection error") {});

        int actualCount = bookingService.getBookingsRowsCount();

        assertEquals(-1, actualCount, "The returned count should be -1.");
    }

    @Test
    void addBooking_shouldReturnBooking_whenValidBooking() throws Exception {
        Booking booking = new Booking();
        User user = new User();
        user.setEmail("abc");
        booking.setUser(user);
        booking.setSeatId(1);
        booking.setShowId(1);
        booking.setUuid(UUID.randomUUID());
        booking.setShowDetails("abcd");

        doNothing().when(bookingValidator).validate(booking);
        when(userService.getUserById(booking.getUser().getId())).thenReturn(new User());
        when(showService.getShowById(booking.getSeatId())).thenReturn(new Show());
        when(showSeatService.getSeatById(booking.getSeatId())).thenReturn(new ShowSeat());
        when(bookingRepository.addBooking(booking)).thenReturn(1);
        when(bookingRepository.getBookingById(1)).thenReturn(booking);
        doNothing().when(mailSenderService).sendEmail(anyString(), anyString(), anyString());

        Booking result = bookingService.addBooking(booking);

        assertNotNull(result, "The booking should not be null");
        verify(bookingValidator, times(1)).validate(booking);
        verify(showSeatService, times(1)).updateSeatById(any(ShowSeat.class), anyInt(), anyInt(), anyInt());
    }

    @Test
    void addBooking_shouldReturnNull_whenNoKeyGenerated() {
        Booking booking = new Booking();
        booking.setUser(new User());
        booking.setSeatId(1);
        booking.setShowId(1);
        booking.setUuid(UUID.randomUUID());
        booking.setShowDetails("abcd");

        doNothing().when(bookingValidator).validate(booking);
        when(userService.getUserById(booking.getUser().getId())).thenReturn(new User());
        when(showService.getShowById(booking.getSeatId())).thenReturn(new Show());
        when(showSeatService.getSeatById(booking.getSeatId())).thenReturn(new ShowSeat());
        when(bookingRepository.addBooking(booking)).thenReturn(-1);

        Booking result = bookingService.addBooking(booking);

        assertNull(result, "The booking should be null");
        verify(bookingValidator, times(1)).validate(booking);
    }

    @Test
    void addBooking_shouldReturnNull_whenInvalidBooking() {
        Booking booking = new Booking();
        booking.setUser(new User());
        booking.setSeatId(1);
        booking.setShowId(1);
        booking.setUuid(UUID.randomUUID());
        booking.setShowDetails("abcd");

        doThrow(new BookingValidationException("User/show/seat of booking is incorrect")).when(bookingValidator).validate(booking);

        Booking result = bookingService.addBooking(booking);

        assertNull(result, "The booking should be null");
        verify(bookingValidator, times(1)).validate(booking);
    }

    @Test
    void validateBooking_shouldThrowNothing_whenValidBooking() {
        Booking booking = new Booking();
        booking.setUser(new User());
        booking.setSeatId(1);
        booking.setShowId(1);
        booking.setUuid(UUID.randomUUID());
        booking.setShowDetails("abcd");

        when(userService.getUserById(booking.getUser().getId())).thenReturn(new User());
        when(showService.getShowById(booking.getShowId())).thenReturn(new Show());
        when(showSeatService.getSeatById(booking.getSeatId())).thenReturn(new ShowSeat());

        assertDoesNotThrow(() -> bookingService.validateBooking(booking), "No exception should be thrown");
        verify(bookingValidator, times(1)).validate(booking);
    }

    @Test
    void validateBooking_shouldThrowException_whenInvalidBooking() {
        Booking booking = new Booking();
        booking.setUser(new User());
        booking.setSeatId(1);
        booking.setShowId(1);
        booking.setUuid(UUID.randomUUID());
        booking.setShowDetails("abcd");

        when(userService.getUserById(booking.getUser().getId())).thenReturn(null);

        assertThrows(BookingValidationException.class, () -> bookingService.validateBooking(booking), "BookingValidationException should be thrown");
        verify(bookingValidator, times(1)).validate(booking);
    }

    @Test
    void validateBooking_shouldThrowException_whenInvalidShow() {
        Booking booking = new Booking();
        booking.setUser(new User());
        booking.setSeatId(1);
        booking.setShowId(1);
        booking.setUuid(UUID.randomUUID());
        booking.setShowDetails("abcd");

        when(userService.getUserById(booking.getUser().getId())).thenReturn(new User());
        when(showService.getShowById(booking.getShowId())).thenReturn(null);

        assertThrows(BookingValidationException.class, () -> bookingService.validateBooking(booking), "BookingValidationException should be thrown");
        verify(bookingValidator, times(1)).validate(booking);
    }

    @Test
    void validateBooking_shouldThrowException_whenInvalidSeat() {
        Booking booking = new Booking();
        booking.setUser(new User());
        booking.setSeatId(1);
        booking.setShowId(1);
        booking.setUuid(UUID.randomUUID());
        booking.setShowDetails("abcd");

        when(userService.getUserById(booking.getUser().getId())).thenReturn(new User());
        when(showService.getShowById(booking.getShowId())).thenReturn(new Show());
        when(showSeatService.getSeatById(booking.getSeatId())).thenReturn(null);

        assertThrows(BookingValidationException.class, () -> bookingService.validateBooking(booking), "BookingValidationException should be thrown");
        verify(bookingValidator, times(1)).validate(booking);
    }

    @Test
    void deleteBookingById_shouldReturnTrue_whenValidId() {
        int bookingId = 1;
        Booking bookingToDel = new Booking();
        bookingToDel.setId(bookingId);
        ShowSeat seat = new ShowSeat();
        seat.setId(1);
        seat.setBooked(true);

        when(bookingRepository.getBookingById(bookingId)).thenReturn(bookingToDel);
        when(showSeatService.getSeatById(bookingToDel.getSeatId())).thenReturn(seat);

        boolean result = bookingService.deleteBookingById(bookingId);

        assertTrue(result, "The booking deletion should be successful");
        verify(bookingRepository, times(1)).deleteBookingById(bookingId);
        verify(showSeatService, times(1)).updateSeatById(seat, seat.getId(), seat.getHallId(), seat.getShowId());
        assertFalse(seat.isBooked(), "The seat should be marked as not booked");
    }

    @Test
    void deleteBookingById_shouldReturnFalse_whenInvalidId() {
        int bookingId = 1;

        when(bookingRepository.getBookingById(bookingId)).thenReturn(null);

        boolean result = bookingService.deleteBookingById(bookingId);

        assertFalse(result, "The booking deletion should fail");
        verify(bookingRepository, never()).deleteBookingById(anyInt());
        verify(showSeatService, never()).updateSeatById(any(), anyInt(), anyInt(), anyInt());
    }

    @Test
    void deleteBookingById_shouldReturnFalse_whenDataAccessException() {
        int bookingId = 1;
        Booking bookingToDel = new Booking();
        bookingToDel.setId(bookingId);
        ShowSeat seat = new ShowSeat();
        seat.setId(1);
        seat.setBooked(true);

        when(bookingRepository.getBookingById(bookingId)).thenReturn(bookingToDel);
        doThrow(new DataAccessException("Custom message") {}).when(bookingRepository).deleteBookingById(bookingId);

        boolean result = bookingService.deleteBookingById(bookingId);

        assertFalse(result, "The booking deletion should fail");
        verify(bookingRepository, times(1)).deleteBookingById(bookingId);
        verify(showSeatService, never()).updateSeatById(any(), anyInt(), anyInt(), anyInt());
    }
}
