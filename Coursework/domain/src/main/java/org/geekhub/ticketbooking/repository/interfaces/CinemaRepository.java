package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.model.City;

import java.util.List;

public interface CinemaRepository {
    List<Cinema> getAllCinemas();

    Cinema getCinemaById(int cinemaId);

    List<Cinema> getCinemasByCity(City city);

    Cinema getCinemaByCityAndName(City city, String name);

    int addCinema(Cinema cinema);

    void updateCinemaById(int cinemaId);

    void deleteCinemaById(int cinemaId);

}
