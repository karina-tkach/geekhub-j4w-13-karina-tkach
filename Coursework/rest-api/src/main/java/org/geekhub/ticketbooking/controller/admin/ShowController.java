package org.geekhub.ticketbooking.controller.admin;

import org.geekhub.ticketbooking.cinema.Cinema;
import org.geekhub.ticketbooking.cinema.CinemaService;
import org.geekhub.ticketbooking.hall.Hall;
import org.geekhub.ticketbooking.hall.HallService;
import org.geekhub.ticketbooking.movie.Movie;
import org.geekhub.ticketbooking.movie.MovieService;
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
@SuppressWarnings("java:S1192")
public class ShowController {
    private final ShowService showService;
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final HallService hallService;

    public ShowController(ShowService showService, CinemaService cinemaService, MovieService movieService, HallService hallService) {
        this.showService = showService;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
        this.hallService = hallService;
    }

    @GetMapping
    public String viewHomePageWithPagination(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "3") int pageSize, Model model) {
        List<Show> shows = showService.getShowsWithPagination(page, pageSize);
        int rows = showService.getShowsRowsCount();

        return setAttributesAndReturnMainPage(shows, rows, model, "Can't load shows",
            page, pageSize, "shows");
    }

    @GetMapping("/{hallId}")
    public String viewHomePageWithPagination(@PathVariable int hallId, @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "3") int pageSize, Model model) {
        List<Show> shows = showService.getShowsByHallWithPagination(hallId, page, pageSize);
        int rows = showService.getShowsByHallRowsCount(hallId);

        int cinemaId = hallService.getHallById(hallId).getCinemaId();
        model.addAttribute("cinemaId", cinemaId);

        return setAttributesAndReturnMainPage(shows, rows, model, "Can't load shows for this hall",
            page, pageSize, "halls_shows");
    }

    @GetMapping("/showNewShowForm")
    public String showNewShowForm(Model model) {
        Show show = new Show();

        model.addAttribute("show", show);
        setMoviesCinemasAndHallsToSelect(model);

        return "new_show";
    }

    @PostMapping(value = "/saveShow")
    public String saveShow(@ModelAttribute("show") Show show, Model model) {
        setMoviesCinemasAndHallsToSelect(model);

        Show addedShow = showService.addShow(show, show.getHallId());
        if (addedShow == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid show parameters", "new_show");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully added show", "new_show");
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        Show show = showService.getShowById(id);

        if (show == null) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot get show by this id", "update_show");
        }

        setMovies(model);
        model.addAttribute("show", show);

        return "update_show";
    }

    @GetMapping("{hallId}/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "hallId") int hallId, @PathVariable(value = "id") int id, Model model) {
        Show show = showService.getShowById(id);

        if (show == null) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot get show by this id", "hall_update_show");
        }

        setMovies(model);
        setHallsSelect(model, hallId);
        model.addAttribute("show", show);
        model.addAttribute("hallRedirect", "");

        return "hall_update_show";
    }

    @PostMapping("/updateShow")
    public String updateShow(@ModelAttribute("show") Show show, Model model) {
        int showId = show.getId();
        setMovies(model);
        setHallsSelect(model, show.getHallId());

        Show updatedShow = showService.updateShowById(show, show.getHallId(), showId);

        if (updatedShow == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid show parameters", "update_show");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully updated show", "update_show");
    }

    @PostMapping("{hallId}/updateShow")
    public String updateShow(@PathVariable(value = "hallId") int hallId, @ModelAttribute("show") Show show, Model model) {
        int showId = show.getId();
        setMovies(model);
        setHallsSelect(model, hallId);

        Show updatedShow = showService.updateShowById(show, show.getHallId(), showId);

        if (updatedShow == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid show parameters", "hall_update_show");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully updated show", "hall_update_show");
    }

    @GetMapping("/deleteShow/{id}")
    public String deleteShow(@PathVariable(value = "id") int id, Model model) {
        boolean result = showService.deleteShowById(id);

        if (!result) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot delete show by this id", "update_show");
        }
        return "redirect:/admin/shows";
    }

    @GetMapping("{hallId}/deleteShow/{id}")
    public String deleteShow(@PathVariable(value = "hallId") int hallId, @PathVariable(value = "id") int id, Model model) {
        boolean result = showService.deleteShowById(id);

        if (!result) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot delete show by this id", "update_show");
        }
        return "redirect:/admin/shows/" + hallId;
    }

    private String setAttributesAndGetProperPage(Model model, String attributeName,
                                                 String attributeValue, String page) {
        model.addAttribute(attributeName, attributeValue);
        return page;
    }

    private String setAttributesAndReturnMainPage(List<Show> shows, int rows, Model model,
                                                  String message, int page, int pageSize,
                                                  String pageName) {
        if (rows == -1 || shows.isEmpty()) {
            model.addAttribute("error", message);
        } else {
            model.addAttribute("page", page);
            model.addAttribute("totalPages", Math.ceil(rows / (float) pageSize));
            model.addAttribute("listShows", shows);
        }

        return pageName;
    }

    private void setMoviesCinemasAndHallsToSelect(Model model) {
        List<Cinema> cinemas = cinemaService.getAllCinemas();
        Map<Integer, List<Hall>> hallsByCinema = new HashMap<>();

        for (Cinema cinema : cinemas) {
            //hallsByCinema.put(cinema.getId(), cinema.getHalls());
        }

        setMovies(model);
        model.addAttribute("cinemas", cinemas);
        model.addAttribute("hallsByCinema", hallsByCinema);
    }

    private void setMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();

        model.addAttribute("movies", movies);
    }

    private void setHallsSelect(Model model, int hallId) {
        Hall hall = hallService.getHallById(hallId);
        List<Hall> halls = hallService.getHallsByCinema(hall.getCinemaId());
        model.addAttribute("halls", halls);
    }
}
