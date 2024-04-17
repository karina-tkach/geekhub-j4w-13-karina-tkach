package org.geekhub.ticketbooking.seat;

import java.util.List;

public interface SeatRepository {
    List<Seat> getSeatsByHall(int hallId);

    Seat getSeatById(int seatId);

    int addSeat(Seat seat, int hallId);

    void deleteSeatById(int seatId);

    List<Seat> getSeatsByHallWithPagination(int hallId, int pageNumber, int limit);

    int getSeatsByHallRowsCount(int hallId);
}
