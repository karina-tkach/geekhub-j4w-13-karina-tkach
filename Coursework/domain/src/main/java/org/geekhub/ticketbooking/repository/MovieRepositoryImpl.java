package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.Genre;
import org.geekhub.ticketbooking.model.Language;
import org.geekhub.ticketbooking.model.Movie;
import org.geekhub.ticketbooking.repository.interfaces.MovieRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.OffsetDateTime;
import java.util.List;

public class MovieRepositoryImpl implements MovieRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MovieRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> getAllMovies() {
        return null;
    }

    @Override
    public Movie getMovieById(int movieId) {
        return null;
    }

    @Override
    public Movie getMovieByTitle(String title) {
        return null;
    }

    @Override
    public List<Movie> getMoviesByLanguage(Language language) {
        return null;
    }

    @Override
    public List<Movie> getMoviesByGenre(Genre genre) {
        return null;
    }

    @Override
    public List<Movie> getMoviesInDateRange(OffsetDateTime from, OffsetDateTime to) {
        return null;
    }

    @Override
    public int addMovie(Movie movie) {
        return 0;
    }

    @Override
    public void updateMovieById(int movieId) {

    }

    @Override
    public void deleteMovieById(int movieId) {

    }
}
