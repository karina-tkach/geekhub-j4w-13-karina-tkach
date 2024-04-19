package org.geekhub.ticketbooking.seat;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings({"java:S1192", "java:S2259"})
public class SeatRepositoryImpl implements SeatRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SeatRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Seat> getSeatsByHall(int hallId) {
        String query = """
            SELECT * FROM seats WHERE hall_id=:id ORDER BY id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", hallId);

        return jdbcTemplate.query(query, mapSqlParameterSource, SeatMapper::mapToPojo);
    }

    @Override
    public Seat getSeatById(int seatId) {
        String query = """
            SELECT * FROM seats WHERE id=:id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", seatId);

        return jdbcTemplate.query(query, mapSqlParameterSource, SeatMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public int addSeat(Seat seat, int hallId) {
        String query = """
            INSERT INTO seats (number, hall_id)
            VALUES (:number, :hallId)
            """;

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("number", seat.getNumber())
            .addValue("hallId", hallId);

        jdbcTemplate.update(query, mapSqlParameterSource, generatedKeyHolder);

        var keys = generatedKeyHolder.getKeys();
        if (keys != null) {
            return (int) keys.get("id");
        }

        return -1;
    }

    @Override
    public void deleteSeatById(int seatId) {
        String query = """
            DELETE FROM seats WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", seatId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }
}
