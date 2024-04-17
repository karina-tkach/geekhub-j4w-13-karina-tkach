package org.geekhub.ticketbooking.cinema;

import java.util.List;

public interface CinemaRepository {
    List<Cinema> getAllCinemas();

    Cinema getCinemaById(int cinemaId);

    List<Cinema> getCinemasByCity(int cityId);

    int addCinema(Cinema cinema, int cityId);

    void updateCinemaById(Cinema cinema, int cinemaId, int cityId);

    void deleteCinemaById(int cinemaId);

    List<Cinema> getCinemasWithPagination(int pageNumber, int limit);

    int getCinemasRowsCount();
}
