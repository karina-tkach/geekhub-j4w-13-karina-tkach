package org.geekhub.ticketbooking.controller;

import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.model.Hall;
import org.geekhub.ticketbooking.service.HallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/halls")
@SuppressWarnings("java:S1192")
public class HallController {
    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping("/{cinemaId}")
    public String viewCinemaHalls(@PathVariable int cinemaId, Model model) {
        List<Hall> halls = hallService.getHallsByCinema(cinemaId);
        if (halls.isEmpty()) {
            model.addAttribute("error", "Can't load halls for this cinema");
        } else {
            model.addAttribute("listHalls", halls);
        }

        return "halls";
    }

    @GetMapping("/{cinemaId}/showNewHallForm")
    public String showNewHallForm(@PathVariable int cinemaId, Model model) {
        Hall hall = new Hall();
        model.addAttribute("cinemaId", cinemaId);
        model.addAttribute("hall", hall);
        return "new_hall";
    }

    @PostMapping("/{cinemaId}/saveHall")
    public String saveHall(@PathVariable int cinemaId, @ModelAttribute("hall") Hall hall, Model model) {
        Hall addedHall = hallService.addHall(hall, cinemaId);

        if (addedHall == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid hall parameters", "new_hall");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully added hall", "new_hall");
    }

    @GetMapping("/{cinemaId}/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable int cinemaId, @PathVariable(value = "id") int id, Model model) {
        Hall hall = hallService.getHallById(id);
        model.addAttribute("hall", hall);
        model.addAttribute("cinemaId", cinemaId);

        if (hall == null) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot get hall by this id", "update_hall");
        }

        return "update_hall";
    }

    @PostMapping("/{cinemaId}/updateHall")
    public String updateCinema(@PathVariable int cinemaId, @ModelAttribute("hall") Hall hall, Model model) {
        int hallId = hall.getId();

        Hall updatedHall = hallService.updateHallById(hall, cinemaId, hallId);

        if (updatedHall == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid hall parameters", "update_hall");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully updated hall", "update_hall");
    }

    @GetMapping("/{cinemaId}/deleteCinema/{id}")
    public String deleteCity(@PathVariable int cinemaId, @PathVariable(value = "id") int id, Model model) {
        boolean result = hallService.deleteHallById(id);
        if (!result) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot delete hall by this id", "update_hall");
        }
        return "redirect:/admin/halls/" + cinemaId;
    }

    private String setAttributesAndGetProperPage(Model model, String attributeName,
                                                 String attributeValue, String page) {
        model.addAttribute(attributeName, attributeValue);
        return page;
    }
}
