package org.geekhub.ticketbooking.service;

import org.geekhub.ticketbooking.exception.HallValidationException;
import org.geekhub.ticketbooking.model.Hall;
import org.geekhub.ticketbooking.model.Seat;
import org.geekhub.ticketbooking.model.Show;
import org.geekhub.ticketbooking.repository.interfaces.HallRepository;
import org.geekhub.ticketbooking.validator.HallValidator;
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
    private final SeatService seatService;
    private final ShowService showService;
    private final Logger logger = LoggerFactory.getLogger(HallService.class);

    public HallService(HallRepository hallRepository, HallValidator hallValidator,
                       SeatService seatService, ShowService showService) {
        this.hallRepository = hallRepository;
        this.hallValidator = hallValidator;
        this.seatService = seatService;
        this.showService = showService;
    }

    public List<Hall> getAllHalls() {
        try {
            logger.info("Try to get halls");
            List<Hall> halls = hallRepository.getAllHalls();
            if (halls != null) {
                for (Hall hall : halls) {
                    setHallProperties(hall);
                }
            }
            logger.info("Halls were fetched successfully");
            return halls;
        } catch (DataAccessException exception) {
            logger.warn("Halls weren't fetched\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public Hall getHallById(int hallId) {
        try {
            logger.info("Try to get hall by id");
            Hall hall = hallRepository.getHallById(hallId);
            if (hall != null) {
                setHallProperties(hall);
            }
            logger.info("Hall was fetched successfully");
            return hall;
        } catch (DataAccessException exception) {
            logger.warn("Hall wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public List<Hall> getHallsByCinema(int cinemaId) {
        try {
            logger.info("Try to get halls by cinema");
            List<Hall> halls = hallRepository.getHallsByCinema(cinemaId);
            if (halls != null) {
                for (Hall hall : halls) {
                    setHallProperties(hall);
                }
            }
            logger.info("Halls were fetched successfully");
            return halls;
        } catch (DataAccessException exception) {
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
            setHallShows(hall, id);

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

            for (Seat seat : hallToUpdate.getSeats()) {
                seatService.deleteSeatById(seat.getId());
            }
            for (Show show : hallToUpdate.getShows()) {
                showService.deleteShowById(show.getId());
            }

            setHallSeats(hall, hallId);
            setHallShows(hall, hallId);

            hallRepository.updateHallById(hall, cinemaId, hallId);
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
            hallRepository.deleteHallById(hallId);

            logger.info("Hall was deleted:\n{}", hallToDel);
            return true;
        } catch (HallValidationException | DataAccessException exception) {
            logger.warn("Hall wasn't deleted: {}\n{}", hallToDel, exception.getMessage());
            return false;
        }
    }

    private void setHallSeats(Hall hall, int hallId) {
        List<Seat> hallSeats = hall.getSeats();
        for (Seat seat : hallSeats) {
            seatService.addSeat(seat, hallId);
        }
    }

    private void setHallShows(Hall hall, int hallId) {
        List<Show> hallShows = hall.getShows();
        if (hallShows != null) {
            for (Show show : hallShows) {
                showService.addShow(show, hallId);
            }
        }
    }

    private void setHallProperties(Hall hall) {
        List<Seat> seats = seatService.getSeatsByHall(hall.getId());
        List<Seat> hallSeats = hall.getSeats();
        for (int i = 0; i < seats.size(); i++) {
            hallSeats.set(i, seats.get(i));
        }

        List<Show> shows = showService.getShowsByHall(hall.getId());
        hall.setShows(shows);
    }
}
