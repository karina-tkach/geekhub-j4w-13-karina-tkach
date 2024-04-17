package org.geekhub.ticketbooking.movie;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;

@Component
public class MovieMapper {
    private MovieMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static Movie mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new Movie(rs.getInt("id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getInt("duration"),
            rs.getTimestamp("releaseDate").toInstant().atOffset(ZoneOffset.UTC),
            rs.getString("country"),
            rs.getInt("ageLimit"),
            Genre.valueOf(rs.getString("genre")),
            rs.getBytes("image")
        );
    }
}
