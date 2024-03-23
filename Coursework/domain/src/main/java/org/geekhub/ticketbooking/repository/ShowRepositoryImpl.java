package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.Show;
import org.geekhub.ticketbooking.repository.interfaces.ShowRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShowRepositoryImpl implements ShowRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ShowRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Show> getAllShows() {
        return null;
    }

    @Override
    public Show getShowById(int showId) {
        return null;
    }

    @Override
    public List<Show> getShowsByMovie(int movieId) {
        return null;
    }

    @Override
    public List<Show> getShowsByHall(int hallId) {
        return null;
    }

    @Override
    public List<Show> getShowsByCinema(int cinemaId) {
        return null;
    }

    @Override
    public int addShow(Show show, int hallId) {
        return 0;
    }

    @Override
    public void updateShowById(Show show, int hallId, int showId) {

    }

    @Override
    public void deleteShowById(int showId) {

    }
}
