package org.geekhub.ticketbooking.show_seat;

import org.geekhub.ticketbooking.exception.SeatValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ShowSeatService {
    private final ShowSeatRepository seatRepository;
    private final ShowSeatValidator seatValidator;

    private final Logger logger = LoggerFactory.getLogger(ShowSeatService.class);

    public ShowSeatService(ShowSeatRepository seatRepository, ShowSeatValidator seatValidator) {
        this.seatRepository = seatRepository;
        this.seatValidator = seatValidator;
    }

    public List<ShowSeat> getSeatsByHallAndShow(int hallId, int showId) {
        try {
            logger.info("Try to get show seats by hall and show");
            List<ShowSeat> seats = seatRepository.getSeatsByHallAndShow(hallId, showId);
            logger.info("Show seats by hall and show were fetched successfully");
            return seats;
        } catch (DataAccessException exception) {
            logger.warn("Show seats by hall and show weren't fetched\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public ShowSeat getSeatById(int seatId) {
        try {
            logger.info("Try to get show seat by id");
            ShowSeat seat = seatRepository.getSeatById(seatId);
            logger.info("Show seat was fetched successfully");
            return seat;
        } catch (DataAccessException exception) {
            logger.warn("Show seat wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public ShowSeat addSeat(ShowSeat seat, int hallId, int showId) {
        try {
            logger.info("Try to add show seat");
            seatValidator.validate(seat);

            int id = seatRepository.addSeat(seat, hallId, showId);
            if (id == -1) {
                throw new SeatValidationException("Unable to retrieve the generated key");
            }

            logger.info("Show seat was added:\n{}", seat);
            return getSeatById(id);
        } catch (SeatValidationException | DataAccessException exception) {
            logger.warn("Show seat wasn't added: {}\n{}", seat, exception.getMessage());
            return null;
        }
    }

    public ShowSeat updateSeatById(ShowSeat seat, int seatId, int hallId, int showId) {
        ShowSeat seatToUpdate = getSeatById(seatId);
        try {
            logger.info("Try to update show seat");
            if (seatToUpdate == null) {
                throw new SeatValidationException("Show seat with id '" + seatId + "' not found");
            }

            if (seatToUpdate.isBooked()) {
                throw new SeatValidationException("Cannot update booked seat with id:" + seatId);
            }

            seatValidator.validate(seat);

            seatRepository.updateSeatById(seat, seatId, hallId, showId);
            logger.info("Show seat was updated:\n{}", seat);
            return getSeatById(seatId);
        } catch (SeatValidationException | DataAccessException exception) {
            logger.warn("Show seat wasn't updated: {}\n{}", seat, exception.getMessage());
            return null;
        }
    }

    public List<ShowSeat> getSeatsByHallAndShowWithPagination(int hallId, int showId, int pageNumber, int limit) {
        try {
            if (pageNumber < 0 || limit < 0) {
                throw new IllegalArgumentException("Page number and limit must be greater than 0");
            }
            logger.info("Try to get show seats by hall and show with pagination");
            List<ShowSeat> seats = seatRepository.getSeatsByHallAndShowWithPagination(hallId, showId,
                pageNumber, limit);
            logger.info("Show seats by hall ans show were fetched with pagination successfully");
            return seats;
        } catch (IllegalArgumentException | DataAccessException exception) {
            logger.warn("Show seats by hall and show weren't fetched with pagination\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public int getSeatsByHallAndShowRowsCount(int hallId, int showId) {
        try {
            logger.info("Try to get show seats by hall and show rows count");
            int count = seatRepository.getSeatsByHallAndShowRowsCount(hallId, showId);
            logger.info("Show seats by hall and show rows count were fetched successfully");
            return count;
        } catch (DataAccessException | NullPointerException exception) {
            logger.warn("Show seats by hall and show rows count weren't fetched\n{}", exception.getMessage());
            return -1;
        }
    }
}
