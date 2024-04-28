package org.geekhub.ticketbooking.booking;

import java.util.List;

public interface BookingRepository {
    Booking getBookingById(int bookingId);

    List<Booking> getBookingsWithPagination(int pageNumber, int limit);

    int getBookingsRowsCount();

    int addBooking(Booking booking);

    void deleteBookingById(int bookingId);
}
