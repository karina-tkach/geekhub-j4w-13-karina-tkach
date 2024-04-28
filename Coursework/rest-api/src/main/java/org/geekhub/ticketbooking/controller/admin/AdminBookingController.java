package org.geekhub.ticketbooking.controller.admin;

import org.geekhub.ticketbooking.booking.Booking;
import org.geekhub.ticketbooking.booking.BookingService;
import org.geekhub.ticketbooking.user.Role;
import org.geekhub.ticketbooking.user.User;
import org.geekhub.ticketbooking.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("admin/bookings")
public class AdminBookingController {
    private final BookingService bookingService;
    private final UserService userService;

    public AdminBookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
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
    public String deleteBooking(@PathVariable(value = "id") int id, Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        if(user.getRole() != Role.SUPER_ADMIN) {
            return setAttributesAndGetProperPage(model, "Only super admin can delete bookings");
        }

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
