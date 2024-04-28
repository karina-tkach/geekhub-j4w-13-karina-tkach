package org.geekhub.ticketbooking.controller;

import org.geekhub.ticketbooking.booking.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.geekhub.ticketbooking.booking.Booking;

@RestController
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/save-booking")
    public ResponseEntity<String> handlePaymentSuccess(@RequestBody Booking booking) {
        Booking addedBooking = bookingService.addBooking(booking);

        if (addedBooking == null) {
            return ResponseEntity.badRequest().body("Unable to add booking");
        }
        return ResponseEntity.ok("Booking received and processed successfully.");
    }
}
