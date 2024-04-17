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
    public Seat getSeatByHallAndNumber(int hallId, int number) {
        String query = """
            SELECT * FROM seats WHERE id=:id AND number=:number
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", hallId)
            .addValue("number", number);

        return jdbcTemplate.query(query, mapSqlParameterSource, SeatMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Seat> getSeatsByHallAndStatus(int hallId, boolean isBooked) {
        String query = """
            SELECT * FROM seats WHERE hall_id=:id AND is_booked=:isBooked ORDER BY id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", hallId)
            .addValue("isBooked", isBooked);

        return jdbcTemplate.query(query, mapSqlParameterSource, SeatMapper::mapToPojo);
    }

    @Override
    public int addSeat(Seat seat, int hallId) {
        String query = """
            INSERT INTO seats (number, is_booked, hall_id)
            VALUES (:number, :isBooked, :hallId)
            """;

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("number", seat.getNumber())
            .addValue("isBooked", seat.isBooked())
            .addValue("hallId", hallId);

        jdbcTemplate.update(query, mapSqlParameterSource, generatedKeyHolder);

        var keys = generatedKeyHolder.getKeys();
        if (keys != null) {
            return (int) keys.get("id");
        }

        return -1;
    }

    @Override
    public void updateSeatById(Seat seat, int hallId, int seatId) {
        String query = """
            UPDATE seats SET
            number=:number, is_booked=:isBooked, hall_id=:hallId WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("number", seat.getNumber())
            .addValue("isBooked", seat.isBooked())
            .addValue("hallId", hallId)
            .addValue("id", seatId);

        jdbcTemplate.update(query, mapSqlParameterSource);
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

    @Override
    public List<Seat> getSeatsByHallWithPagination(int hallId, int pageNumber, int limit) {
        String query = """
            SELECT * FROM seats WHERE hall_id=:id
            ORDER BY id
            LIMIT :limit
            OFFSET :offset
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", hallId)
            .addValue("limit", limit)
            .addValue("offset", getOffset(pageNumber, limit));

        return jdbcTemplate.query(query, mapSqlParameterSource, SeatMapper::mapToPojo);
    }

    @Override
    public int getSeatsByHallRowsCount(int hallId) {
        String query = "SELECT COUNT(*) FROM seats WHERE hall_id=:id";

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", hallId);

        return jdbcTemplate.queryForObject(query, mapSqlParameterSource, Integer.class);
    }

    private static int getOffset(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }
}
