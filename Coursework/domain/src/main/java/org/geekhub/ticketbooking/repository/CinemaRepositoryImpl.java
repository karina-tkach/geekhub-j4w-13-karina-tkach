package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.model.City;
import org.geekhub.ticketbooking.repository.interfaces.CinemaRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class CinemaRepositoryImpl implements CinemaRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CinemaRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Cinema> getAllCinemas() {
        return null;
    }

    @Override
    public Cinema getCinemaById(int cinemaId) {
        return null;
    }

    @Override
    public List<Cinema> getCinemasByCity(City city) {
        return null;
    }

    @Override
    public Cinema getCinemaByCityAndName(City city, String name) {
        return null;
    }

    @Override
    public int addCinema(Cinema cinema) {
        return 0;
    }

    @Override
    public void updateCinemaById(int cinemaId) {

    }

    @Override
    public void deleteCinemaById(int cinemaId) {

    }
}
