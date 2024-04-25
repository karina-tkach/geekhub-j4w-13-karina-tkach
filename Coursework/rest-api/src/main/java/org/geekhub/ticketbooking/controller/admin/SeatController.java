package org.geekhub.ticketbooking.controller.admin;

import org.geekhub.ticketbooking.show_seat.ShowSeat;
import org.geekhub.ticketbooking.show_seat.ShowSeatService;
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
    private final ShowSeatService showSeatService;

    public SeatController(ShowSeatService showSeatService) {
        this.showSeatService = showSeatService;
    }

    @GetMapping("/{hallId}/{showId}")
    public String viewHallSeatsForShow(@PathVariable int hallId,
                                       @PathVariable int showId,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "6") int pageSize, Model model) {
        List<ShowSeat> seats = showSeatService.getSeatsByHallAndShowWithPagination(hallId, showId, page, pageSize);
        int rows = showSeatService.getSeatsByHallAndShowRowsCount(hallId, showId);

        if (rows == -1 || seats.isEmpty()) {
            model.addAttribute("error", "Can't load seats for this hall and show");
        } else {
            model.addAttribute("page", page);
            model.addAttribute("totalPages", Math.ceil(rows / (float) pageSize));
            model.addAttribute("listSeats", seats);
        }

        return "seats";
    }

    @GetMapping("/{hallId}/{showId}/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable int hallId, @PathVariable int showId, @PathVariable(value = "id") int id, Model model) {
        ShowSeat seat = showSeatService.getSeatById(id);
        model.addAttribute("seat", seat);

        if (seat == null) {
            return setAttributesAndReturnUpdatePage(model, "error",
                "Cannot get seat by this id");
        }

        return "update_seat";
    }

    @PostMapping("/{hallId}/{showId}/updateSeat")
    public String updateSeat(@PathVariable int hallId, @PathVariable int showId, @ModelAttribute("seat") ShowSeat seat, Model model) {
        int seatId = seat.getId();

        ShowSeat updatedSeat = showSeatService.updateSeatById(seat, seatId, hallId, showId);

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
