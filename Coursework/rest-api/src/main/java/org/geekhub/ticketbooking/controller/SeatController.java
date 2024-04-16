package org.geekhub.ticketbooking.controller;

import org.geekhub.ticketbooking.model.Hall;
import org.geekhub.ticketbooking.model.Seat;
import org.geekhub.ticketbooking.service.SeatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/seats")
@SuppressWarnings("java:S1192")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/{hallId}")
    public String viewHallSeats(@PathVariable int hallId, Model model) {
        List<Seat> seats = seatService.getSeatsByHall(hallId);

        if (seats.isEmpty()) {
            model.addAttribute("error", "Can't load seats for this hall");
        } else {
            model.addAttribute("listSeats", seats);
        }

        return "seats";
    }
}
