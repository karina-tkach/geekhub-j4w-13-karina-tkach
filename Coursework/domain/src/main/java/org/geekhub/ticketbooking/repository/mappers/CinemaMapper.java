package org.geekhub.ticketbooking.repository.mappers;

import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.model.City;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CinemaMapper {
    private CinemaMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static Cinema mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new Cinema(rs.getInt("id"),
            rs.getString("name"),
            new City(rs.getInt("city_id"), rs.getString("city_name")),
            rs.getString("street"),
            null);
    }
}
