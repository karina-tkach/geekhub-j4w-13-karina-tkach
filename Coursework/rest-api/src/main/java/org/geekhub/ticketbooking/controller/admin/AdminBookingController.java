package org.geekhub.ticketbooking.controller.admin;

import org.geekhub.ticketbooking.booking.Booking;
import org.geekhub.ticketbooking.booking.BookingService;
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

    @GetMapping("/deleteBooking/{id}")
    public String deleteCity(@PathVariable(value = "id") int id, Model model) {
        /*check role
        //boolean result = bookingService.deleteBookingById(id);
        if(!result) {
            model.addAttribute("error", "Cannot delete booking by this id");
            //return "update_city";
        }
        return "redirect:/admin/bookings";*/
        return null;
    }
}
