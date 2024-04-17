package org.geekhub.ticketbooking.hall;

import org.geekhub.ticketbooking.seat.Seat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HallMapper {
    private HallMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static Hall mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new Hall(rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("rows"),
            rs.getInt("columns"),
            initializeSeats(rs.getInt("rows") * rs.getInt("columns")),
            rs.getInt("cinema_id"),
            null);
    }

    private static List<Seat> initializeSeats(int numberOfSeats) {
        List<Seat> seatsList = new ArrayList<>(numberOfSeats);
        for (int i = 0; i < numberOfSeats; i++) {
            seatsList.add(new Seat());
        }
        return seatsList;
    }
}
