package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.model.Hall;
import org.geekhub.ticketbooking.model.Movie;
import org.geekhub.ticketbooking.model.Show;

import java.util.List;

public interface ShowRepository {
    List<Show> getAllShows();

    Show getShowById(int showId);

    List<Show> getShowsByMovie(Movie movie);

    List<Show> getShowsByHall(Hall hall);

    List<Show> getShowsByCinema(Cinema cinema);

    int addShow(Show show);

    void updateShowById(int showId);

    void deleteShowById(int showId);
}
