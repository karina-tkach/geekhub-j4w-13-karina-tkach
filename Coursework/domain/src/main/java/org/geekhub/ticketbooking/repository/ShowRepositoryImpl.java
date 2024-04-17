package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.Show;
import org.geekhub.ticketbooking.repository.interfaces.ShowRepository;
import org.geekhub.ticketbooking.repository.mappers.ShowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ShowRepositoryImpl implements ShowRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ShowRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Show> getAllShows() {
        String query = """
            SELECT shows.id, shows.price, shows.start_time, shows.end_time,
            shows.movie_id, movies.title, movies.description, movies.duration,
             movies.releaseDate, movies.country, movies.ageLimit, movies.genres FROM shows
            INNER JOIN movies ON shows.movie_id = movies.id ORDER BY shows.id
            """;
        return jdbcTemplate.query(query, ShowMapper::mapToPojo);
    }

    @Override
    public Show getShowById(int showId) {
        String query = """
            SELECT shows.id, shows.price, shows.start_time, shows.end_time,
            shows.movie_id, movies.title, movies.description, movies.duration,
             movies.releaseDate, movies.country, movies.ageLimit, movies.genres, shows.hall_id FROM shows
            INNER JOIN movies ON shows.movie_id = movies.id WHERE shows.id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", showId);

        return jdbcTemplate.query(query, mapSqlParameterSource, ShowMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Show> getShowsByMovie(int movieId) {
        String query = """
            SELECT shows.id, shows.price, shows.start_time, shows.end_time,
            shows.movie_id, movies.title, movies.description, movies.duration,
             movies.releaseDate, movies.country, movies.ageLimit, movies.genres, shows.hall_id FROM shows
            INNER JOIN movies ON shows.movie_id = movies.id WHERE shows.movie_id=:id ORDER BY shows.id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", movieId);

        return jdbcTemplate.query(query, mapSqlParameterSource, ShowMapper::mapToPojo);
    }

    @Override
    public List<Show> getShowsByHall(int hallId) {
        String query = """
            SELECT shows.id, shows.price, shows.start_time, shows.end_time,
            shows.movie_id, movies.title, movies.description, movies.duration,
             movies.releaseDate, movies.country, movies.ageLimit, movies.genres, shows.hall_id FROM shows
            INNER JOIN movies ON shows.movie_id = movies.id WHERE shows.hall_id=:id ORDER BY shows.id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", hallId);

        return jdbcTemplate.query(query, mapSqlParameterSource, ShowMapper::mapToPojo);
    }

    @Override
    public int addShow(Show show, int movieId, int hallId) {
        String query = """
            INSERT INTO shows (price, start_time, end_time, movie_id, hall_id)
            VALUES (:price,:startTime, :endTime, :movieId, :hallId)
            """;


        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("price", show.getPrice())
            .addValue("startTime", Timestamp.from(show.getStart().toInstant()))
            .addValue("endTime", Timestamp.from(show.getEnd().toInstant()))
            .addValue("movieId", movieId)
            .addValue("hallId", hallId);

        jdbcTemplate.update(query, mapSqlParameterSource, generatedKeyHolder);

        var keys = generatedKeyHolder.getKeys();
        if (keys != null) {
            return (int) keys.get("id");
        }

        return -1;
    }

    @Override
    public void updateShowById(Show show, int movieId, int hallId, int showId) {
        String query = """
            UPDATE shows SET
            price=:price, start_time=:startTime,
             end_time=:endTime, movie_id=:movieId, hall_id=:hallId
            WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("price", show.getPrice())
            .addValue("startTime", Timestamp.from(show.getStart().toInstant()))
            .addValue("endTime", Timestamp.from(show.getEnd().toInstant()))
            .addValue("movieId", movieId)
            .addValue("hallId", hallId)
            .addValue("id", showId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void deleteShowById(int showId) {
        String query = """
            DELETE FROM shows WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", showId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }
}
