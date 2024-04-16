package org.geekhub.ticketbooking.repository.mappers;

import org.geekhub.ticketbooking.model.City;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CityMapper {
    private CityMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static City mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new City(rs.getInt("id"),
            rs.getString("name"));
    }
}
