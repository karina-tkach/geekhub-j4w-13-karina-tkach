package org.geekhub.ticketbooking.controller.admin;

import org.geekhub.ticketbooking.booking.Booking;
import org.geekhub.ticketbooking.booking.BookingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("admin/bookings")
public class AdminBookingController {
    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String viewBookingsWithPagination(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "7") int pageSize, Model model) {
        List<Booking> bookings = bookingService.getBookingsWithPagination(page, pageSize);
        int rows = bookingService.getBookingsRowsCount();
        if(rows == -1 || bookings.isEmpty()) {
            model.addAttribute("error", "Can't load bookings");
        }
        else {
            model.addAttribute("page", page);
            model.addAttribute("totalPages", Math.ceil(rows / (float) pageSize));
            model.addAttribute("listBookings", bookings);
        }

        return "bookings";
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/deleteBooking/{id}")
    public String deleteBooking(@PathVariable(value = "id") int id, Model model) {
        boolean result = bookingService.deleteBookingById(id);
        if(!result) {
            return setAttributesAndGetProperPage(model, "Cannot delete booking by this id");
        }
        return "redirect:/admin/bookings";
    }

    private String setAttributesAndGetProperPage(Model model, String attributeValue) {
        model.addAttribute("error", attributeValue);
        return "bookings";
    }
}
