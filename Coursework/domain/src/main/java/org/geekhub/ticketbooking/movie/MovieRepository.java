package org.geekhub.ticketbooking.movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> getAllMovies();

    Movie getMovieById(int movieId);

    Movie getMovieByTitle(String title);

    int addMovie(Movie movie);

    void updateMovieById(Movie movie, int movieId);

    void deleteMovieById(int movieId);

    List<Movie> getMoviesWithPagination(int pageNumber, int limit);

    int getMoviesRowsCount();

    List<Movie> getByTitleIgnoreCaseWithPagination(String keyword, int pageNumber, int limit);

    int getMoviesByTitleRowsCount(String keyword);
}
