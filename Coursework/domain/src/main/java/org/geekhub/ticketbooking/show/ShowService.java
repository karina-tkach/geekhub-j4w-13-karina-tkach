package org.geekhub.ticketbooking.show;

import org.geekhub.ticketbooking.exception.ShowValidationException;
import org.geekhub.ticketbooking.movie.Movie;
import org.geekhub.ticketbooking.movie.MovieService;
import org.geekhub.ticketbooking.seat.Seat;
import org.geekhub.ticketbooking.seat.SeatService;
import org.geekhub.ticketbooking.show_seat.ShowSeat;
import org.geekhub.ticketbooking.show_seat.ShowSeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@SuppressWarnings({"java:S1192", "java:S3516"})
public class ShowService {
    private final ShowRepository showRepository;
    private final ShowValidator showValidator;
    private final MovieService movieService;
    private final ShowSeatService showSeatService;
    private final SeatService seatService;

    private final Logger logger = LoggerFactory.getLogger(ShowService.class);

    public ShowService(ShowRepository showRepository, ShowValidator showValidator,
                       MovieService movieService, ShowSeatService showSeatService,
                       SeatService seatService) {
        this.showRepository = showRepository;
        this.showValidator = showValidator;
        this.movieService = movieService;
        this.showSeatService = showSeatService;
        this.seatService = seatService;
    }

    public List<Show> getAllShows() {
        try {
            logger.info("Try to get shows");
            List<Show> shows = showRepository.getAllShows();

            if (shows != null) {
                for (Show show : shows) {
                    setShowSeats(show);
                }
            }

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

            if (show != null) {
                setShowSeats(show);
            }

            logger.info("Show was fetched successfully");
            return show;
        } catch (DataAccessException exception) {
            logger.warn("Show wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public List<Show> getShowsByHall(int hallId) {
        try {
            logger.info("Try to get shows by hall");
            List<Show> shows = showRepository.getShowsByHall(hallId);

            if (shows != null) {
                for (Show show : shows) {
                    setShowSeats(show);
                }
            }

            logger.info("Shows by hall were fetched successfully");
            return shows;
        } catch (DataAccessException exception) {
            logger.warn("Shows by hall weren't fetched\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Show> getShowsByHallWithPagination(int hallId, int pageNumber, int limit) {
        try {
            logger.info("Try to get shows by hall with pagination");
            List<Show> shows = showRepository.getShowsByHallWithPagination(hallId, pageNumber, limit);

            if (shows != null) {
                for (Show show : shows) {
                    setShowSeats(show);
                }
            }

            logger.info("Shows by hall were fetched successfully with pagination");
            return shows;
        } catch (DataAccessException exception) {
            logger.warn("Shows by hall weren't fetched with pagination\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public Show addShow(Show show, int hallId) {
        try {
            logger.info("Try to add show");
            Movie movie = movieService.getMovieByTitle(show.getMovie().getTitle());

            if (movie == null) {
                throw new ShowValidationException("Show movie was incorrect");
            }

            show.setMovie(movie);
            showValidator.validate(show);

            int movieId = movie.getId();

            int id = showRepository.addShow(show, movieId, hallId);
            if (id == -1) {
                throw new ShowValidationException("Unable to retrieve the generated key");
            }

            addShowSeats(id, hallId);

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


            Movie movie = movieService.getMovieByTitle(show.getMovie().getTitle());

            if (movie == null) {
                throw new ShowValidationException("Show movie was incorrect");
            }
            int movieId = movie.getId();
            show.setMovie(movie);

            showValidator.validate(show);

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

            if (shows != null) {
                for (Show show : shows) {
                    setShowSeats(show);
                }
            }

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

    private void setShowSeats(Show show) {
        List<ShowSeat> seats = showSeatService.getSeatsByHallAndShow(show.getHallId(), show.getId());
        show.setSeats(seats);
    }

    private void addShowSeats(int showId, int hallId) {
        List<Seat> seats = seatService.getSeatsByHall(hallId);

        for (Seat seat : seats) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setNumber(seat.getNumber());
            showSeat.setHallId(hallId);
            showSeat.setShowId(showId);
            showSeatService.addSeat(showSeat, hallId, showId);
        }
    }
}
