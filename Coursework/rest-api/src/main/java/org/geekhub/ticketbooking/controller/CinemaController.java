package org.geekhub.ticketbooking.controller;

import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.model.City;
import org.geekhub.ticketbooking.service.CinemaService;
import org.geekhub.ticketbooking.service.CityService;
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
@RequestMapping("admin/cinemas")
@SuppressWarnings("java:S1192")
public class CinemaController {
    private final CinemaService cinemaService;
    private final CityService cityService;

    public CinemaController(CinemaService cinemaService, CityService cityService) {
        this.cinemaService = cinemaService;
        this.cityService = cityService;
    }

    @GetMapping
    public String viewHomePageWithPagination(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "5") int pageSize, Model model) {
        List<Cinema> cinemas = cinemaService.getCinemasWithPagination(page, pageSize);
        int rows = cinemaService.getCinemasRowsCount();
        if (rows == -1 || cinemas.isEmpty()) {
            model.addAttribute("error", "Can't load cinemas");
        } else {
            model.addAttribute("page", page);
            model.addAttribute("totalPages", Math.ceil(rows / (float) pageSize));
            model.addAttribute("listCinemas", cinemas);
        }

        return "cinemas";
    }

    @GetMapping("/showNewCinemaForm")
    public String showNewCinemaForm(Model model) {
        Cinema cinema = new Cinema();
        addCities(model);
        model.addAttribute("cinema", cinema);
        return "new_cinema";
    }

    @PostMapping("/saveCinema")
    public String saveCinema(@ModelAttribute("cinema") Cinema cinema, Model model) {
        Cinema addedCinema = cinemaService.addCinema(cinema);

        addCities(model);

        if (addedCinema == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid cinema parameters", "new_cinema");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully added cinema", "new_cinema");
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Cinema cinema = cinemaService.getCinemaById(id);
        addCities(model);
        model.addAttribute("cinema", cinema);

        if (cinema == null) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot get cinema by this id", "update_cinema");
        }

        return "update_cinema";
    }

    @PostMapping("/updateCinema")
    public String updateCinema(@ModelAttribute("cinema") Cinema cinema, Model model) {
        addCities(model);

        int cinemaId = cinema.getId();
        Cinema oldCinema = cinemaService.getCinemaById(cinemaId);
        cinema.setHalls(oldCinema.getHalls());

        Cinema updatedCinema = cinemaService.updateCinemaById(cinema, cinemaId);

        if (updatedCinema == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid cinema parameters", "update_cinema");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully updated cinema", "update_cinema");
    }

    @GetMapping("/deleteCinema/{id}")
    public String deleteCity(@PathVariable(value = "id") int id, Model model) {
        boolean result = cinemaService.deleteCinemaById(id);
        if (!result) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot delete cinema by this id", "update_cinema");
        }
        return "redirect:/admin/cinemas";
    }

    private String setAttributesAndGetProperPage(Model model, String attributeName,
                                                 String attributeValue, String page) {
        model.addAttribute(attributeName, attributeValue);
        return page;
    }

    private void addCities(Model model) {
        List<City> cities = cityService.getAllCities();
        model.addAttribute("cities", cities);
    }
}
