package org.geekhub.ticketbooking.seat;

import java.util.List;

public interface SeatRepository {
    List<Seat> getSeatsByHall(int hallId);

    Seat getSeatById(int seatId);

    Seat getSeatByHallAndNumber(int hallId, int number);

    List<Seat> getSeatsByHallAndStatus(int hallId, boolean isBooked);

    int addSeat(Seat seat, int hallId);

    void updateSeatById(Seat seat, int hallId, int seatId);

    void deleteSeatById(int seatId);

    List<Seat> getSeatsByHallWithPagination(int hallId, int pageNumber, int limit);

    int getSeatsByHallRowsCount(int hallId);
}
