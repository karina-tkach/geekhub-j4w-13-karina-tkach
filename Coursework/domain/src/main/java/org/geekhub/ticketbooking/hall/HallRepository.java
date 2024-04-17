package org.geekhub.ticketbooking.hall;

import java.util.List;

public interface HallRepository {
    Hall getHallById(int hallId);

    List<Hall> getHallsByCinema(int cinemaId);

    int addHall(Hall hall, int cinemaId);

    void updateHallById(Hall hall, int cinemaId, int hallId);

    void deleteHallById(int hallId);

    List<Hall> getHallsByCinemaWithPagination(int cinemaId, int pageNumber, int limit);

    int getHallsByCinemaRowsCount(int cinemaId);
}
