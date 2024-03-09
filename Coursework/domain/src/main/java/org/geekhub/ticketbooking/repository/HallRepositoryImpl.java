package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.model.Hall;
import org.geekhub.ticketbooking.repository.interfaces.HallRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

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
    public List<Hall> getHallsByCinema(Cinema cinema) {
        return null;
    }

    @Override
    public int addHall(Hall hall) {
        return 0;
    }

    @Override
    public void updateHallById(int hallId) {

    }

    @Override
    public void deleteHallById(int hallId) {

    }
}
