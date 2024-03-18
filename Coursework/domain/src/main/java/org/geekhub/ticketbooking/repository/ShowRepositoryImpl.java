package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.model.Hall;
import org.geekhub.ticketbooking.model.Movie;
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
    public List<Show> getShowsByMovie(Movie movie) {
        return null;
    }

    @Override
    public List<Show> getShowsByHall(Hall hall) {
        return null;
    }

    @Override
    public List<Show> getShowsByCinema(Cinema cinema) {
        return null;
    }

    @Override
    public int addShow(Show show) {
        return 0;
    }

    @Override
    public void updateShowById(int showId) {

    }

    @Override
    public void deleteShowById(int showId) {

    }
}
