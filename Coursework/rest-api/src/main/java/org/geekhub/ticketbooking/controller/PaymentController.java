package org.geekhub.ticketbooking.controller;

import org.geekhub.ticketbooking.booking.Booking;
import org.geekhub.ticketbooking.booking.BookingService;
import org.geekhub.ticketbooking.exception.BookingValidationException;
import org.geekhub.ticketbooking.show.Show;
import org.geekhub.ticketbooking.show.ShowService;
import org.geekhub.ticketbooking.show.ShowUtilityService;
import org.geekhub.ticketbooking.show_seat.ShowSeatService;
import org.geekhub.ticketbooking.user.User;
import org.geekhub.ticketbooking.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.UUID;

@Controller
public class PaymentController {
    @Value("${stripe.api.publicKey}")
    private String publicKey;
    private final UserService userService;
    private final ShowService showService;
    private final ShowUtilityService showUtilityService;
    private final ShowSeatService showSeatService;
    private final BookingService bookingService;

    public PaymentController(UserService userService, ShowService showService,
                             ShowUtilityService showUtilityService,
                             ShowSeatService showSeatService, BookingService bookingService) {
        this.userService = userService;
        this.showService = showService;
        this.showUtilityService = showUtilityService;
        this.showSeatService = showSeatService;
        this.bookingService = bookingService;
    }

    @PostMapping("/buyTicket")
    public String book(@ModelAttribute("booking") Booking booking, Principal principal,
                       Model model) {
        User user = userService.getUserByEmail(principal.getName());
        booking.setUser(user);
        Show show = showService.getShowById(booking.getShowId());

        if (user == null || show == null) {
            return error(model);
        }
        String showDetails = showUtilityService.getShowsSelectOptions(Collections.singletonList(show))
            .get(booking.getShowId()) + "; Seat:" + showSeatService.getSeatById(booking.getSeatId()).getNumber();
        booking.setShowDetails(showDetails);
        booking.setUuid(UUID.randomUUID());

        try {
            bookingService.validateBooking(booking);
        } catch (BookingValidationException e) {
            return error(model);
        }

        model.addAttribute("booking", booking);
        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", show.getPrice());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("productName", showDetails);
        return "checkout";
    }

    @GetMapping("/paymentStatus")
    public String status() {
        return "payment_status";
    }

    private String error(Model model) {
        model.addAttribute("error", "Error happened processing booking");
        return "error-page";
    }
}
