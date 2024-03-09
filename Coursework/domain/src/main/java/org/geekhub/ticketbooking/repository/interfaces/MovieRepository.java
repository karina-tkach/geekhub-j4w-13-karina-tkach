package org.geekhub.ticketbooking.repository.interfaces;


import org.geekhub.ticketbooking.model.Genre;
import org.geekhub.ticketbooking.model.Language;
import org.geekhub.ticketbooking.model.Movie;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.util.List;

public interface MovieRepository {
    List<Movie> getAllMovies();

    Movie getMovieById(int movieId);

    Movie getMovieByTitle(String title);

    List<Movie> getMoviesByLanguage(Language language);

    List<Movie> getMoviesByGenre(Genre genre);

    List<Movie> getMoviesInDateRange(@Nullable OffsetDateTime from, @Nullable OffsetDateTime to);

    int addMovie(Movie movie);

    void updateMovieById(int movieId);

    void deleteMovieById(int movieId);
}
