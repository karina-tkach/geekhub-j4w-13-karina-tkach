package org.geekhub.ticketbooking.hall;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HallMapper {
    private HallMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static Hall mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new Hall(rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("rows"),
            rs.getInt("columns"),
            rs.getInt("cinema_id"));
    }
}
