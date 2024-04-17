package org.geekhub.ticketbooking.show;

import java.util.List;

public interface ShowRepository {
    List<Show> getAllShows();

    Show getShowById(int showId);

    List<Show> getShowsByMovie(int movieId);

    List<Show> getShowsByHall(int hallId);

    List<Show> getShowsByHallWithPagination(int hallId, int pageNumber, int limit);

    int addShow(Show show, int movieId, int hallId);

    void updateShowById(Show show, int movieId, int hallId, int showId);

    void deleteShowById(int showId);

    List<Show> getShowsWithPagination(int pageNumber, int limit);

    int getShowsRowsCount();

    int getShowsByHallRowsCount(int hallId);
}
