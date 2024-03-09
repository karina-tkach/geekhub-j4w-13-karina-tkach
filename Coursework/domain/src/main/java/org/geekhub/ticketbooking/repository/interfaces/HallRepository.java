package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.model.Hall;

import java.util.List;

public interface HallRepository {
    List<Hall> getAllHalls();

    Hall getHallById(int hallId);

    List<Hall> getHallsByCinema(Cinema cinema);

    int addHall(Hall hall);

    void updateHallById(int hallId);

    void deleteHallById(int hallId);
}
