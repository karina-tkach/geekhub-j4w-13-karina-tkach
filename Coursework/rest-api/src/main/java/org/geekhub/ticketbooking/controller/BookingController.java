package org.geekhub.ticketbooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.geekhub.ticketbooking.booking.Booking;

@RestController
public class BookingController {

    @PostMapping("/save-booking")
    public ResponseEntity<String> handlePaymentSuccess(@RequestBody Booking booking) {
        System.out.println(booking);
        return ResponseEntity.ok("POST request received and processed successfully.");
    }
}
