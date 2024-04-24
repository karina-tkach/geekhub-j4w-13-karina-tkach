package org.geekhub.ticketbooking.controller;

import org.geekhub.ticketbooking.booking.Booking;
import org.geekhub.ticketbooking.movie.Movie;
import org.geekhub.ticketbooking.movie.MovieService;
import org.geekhub.ticketbooking.show.Show;
import org.geekhub.ticketbooking.show.ShowService;
import org.geekhub.ticketbooking.show.ShowUtilityService;
import org.geekhub.ticketbooking.show_seat.ShowSeat;
import org.geekhub.ticketbooking.user.User;
import org.geekhub.ticketbooking.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shows")
@SuppressWarnings("java:S1192")
public class UserShowsController {
    private final MovieService movieService;
    private final ShowService showService;
    private final ShowUtilityService showUtilityService;
    private final UserService userService;

    public UserShowsController(MovieService movieService, ShowService showService,
                               ShowUtilityService showUtilityService, UserService userService) {
        this.movieService = movieService;
        this.showService = showService;
        this.showUtilityService = showUtilityService;
        this.userService = userService;
    }

    @GetMapping
    public String shows(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "12") int pageSize, Model model) {
        List<Movie> movies = movieService.getMoviesWithPagination(page, pageSize);
        int rows = movieService.getMoviesRowsCount();

        if (rows == -1 || movies.isEmpty()) {
            model.addAttribute("error", "Sorry, now shows are unavailable");
        } else {
            model.addAttribute("page", page);
            model.addAttribute("totalPages", Math.ceil(rows / (float) pageSize));
            model.addAttribute("movies", movies);
        }

        return "shows_list";
    }

    @GetMapping("/{movieId}/details")
    public String showDetails(@PathVariable int movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);

        if(movie == null) {
            model.addAttribute("error", "Can't find movie");
            return "shows_list";
        }

        List<Show> shows = showService.getShowsByMovie(movieId);
        model.addAttribute("movie", movie);

        if(shows.isEmpty()) {
            model.addAttribute("error", "Now there is no available shows for this movie");
        }
        else {
            Map<Integer, String> cinemaAndHallNames = showUtilityService.getShowsSelectOptions(shows);
            Map<Integer, List<ShowSeat>> seats = showUtilityService.getShowsSeats(shows);
            Map<Integer, List<Integer>> layout = showUtilityService.getSeatsLayoutForShow(shows);
            model.addAttribute("cinemaAndHallNames", cinemaAndHallNames);
            model.addAttribute("showSeatMap", seats);
            model.addAttribute("seatLayoutMap", layout);
            model.addAttribute("shows", shows);
            model.addAttribute("booking", new Booking());
        }

        return "show_details";
    }

    @PostMapping("/book")
    public String book(@ModelAttribute("booking") Booking booking, Principal principal, RedirectAttributes attributes) {
        //validate
        User user = userService.getUserByEmail(principal.getName());
        booking.setUser(user);
        attributes.addFlashAttribute("booking", booking);
        System.out.println(booking);
        return "redirect:/shows/xxxxxxxxx";
    }
}
