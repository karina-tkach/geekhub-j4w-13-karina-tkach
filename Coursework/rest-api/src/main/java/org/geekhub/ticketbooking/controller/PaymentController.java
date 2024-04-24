package org.geekhub.ticketbooking.controller;

import org.geekhub.ticketbooking.booking.Booking;
import org.geekhub.ticketbooking.show.Show;
import org.geekhub.ticketbooking.show.ShowService;
import org.geekhub.ticketbooking.show.ShowUtilityService;
import org.geekhub.ticketbooking.user.User;
import org.geekhub.ticketbooking.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collections;

@Controller
public class PaymentController {
    @Value("${stripe.api.publicKey}")
    private String publicKey;
    private final UserService userService;
    private final ShowService showService;
    private final ShowUtilityService showUtilityService;

    public PaymentController(UserService userService, ShowService showService, ShowUtilityService showUtilityService) {
        this.userService = userService;
        this.showService = showService;
        this.showUtilityService = showUtilityService;
    }

    @PostMapping("/book")
    public String book(@ModelAttribute("booking") Booking booking, Principal principal, RedirectAttributes attributes,
                       Model model) {
        //validate
        User user = userService.getUserByEmail(principal.getName());
        booking.setUser(user);
        Show show = showService.getShowById(booking.getShowId());
        String productName = showUtilityService.getShowsSelectOptions(Collections.singletonList(show)).get(show.getId());
        booking.setShowDetails(productName);

        model.addAttribute("booking", booking);
        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", show.getPrice());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("productName", productName);
        return "checkout";
    }
}
