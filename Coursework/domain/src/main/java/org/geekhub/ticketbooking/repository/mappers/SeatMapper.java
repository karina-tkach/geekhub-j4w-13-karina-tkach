package org.geekhub.ticketbooking.repository.mappers;

import org.geekhub.ticketbooking.model.Seat;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeatMapper {
    private SeatMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static Seat mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new Seat(rs.getInt("id"),
            rs.getInt("number"),
            rs.getBoolean("is_booked"),
            rs.getInt("hall_id"));
    }
}
