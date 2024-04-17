package org.geekhub.ticketbooking.controller.admin;

import org.geekhub.ticketbooking.seat.Seat;
import org.geekhub.ticketbooking.seat.SeatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/{hallId}")
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

    @GetMapping("/{hallId}/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable int hallId, @PathVariable(value = "id") int id, Model model) {
        Seat seat = seatService.getSeatById(id);
        model.addAttribute("seat", seat);
        model.addAttribute("hallId", hallId);

        if (seat == null) {
            return setAttributesAndReturnUpdatePage(model, "error",
                "Cannot get seat by this id");
        }

        return "update_seat";
    }

    @PostMapping("/{hallId}/updateSeat")
    public String updateSeat(@PathVariable int hallId, @ModelAttribute("seat") Seat seat, Model model) {
        int seatId = seat.getId();

        Seat updatedSeat = seatService.updateSeatById(seat, hallId, seatId);

        if (updatedSeat == null) {
            return setAttributesAndReturnUpdatePage(model, "message",
                "Invalid seat parameters");
        }
        return setAttributesAndReturnUpdatePage(model, "message",
            "You have successfully updated seat");
    }

    private String setAttributesAndReturnUpdatePage(Model model, String attributeName,
                                                    String attributeValue) {
        model.addAttribute(attributeName, attributeValue);
        return "update_seat";
    }
}
