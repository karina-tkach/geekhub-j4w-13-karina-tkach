package org.geekhub.ticketbooking.show;

import org.geekhub.ticketbooking.exception.ShowValidationException;
import org.geekhub.ticketbooking.movie.Movie;
import org.geekhub.ticketbooking.movie.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@SuppressWarnings("java:S1192")
public class ShowService {
    private final ShowRepository showRepository;
    private final ShowValidator showValidator;
    private final MovieService movieService;

    private final Logger logger = LoggerFactory.getLogger(ShowService.class);

    public ShowService(ShowRepository showRepository, ShowValidator showValidator, MovieService movieService) {
        this.showRepository = showRepository;
        this.showValidator = showValidator;
        this.movieService = movieService;
    }

    public List<Show> getAllShows() {
        try {
            logger.info("Try to get shows");
            List<Show> shows = showRepository.getAllShows();
            logger.info("Shows were fetched successfully");
            return shows;
        } catch (DataAccessException exception) {
            logger.warn("Shows weren't fetched\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public Show getShowById(int showId) {
        try {
            logger.info("Try to get show by id");
            Show show = showRepository.getShowById(showId);
            logger.info("Show was fetched successfully");
            return show;
        } catch (DataAccessException exception) {
            logger.warn("Show wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public List<Show> getShowsByMovie(int movieId) {
        try {
            logger.info("Try to get shows by movie");
            List<Show> shows = showRepository.getShowsByMovie(movieId);
            logger.info("Shows were fetched successfully");
            return shows;
        } catch (DataAccessException exception) {
            logger.warn("Shows weren't fetched\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Show> getShowsByHall(int hallId) {
        try {
            logger.info("Try to get shows by hall");
            List<Show> shows = showRepository.getShowsByHall(hallId);
            logger.info("Shows were fetched successfully");
            return shows;
        } catch (DataAccessException exception) {
            logger.warn("Shows weren't fetched\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Show> getShowsByHallWithPagination(int hallId, int pageNumber, int limit) {
        try {
            logger.info("Try to get shows by hall with pagination");
            List<Show> shows = showRepository.getShowsByHallWithPagination(hallId, pageNumber, limit);
            logger.info("Shows were fetched successfully with pagination");
            return shows;
        } catch (DataAccessException exception) {
            logger.warn("Shows weren't fetched with pagination\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public Show addShow(Show show, int hallId) {
        try {
            logger.info("Try to add show");
            showValidator.validate(show);

            Movie movie = movieService.getMovieByTitle(show.getMovie().getTitle());

            if (movie == null) {
                throw new ShowValidationException("Show movie was incorrect");
            }
            int movieId = movie.getId();

            int id = showRepository.addShow(show, movieId, hallId);
            if (id == -1) {
                throw new ShowValidationException("Unable to retrieve the generated key");
            }

            logger.info("Show was added:\n{}", show);
            return getShowById(id);
        } catch (ShowValidationException | DataAccessException exception) {
            logger.warn("Show wasn't added: {}\n{}", show, exception.getMessage());
            return null;
        }
    }

    public Show updateShowById(Show show, int hallId, int showId) {
        Show showToUpdate = getShowById(showId);
        try {
            logger.info("Try to update show");
            if (showToUpdate == null) {
                throw new ShowValidationException("Show with id '" + showId + "' not found");
            }
            showValidator.validate(show);

            Movie movie = movieService.getMovieByTitle(show.getMovie().getTitle());

            if (movie == null) {
                throw new ShowValidationException("Show movie was incorrect");
            }
            int movieId = movie.getId();

            showRepository.updateShowById(show, movieId, hallId, showId);
            logger.info("Show was updated:\n{}", show);
            return getShowById(showId);
        } catch (ShowValidationException | DataAccessException exception) {
            logger.warn("Show wasn't updated: {}\n{}", showId, exception.getMessage());
            return null;
        }
    }

    public boolean deleteShowById(int showId) {
        Show showToDel = getShowById(showId);
        try {
            logger.info("Try to delete show");
            if (showToDel == null) {
                throw new ShowValidationException("Show with id '" + showId + "' not found");
            }
            showRepository.deleteShowById(showId);

            logger.info("Show was deleted:\n{}", showToDel);
            return true;
        } catch (ShowValidationException | DataAccessException exception) {
            logger.warn("Show wasn't deleted: {}\n{}", showToDel, exception.getMessage());
            return false;
        }
    }

    public List<Show> getShowsWithPagination(int pageNumber, int limit) {
        try {
            if (pageNumber < 0 || limit < 0) {
                throw new IllegalArgumentException("Page number and limit must be greater than 0");
            }
            logger.info("Try to get shows with pagination");
            List<Show> shows = showRepository.getShowsWithPagination(pageNumber, limit);
            logger.info("Shows were fetched with pagination successfully");
            return shows;
        } catch (IllegalArgumentException | DataAccessException exception) {
            logger.warn("Shows weren't fetched with pagination\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public int getShowsRowsCount() {
        try {
            logger.info("Try to get shows rows count");
            int count = showRepository.getShowsRowsCount();
            logger.info("Shows rows count were fetched successfully");
            return count;
        } catch (DataAccessException | NullPointerException exception) {
            logger.warn("Shows rows count weren't fetched\n{}", exception.getMessage());
            return -1;
        }
    }

    public int getShowsByHallRowsCount(int hallId) {
        try {
            logger.info("Try to get shows by hall rows count");
            int count = showRepository.getShowsByHallRowsCount(hallId);
            logger.info("Shows by hall rows count were fetched successfully");
            return count;
        } catch (DataAccessException | NullPointerException exception) {
            logger.warn("Shows by hall rows count weren't fetched\n{}", exception.getMessage());
            return -1;
        }
    }
}
