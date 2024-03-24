package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.Hall;

import java.util.List;

public interface HallRepository {
    Hall getHallById(int hallId);

    List<Hall> getHallsByCinema(int cinemaId);

    int addHall(Hall hall, int cinemaId);

    void updateHallById(Hall hall, int cinemaId, int hallId);

    void deleteHallById(int hallId);
}
