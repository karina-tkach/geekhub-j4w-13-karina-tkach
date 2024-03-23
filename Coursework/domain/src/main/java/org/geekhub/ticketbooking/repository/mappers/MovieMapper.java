package org.geekhub.ticketbooking.repository.mappers;

import org.geekhub.ticketbooking.model.Genre;
import org.geekhub.ticketbooking.model.Movie;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Component
public class MovieMapper {
    private MovieMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static Movie mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new Movie(rs.getInt("id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getInt("durationInMins"),
            rs.getTimestamp("releaseDate").toInstant().atOffset(ZoneOffset.UTC),
            rs.getString("country"),
            rs.getInt("ageLimit"),
            Arrays.stream(rs.getString("genres").split(", "))
                .map(String::trim)
                .map(Genre::valueOf)
                .toList()
        );
    }
}
