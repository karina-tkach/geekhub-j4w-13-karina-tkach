package org.geekhub.ticketbooking.seat;

import org.geekhub.ticketbooking.exception.SeatValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatValidator seatValidator;

    private final Logger logger = LoggerFactory.getLogger(SeatService.class);

    public SeatService(SeatRepository seatRepository, SeatValidator seatValidator) {
        this.seatRepository = seatRepository;
        this.seatValidator = seatValidator;
    }

    public List<Seat> getSeatsByHall(int hallId) {
        try {
            logger.info("Try to get seats by hall");
            List<Seat> seats = seatRepository.getSeatsByHall(hallId);
            logger.info("Seats were fetched successfully");
            return seats;
        } catch (DataAccessException exception) {
            logger.warn("Seats weren't fetched\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public Seat getSeatById(int seatId) {
        try {
            logger.info("Try to get seat by id");
            Seat seat = seatRepository.getSeatById(seatId);
            logger.info("Seat was fetched successfully");
            return seat;
        } catch (DataAccessException exception) {
            logger.warn("Seat wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public Seat addSeat(Seat seat, int hallId) {
        try {
            logger.info("Try to add seat");
            seatValidator.validate(seat);

            int id = seatRepository.addSeat(seat, hallId);
            if (id == -1) {
                throw new SeatValidationException("Unable to retrieve the generated key");
            }

            logger.info("Seat was added:\n{}", seat);
            return getSeatById(id);
        } catch (SeatValidationException | DataAccessException exception) {
            logger.warn("Seat wasn't added: {}\n{}", seat, exception.getMessage());
            return null;
        }
    }

    public boolean deleteSeatById(int seatId) {
        Seat seatToDel = getSeatById(seatId);
        try {
            logger.info("Try to delete seat");
            if (seatToDel == null) {
                throw new SeatValidationException("Seat with id '" + seatId + "' not found");
            }

            seatRepository.deleteSeatById(seatId);
            logger.info("Seat was deleted:\n{}", seatToDel);
            return true;
        } catch (SeatValidationException | DataAccessException exception) {
            logger.warn("Seat wasn't deleted: {}\n{}", seatToDel, exception.getMessage());
            return false;
        }
    }
}
