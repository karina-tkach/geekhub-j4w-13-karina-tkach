package org.geekhub.ticketbooking.show_seat;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowSeatMapper {
    private ShowSeatMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static ShowSeat mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new ShowSeat(rs.getInt("id"),
            rs.getInt("number"),
            rs.getBoolean("is_booked"),
            rs.getInt("hall_id"),
            rs.getInt("show_id"));
    }
}
