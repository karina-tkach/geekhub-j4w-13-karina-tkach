package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.Show;

import java.util.List;

public interface ShowRepository {
    List<Show> getAllShows();

    Show getShowById(int showId);

    List<Show> getShowsByMovie(int movieId);

    List<Show> getShowsByHall(int hallId);

    List<Show> getShowsByCinema(int cinemaId);

    int addShow(Show show, int hallId);

    void updateShowById(Show show, int hallId, int showId);

    void deleteShowById(int showId);
}
