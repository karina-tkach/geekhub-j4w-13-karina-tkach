package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.Cinema;

import java.util.List;

public interface CinemaRepository {
    List<Cinema> getAllCinemas();

    Cinema getCinemaById(int cinemaId);

    List<Cinema> getCinemasByCity(int cityId);

    Cinema getCinemaByCityAndName(int cityId, String name);

    int addCinema(Cinema cinema, int cityId);

    void updateCinemaById(Cinema cinema, int cinemaId, int cityId);

    void deleteCinemaById(int cinemaId);

}
