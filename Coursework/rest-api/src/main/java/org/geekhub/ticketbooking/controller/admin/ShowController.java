package org.geekhub.ticketbooking.controller.admin;

import org.geekhub.ticketbooking.cinema.Cinema;
import org.geekhub.ticketbooking.cinema.CinemaService;
import org.geekhub.ticketbooking.hall.Hall;
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

    public ShowController(ShowService showService, CinemaService cinemaService, MovieService movieService) {
        this.showService = showService;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
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
        List<Movie> movies = movieService.getAllMovies();
        List<Cinema> cinemas = cinemaService.getAllCinemas();
        Map<Integer, List<Hall>> hallsByCinema = new HashMap<>();

        for (Cinema cinema : cinemas) {
            hallsByCinema.put(cinema.getId(), cinema.getHalls());
        }

        model.addAttribute("movies", movies);
        model.addAttribute("cinemas", cinemas);
        model.addAttribute("hallsByCinema", hallsByCinema);
    }
}
