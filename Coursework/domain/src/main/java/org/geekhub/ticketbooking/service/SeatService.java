package org.geekhub.ticketbooking.service;

import org.geekhub.ticketbooking.model.Seat;
import org.geekhub.ticketbooking.repository.interfaces.SeatRepository;
import org.geekhub.ticketbooking.validator.SeatValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatValidator seatValidator;

    public SeatService(SeatRepository seatRepository, SeatValidator seatValidator) {
        this.seatRepository = seatRepository;
        this.seatValidator = seatValidator;
    }
    public List<Seat> getSeatsByHall(int hallId) {
        return null;
    }

    public Seat getSeatById(int seatId) {
        return null;
    }

    public Seat getSeatByHallAndNumber(int hallId, int number) {
        return null;
    }

    public List<Seat> getSeatsByHallAndStatus(int hallId, boolean isBooked) {
        return null;
    }

    public Seat addSeat(Seat seat, int hallId) {
        return null;
    }

    public Seat updateSeatById(Seat seat, int hallId, int seatId){
        return null;
    }

    public boolean deleteSeatById(int seatId) {
        return false;
    }
}
