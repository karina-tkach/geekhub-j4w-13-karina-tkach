package org.geekhub.ticketbooking.repository.mappers;

import org.geekhub.ticketbooking.model.Genre;
import org.geekhub.ticketbooking.model.Language;
import org.geekhub.ticketbooking.model.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MovieMapper {
    private MovieMapper() {
    }
    @SuppressWarnings("java:S1172")
    public static Movie mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new Movie(rs.getInt("id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getInt("durationInMins"),
            Language.valueOf(rs.getString("language")),
            rs.getTimestamp("releaseDate").toInstant().atOffset(ZoneOffset.UTC),
            rs.getString("country"),
            rs.getInt("ageLimit"),
            Arrays.stream(rs.getString("genres").split(", "))
                .map(String::trim)
                .map(Genre::valueOf)
                .collect(Collectors.toList())
            );
    }
}
