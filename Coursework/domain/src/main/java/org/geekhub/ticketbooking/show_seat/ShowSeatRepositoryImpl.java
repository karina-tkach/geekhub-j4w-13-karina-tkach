package org.geekhub.ticketbooking.show_seat;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings({"java:S1192", "java:S2259"})
public class ShowSeatRepositoryImpl implements ShowSeatRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ShowSeatRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ShowSeat> getSeatsByHallAndShow(int hallId, int showId) {
        String query = """
            SELECT * FROM show_seats WHERE hall_id=:hallId AND show_id=:showId ORDER BY id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("hallId", hallId)
            .addValue("showId", showId);

        return jdbcTemplate.query(query, mapSqlParameterSource, ShowSeatMapper::mapToPojo);
    }

    @Override
    public ShowSeat getSeatById(int seatId) {
        String query = """
            SELECT * FROM show_seats WHERE id=:id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", seatId);

        return jdbcTemplate.query(query, mapSqlParameterSource, ShowSeatMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public int addSeat(ShowSeat seat, int hallId, int showId) {
        String query = """
            INSERT INTO show_seats (number, is_booked, hall_id, show_id)
            VALUES (:number, :isBooked, :hallId, :showId)
            """;

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("number", seat.getNumber())
            .addValue("isBooked", seat.isBooked())
            .addValue("hallId", hallId)
            .addValue("showId", showId);

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
            DELETE FROM show_seats WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", seatId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void updateSeatById(ShowSeat seat, int seatId, int hallId, int showId) {
        String query = """
            UPDATE show_seats SET
            number=:number, is_booked=:isBooked, hall_id=:hallId, show_id=:showId WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("number", seat.getNumber())
            .addValue("isBooked", seat.isBooked())
            .addValue("hallId", hallId)
            .addValue("showId", showId)
            .addValue("id", seatId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public List<ShowSeat> getSeatsByHallAndShowWithPagination(int hallId, int showId, int pageNumber, int limit) {
        String query = """
            SELECT * FROM show_seats WHERE hall_id=:hallId AND show_id=:showId ORDER BY id
            LIMIT :limit
            OFFSET :offset
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("hallId", hallId)
            .addValue("showId", showId)
            .addValue("limit", limit)
            .addValue("offset", getOffset(pageNumber, limit));

        return jdbcTemplate.query(query, mapSqlParameterSource, ShowSeatMapper::mapToPojo);
    }

    @Override
    public int getSeatsByHallAndShowRowsCount(int hallId, int showId) {
        String query = "SELECT COUNT(*) FROM show_seats WHERE hall_id=:hallId AND show_id=:showId";

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("hallId", hallId)
            .addValue("showId", showId);

        return jdbcTemplate.queryForObject(query, mapSqlParameterSource, Integer.class);
    }

    private static int getOffset(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }
}
