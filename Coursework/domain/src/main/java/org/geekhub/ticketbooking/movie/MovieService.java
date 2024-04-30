package org.geekhub.ticketbooking.movie;

import org.geekhub.ticketbooking.exception.MovieValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@SuppressWarnings("java:S1192")
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieValidator movieValidator;
    private final Logger logger = LoggerFactory.getLogger(MovieService.class);

    public MovieService(MovieRepository movieRepository, MovieValidator movieValidator) {
        this.movieRepository = movieRepository;
        this.movieValidator = movieValidator;
    }

    public List<Movie> getAllMovies() {
        try {
            logger.info("Try to get all movies");
            List<Movie> movies = movieRepository.getAllMovies();
            logger.info("Movies ware fetched successfully");
            return movies;
        } catch (DataAccessException exception) {
            logger.warn("Movies weren't fetched\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public Movie getMovieById(int movieId) {
        try {
            logger.info("Try to get movie by id");
            Movie movie = movieRepository.getMovieById(movieId);
            logger.info("Movie was fetched successfully");
            return movie;
        } catch (DataAccessException exception) {
            logger.warn("Movie wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public Movie getMovieByTitle(String title) {
        try {
            logger.info("Try to get movie by title");
            if (title == null) {
                throw new MovieValidationException("Title must not be null");
            }
            Movie movie = movieRepository.getMovieByTitle(title);
            logger.info("Movie was fetched by title successfully");
            return movie;
        } catch (MovieValidationException | DataAccessException exception) {
            logger.warn("Movie wasn't fetched by title\n{}", exception.getMessage());
            return null;
        }
    }

    public Movie addMovie(Movie movie) {
        try {
            logger.info("Try to add movie");
            movieValidator.validate(movie);

            boolean existsMovie = movie.equals(movieRepository.getMovieByTitle(movie.getTitle()));
            if (existsMovie) {
                throw new MovieValidationException(
                    "Movie " + movie + " already exists");
            }

            int id = movieRepository.addMovie(movie);
            if (id == -1) {
                throw new MovieValidationException("Unable to retrieve the generated key");
            }

            logger.info("Movie was added:\n{}", movie);
            return getMovieById(id);
        } catch (MovieValidationException | DataAccessException exception) {
            logger.warn("Movie wasn't added: {}\n{}", movie, exception.getMessage());
            return null;
        }
    }

    public Movie updateMovieById(Movie movie, int movieId) {
        Movie movieToUpdate = getMovieById(movieId);
        try {
            logger.info("Try to update movie");
            if (movieToUpdate == null) {
                throw new MovieValidationException("Movie with id '" + movieId + "' not found");
            }
            movieValidator.validate(movie);

            movieRepository.updateMovieById(movie, movieId);
            logger.info("Movie was updated:\n{}", movie);
            return getMovieById(movieId);
        } catch (MovieValidationException | DataAccessException exception) {
            logger.warn("Movie wasn't updated: {}\n{}", movie, exception.getMessage());
            return null;
        }
    }

    public boolean deleteMovieById(int movieId) {
        Movie movieToDel = getMovieById(movieId);
        try {
            logger.info("Try to delete movie");
            if (movieToDel == null) {
                throw new MovieValidationException("Movie with id '" + movieId + "' not found");
            }

            movieRepository.deleteMovieById(movieId);
            logger.info("Movie was deleted:\n{}", movieToDel);
            return true;
        } catch (MovieValidationException | DataAccessException exception) {
            logger.warn("Movie wasn't deleted: {}\n{}", movieToDel, exception.getMessage());
            return false;
        }
    }

    public List<Movie> getMoviesWithPagination(int pageNumber, int limit) {
        try {
            if (pageNumber < 0 || limit < 0) {
                throw new IllegalArgumentException("Page number and limit must be greater than 0");
            }
            logger.info("Try to get movies with pagination");
            List<Movie> movies = movieRepository.getMoviesWithPagination(pageNumber, limit);
            logger.info("Movies were fetched with pagination successfully");
            return movies;
        } catch (IllegalArgumentException | DataAccessException exception) {
            logger.warn("Movies weren't fetched with pagination\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public int getMoviesRowsCount() {
        try {
            logger.info("Try to get movies rows count");
            int count = movieRepository.getMoviesRowsCount();
            logger.info("Movies rows count were fetched successfully");
            return count;
        } catch (DataAccessException | NullPointerException exception) {
            logger.warn("Movies rows count weren't fetched\n{}", exception.getMessage());
            return -1;
        }
    }

    public boolean setMovieForUpdate(Movie movie, MultipartFile file) {
        Movie oldMovie = this.getMovieById(movie.getId());

        try {
            if (file.isEmpty()) {
                movie.setImage(oldMovie.getImage());
            } else {
                movie.setImage(file.getBytes());
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public List<Movie> getByTitleIgnoreCaseWithPagination(String keyword, int pageNumber, int limit) {
        try {
            logger.info("Try to get movies by title");
            if (keyword == null || keyword.isBlank()) {
                return getMoviesWithPagination(pageNumber, limit);
            }
            List<Movie> movies = movieRepository.getByTitleIgnoreCaseWithPagination(keyword, pageNumber, limit);
            logger.info("Movies by title were fetched by genre successfully");
            return movies;
        } catch (DataAccessException exception) {
            logger.warn("Movies by title weren't fetched by genre\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public int getMoviesByTitleRowsCount(String keyword) {
        try {
            logger.info("Try to get movies by title rows count");
            int count = movieRepository.getMoviesByTitleRowsCount(keyword);
            logger.info("Movies by title rows count were fetched successfully");
            return count;
        } catch (DataAccessException | NullPointerException exception) {
            logger.warn("Movies by title rows count weren't fetched\n{}", exception.getMessage());
            return -1;
        }
    }
}
