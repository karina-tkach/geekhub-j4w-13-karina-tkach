package org.geekhub.ticketbooking.controller.admin;

import org.geekhub.ticketbooking.cinema.Cinema;
import org.geekhub.ticketbooking.cinema.CinemaService;
import org.geekhub.ticketbooking.hall.Hall;
import org.geekhub.ticketbooking.hall.HallService;
import org.geekhub.ticketbooking.show.Show;
import org.geekhub.ticketbooking.show.ShowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/shows")
public class ShowController {
    private final ShowService showService;
    private final CinemaService cinemaService;
    private final HallService hallService;

    public ShowController(ShowService showService, CinemaService cinemaService, HallService hallService) {
        this.showService = showService;
        this.cinemaService = cinemaService;
        this.hallService = hallService;
    }

    @GetMapping
    public String viewHomePageWithPagination(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "3") int pageSize, Model model) {
        List<Show> shows = showService.getShowsWithPagination(page, pageSize);
        int rows = showService.getShowsRowsCount();

        if (rows == -1 || shows.isEmpty()) {
            model.addAttribute("error", "Can't load shows");
        } else {
            model.addAttribute("page", page);
            model.addAttribute("totalPages", Math.ceil(rows / (float) pageSize));
            model.addAttribute("listShows", shows);
        }

        return "shows";
    }

    /*@GetMapping("/{hallId}")
    public String viewHomePageWithPagination(@PathVariable int hallId, @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "3") int pageSize, Model model) {
        List<Show> shows = showService.getShowsByHallWithPagination(hallId, page, pageSize);
        int rows = showService.getShowsByHallRowsCount(hallId);

        if (rows == -1 || shows.isEmpty()) {
            model.addAttribute("error", "Can't load shows for this hall");
        } else {
            model.addAttribute("page", page);
            model.addAttribute("totalPages", Math.ceil(rows / (float) pageSize));
            model.addAttribute("listShows", shows);
        }

        return "shows";
    }*/

    /*@GetMapping("/{hallId}/showNewShowForm")
    public String showNewShowForm(@PathVariable int hallId, Model model) {
        Show show = new Show();
        model.addAttribute("hallId", hallId);
        model.addAttribute("show", show);
        return "new_show";
    }

    @PostMapping(value = "/{hallId}/saveShow")
    public String saveMovie(@PathVariable int hallId, @ModelAttribute("show") Show show, Model model) {
        Show addedShow = showService.addShow(show, hallId);
        if (addedShow == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid show parameters", "new_show");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully added show", "new_show");
    }*/

    @GetMapping("/showNewShowForm")
    public String showNewShowForm(Model model) {
        Show show = new Show();
        List<Cinema> cinemas = cinemaService.getAllCinemas();
        Map<Integer, List<Hall>> hallsByCinema = new HashMap<>();

        for (Cinema cinema : cinemas) {
            hallsByCinema.put(cinema.getId(), cinema.getHalls());
        }

        model.addAttribute("show", show);
        model.addAttribute("cinemas", cinemas);
        model.addAttribute("hallsByCinema", hallsByCinema);

        return "new_show";
    }

    @PostMapping(value = "/saveShow")
    public String saveMovie(@PathVariable int hallId, @ModelAttribute("show") Show show, Model model) {
        Show addedShow = showService.addShow(show, hallId);
        if (addedShow == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid show parameters", "new_show");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully added show", "new_show");
    }

    private String setAttributesAndGetProperPage(Model model, String attributeName,
                                                 String attributeValue, String page) {
        model.addAttribute(attributeName, attributeValue);
        return page;
    }
}
