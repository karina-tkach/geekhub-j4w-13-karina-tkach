package org.geekhub.ticketbooking.hall;

import org.geekhub.ticketbooking.exception.HallValidationException;
import org.geekhub.ticketbooking.seat.Seat;
import org.geekhub.ticketbooking.seat.SeatService;
import org.geekhub.ticketbooking.show.ShowService;
import org.geekhub.ticketbooking.show.Show;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HallService {
    private final HallRepository hallRepository;
    private final HallValidator hallValidator;
    private final ShowService showService;
    private final SeatService seatService;
    private final Logger logger = LoggerFactory.getLogger(HallService.class);

    public HallService(HallRepository hallRepository, HallValidator hallValidator, ShowService showService,
                       SeatService seatService) {
        this.hallRepository = hallRepository;
        this.hallValidator = hallValidator;
        this.showService = showService;
        this.seatService = seatService;
    }

    public Hall getHallById(int hallId) {
        try {
            logger.info("Try to get hall by id");
            Hall hall = hallRepository.getHallById(hallId);
            logger.info("Hall was fetched successfully");
            return hall;
        } catch (HallValidationException | DataAccessException exception) {
            logger.warn("Hall wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public List<Hall> getHallsByCinema(int cinemaId) {
        try {
            logger.info("Try to get halls by cinema");
            List<Hall> halls = hallRepository.getHallsByCinema(cinemaId);
            logger.info("Halls were fetched successfully");
            return halls;
        } catch (HallValidationException | DataAccessException exception) {
            logger.warn("Halls weren't fetched\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public Hall addHall(Hall hall, int cinemaId) {
        try {
            logger.info("Try to add hall");
            hallValidator.validate(hall);

            int id = hallRepository.addHall(hall, cinemaId);
            if (id == -1) {
                throw new HallValidationException("Unable to retrieve the generated key");
            }

            setHallSeats(hall, id);

            logger.info("Hall was added:\n{}", hall);
            return getHallById(id);
        } catch (HallValidationException | DataAccessException exception) {
            logger.warn("Hall wasn't added: {}\n{}", hall, exception.getMessage());
            return null;
        }
    }

    public Hall updateHallById(Hall hall, int cinemaId, int hallId) {
        Hall hallToUpdate = getHallById(hallId);

        try {
            logger.info("Try to update hall");
            if (hallToUpdate == null) {
                throw new HallValidationException("Hall with id '" + hallId + "' not found");
            }

            hallValidator.validate(hall);
            hallRepository.updateHallById(hall, cinemaId, hallId);

            if (!(hallToUpdate.getRows() == hall.getRows() && hallToUpdate.getColumns() == hall.getColumns())) {
                List<Show> hallShows = showService.getShowsByHall(hallId);
                List<Seat> hallSeats = seatService.getSeatsByHall(hallToUpdate.getId());

                for (Show show : hallShows) {
                    showService.deleteShowById(show.getId());
                }
                for (Seat seat : hallSeats) {
                    seatService.deleteSeatById(seat.getId());
                }

                setHallSeats(hall, hallId);
            }
            logger.info("Hall was updated:\n{}", hall);
            return getHallById(hallId);
        } catch (HallValidationException | DataAccessException exception) {
            logger.warn("Hall wasn't updated: {}\n{}", hallId, exception.getMessage());
            return null;
        }
    }

    public boolean deleteHallById(int hallId) {
        Hall hallToDel = getHallById(hallId);
        try {
            logger.info("Try to delete hall");
            if (hallToDel == null) {
                throw new HallValidationException("Hall with id '" + hallId + "' not found");
            }

            List<Hall> halls = this.getHallsByCinema(hallToDel.getCinemaId());
            if (halls.size() == 1) {
                throw new HallValidationException("Can't delete hall with id '" + hallId + "' as it is last");
            }

            hallRepository.deleteHallById(hallId);

            logger.info("Hall was deleted:\n{}", hallToDel);
            return true;
        } catch (HallValidationException | DataAccessException exception) {
            logger.warn("Hall wasn't deleted: {}\n{}", hallToDel, exception.getMessage());
            return false;
        }
    }

    public List<Hall> getHallsByCinemaWithPagination(int cinemaId, int pageNumber, int limit) {
        try {
            if (pageNumber < 0 || limit < 0) {
                throw new IllegalArgumentException("Page number and limit must be greater than 0");
            }
            logger.info("Try to get halls by cinema with pagination");
            List<Hall> halls = hallRepository.getHallsByCinemaWithPagination(cinemaId, pageNumber, limit);
            logger.info("Halls by cinema were fetched with pagination successfully");
            return halls;
        } catch (IllegalArgumentException | DataAccessException exception) {
            logger.warn("Halls by cinema weren't fetched with pagination\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public int getHallsByCinemaRowsCount(int cinemaId) {
        try {
            logger.info("Try to get halls by cinema rows count");
            int count = hallRepository.getHallsByCinemaRowsCount(cinemaId);
            logger.info("Halls by cinema rows count were fetched successfully");
            return count;
        } catch (DataAccessException | NullPointerException exception) {
            logger.warn("Halls by cinema rows count weren't fetched\n{}", exception.getMessage());
            return -1;
        }
    }

    private void setHallSeats(Hall hall, int hallId) {
        int seatsNumber = hall.getRows() * hall.getColumns();

        for (int i = 0; i < seatsNumber; i++) {
            Seat seat = new Seat();

            seat.setNumber(i + 1);
            seat.setHallId(hallId);

            seatService.addSeat(seat, hallId);
        }
    }
}
