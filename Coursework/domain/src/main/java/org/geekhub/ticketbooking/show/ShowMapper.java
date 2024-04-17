package org.geekhub.ticketbooking.show;

import org.geekhub.ticketbooking.movie.Genre;
import org.geekhub.ticketbooking.movie.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;

public class ShowMapper {
    private ShowMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static Show mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new Show(rs.getInt("id"),
            rs.getBigDecimal("price"),
            rs.getTimestamp("start_time").toInstant().atOffset(ZoneOffset.UTC),
            rs.getTimestamp("end_time").toInstant().atOffset(ZoneOffset.UTC),
            new Movie(rs.getInt("movie_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getInt("duration"),
                rs.getTimestamp("releaseDate").toInstant().atOffset(ZoneOffset.UTC),
                rs.getString("country"),
                rs.getInt("ageLimit"),
                Genre.valueOf(rs.getString("genre")),
                rs.getBytes("image")),
            rs.getInt("hall_id"),
            null);
    }
}
