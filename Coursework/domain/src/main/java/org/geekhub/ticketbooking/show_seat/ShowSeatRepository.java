package org.geekhub.ticketbooking.show_seat;

import java.util.List;

public interface ShowSeatRepository {
    List<ShowSeat> getSeatsByHallAndShow(int hallId, int showId);

    ShowSeat getSeatById(int seatId);

    int addSeat(ShowSeat seat, int hallId, int showId);

    void updateSeatById(ShowSeat seat, int seatId, int hallId, int showId);

    List<ShowSeat> getSeatsByHallAndShowWithPagination(int hallId, int showId, int pageNumber, int limit);

    int getSeatsByHallAndShowRowsCount(int hallId, int showId);
}
