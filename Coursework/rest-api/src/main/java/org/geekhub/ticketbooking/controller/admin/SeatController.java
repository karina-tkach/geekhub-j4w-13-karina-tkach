package org.geekhub.ticketbooking.controller.admin;

import org.geekhub.ticketbooking.seat.Seat;
import org.geekhub.ticketbooking.seat.SeatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("admin/seats")
@SuppressWarnings("java:S1192")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/{hallId}/{showId}")
    public String viewHallSeats(@PathVariable int hallId,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "6") int pageSize, Model model) {
        List<Seat> seats = seatService.getSeatsByHallWithPagination(hallId, page, pageSize);
        int rows = seatService.getSeatsByHallRowsCount(hallId);

        if (rows == -1 || seats.isEmpty()) {
            model.addAttribute("error", "Can't load seats for this hall");
        } else {
            model.addAttribute("page", page);
            model.addAttribute("totalPages", Math.ceil(rows / (float) pageSize));
            model.addAttribute("listSeats", seats);
        }

        return "seats";
    }
}
