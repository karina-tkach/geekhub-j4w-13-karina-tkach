package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.Seat;

import java.util.List;

public interface SeatRepository {
    List<Seat> getSeatsByHall(int hallId);

    Seat getSeatById(int seatId);

    Seat getSeatByHallAndNumber(int hallId, int number);

    List<Seat> getSeatsByHallAndStatus(int hallId, boolean isBooked);

    int addSeat(Seat seat, int hallId);

    void updateSeatById(Seat seat, int hallId, int seatId);

    void deleteSeatById(int seatId);
}
