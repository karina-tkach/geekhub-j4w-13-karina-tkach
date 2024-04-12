package org.geekhub.ticketbooking.repository.mappers;

import org.geekhub.ticketbooking.model.Genre;
import org.geekhub.ticketbooking.model.Movie;
import org.geekhub.ticketbooking.model.Show;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.util.Arrays;

public class ShowMapper {
    private ShowMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static Show mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new Show(rs.getInt("id"),
            rs.getInt("price"),
            rs.getTimestamp("start_time").toInstant().atOffset(ZoneOffset.UTC),
            rs.getTimestamp("end_time").toInstant().atOffset(ZoneOffset.UTC),
            new Movie(rs.getInt("movie_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getInt("duration"),
                rs.getTimestamp("releaseDate").toInstant().atOffset(ZoneOffset.UTC),
                rs.getString("country"),
                rs.getInt("ageLimit"),
                Arrays.stream(rs.getString("genres").split(", "))
                    .map(String::trim)
                    .map(Genre::valueOf)
                    .toList()),
            rs.getInt("hall_id"));
    }
}