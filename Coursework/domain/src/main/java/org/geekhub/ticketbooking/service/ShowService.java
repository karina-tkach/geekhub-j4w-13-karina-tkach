package org.geekhub.ticketbooking.service;

import org.geekhub.ticketbooking.model.Show;
import org.geekhub.ticketbooking.repository.interfaces.ShowRepository;
import org.geekhub.ticketbooking.validator.ShowValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {
    private final ShowRepository showRepository;
    private final ShowValidator showValidator;
    private final MovieService movieService;

    public ShowService(ShowRepository showRepository, ShowValidator showValidator, MovieService movieService) {
        this.showRepository = showRepository;
        this.showValidator = showValidator;
        this.movieService = movieService;
    }

    public List<Show> getAllShows() {
        return null;
    }

    public Show getShowById(int showId) {
        return null;
    }

    public List<Show> getShowsByMovie(int movieId) {
        return null;
    }

    public List<Show> getShowsByHall(int hallId) {
        return null;
    }

    public List<Show> getShowsByCinema(int cinemaId) {
        return null;
    }

    public Show addShow(Show show, int hallId) {
        return null;
    }

    public Show updateShowById(Show show, int hallId, int showId) {
        return null;
    }

    public boolean deleteShowById(int showId) {
        return false;
    }
}
