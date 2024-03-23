package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.Hall;
import org.geekhub.ticketbooking.repository.interfaces.HallRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HallRepositoryImpl implements HallRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public HallRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Hall> getAllHalls() {
        return null;
    }

    @Override
    public Hall getHallById(int hallId) {
        return null;
    }

    @Override
    public List<Hall> getHallsByCinema(int cinemaId) {
        return null;
    }

    @Override
    public int addHall(Hall hall, int cinemaId) {
        return 0;
    }

    @Override
    public void updateHallById(Hall hall, int cinemaId, int hallId) {

    }

    @Override
    public void deleteHallById(int hallId) {

    }
}
