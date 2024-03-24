package org.geekhub.ticketbooking.service;

import org.geekhub.ticketbooking.exception.ShowValidationException;
import org.geekhub.ticketbooking.model.Movie;
import org.geekhub.ticketbooking.model.Show;
import org.geekhub.ticketbooking.repository.interfaces.ShowRepository;
import org.geekhub.ticketbooking.validator.ShowValidator;
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
}
