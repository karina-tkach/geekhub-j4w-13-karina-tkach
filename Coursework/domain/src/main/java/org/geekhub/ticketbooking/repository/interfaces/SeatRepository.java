package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.Hall;
import org.geekhub.ticketbooking.model.Seat;

import java.util.List;

public interface SeatRepository {
    List<Seat> getSeatsByHall(Hall hall);

    Seat getSeatById(int seatId);

    Seat getSeatByHallAndNumber(Hall hall, int number);

    List<Seat> getSeatsByHallAndStatus(Hall hall, boolean isBooked);

    int addSeat(Seat seat);

    void updateSeatById(Seat seat, int seatId);

    void deleteSeatById(int seatId);
}
