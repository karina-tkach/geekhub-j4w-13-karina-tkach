package org.geekhub.ticketbooking.hall;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings({"java:S1192", "java:S2259"})
public class HallRepositoryImpl implements HallRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public HallRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Hall getHallById(int hallId) {
        String query = """
            SELECT * FROM halls WHERE id=:id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", hallId);

        return jdbcTemplate.query(query, mapSqlParameterSource, HallMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Hall> getHallsByCinema(int cinemaId) {
        String query = """
            SELECT * FROM halls WHERE cinema_id=:id
            ORDER BY id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", cinemaId);

        return jdbcTemplate.query(query, mapSqlParameterSource, HallMapper::mapToPojo);
    }

    @Override
    public int addHall(Hall hall, int cinemaId) {
        String query = """
            INSERT INTO halls (name, rows, columns, cinema_id)
            VALUES (:name, :rows, :columns, :cinema_id)
            """;

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("name", hall.getHallName())
            .addValue("rows", hall.getRows())
            .addValue("columns", hall.getColumns())
            .addValue("cinema_id", cinemaId);

        jdbcTemplate.update(query, mapSqlParameterSource, generatedKeyHolder);

        var keys = generatedKeyHolder.getKeys();
        if (keys != null) {
            return (int) keys.get("id");
        }

        return -1;
    }

    @Override
    public void updateHallById(Hall hall, int cinemaId, int hallId) {
        String query = """
            UPDATE halls SET
            name=:name, rows=:rows, columns=:columns, cinema_id=:cinemaId WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("name", hall.getHallName())
            .addValue("rows", hall.getRows())
            .addValue("columns", hall.getColumns())
            .addValue("cinemaId", cinemaId)
            .addValue("id", hallId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void deleteHallById(int hallId) {
        String query = """
            DELETE FROM halls WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", hallId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public List<Hall> getHallsByCinemaWithPagination(int cinemaId, int pageNumber, int limit) {
        String query = """
            SELECT * FROM halls WHERE cinema_id=:id
            ORDER BY id
            LIMIT :limit
            OFFSET :offset
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", cinemaId)
            .addValue("limit", limit)
            .addValue("offset", getOffset(pageNumber, limit));

        return jdbcTemplate.query(query, mapSqlParameterSource, HallMapper::mapToPojo);
    }

    @Override
    public int getHallsByCinemaRowsCount(int cinemaId) {
        String query = "SELECT COUNT(*) FROM halls WHERE cinema_id=:id";

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", cinemaId);

        return jdbcTemplate.queryForObject(query, mapSqlParameterSource, Integer.class);
    }

    private static int getOffset(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }
}
