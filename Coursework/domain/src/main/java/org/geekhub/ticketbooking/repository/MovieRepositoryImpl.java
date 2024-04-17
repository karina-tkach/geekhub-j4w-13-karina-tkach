package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.Genre;
import org.geekhub.ticketbooking.model.Movie;
import org.geekhub.ticketbooking.repository.interfaces.MovieRepository;
import org.geekhub.ticketbooking.repository.mappers.MovieMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
@SuppressWarnings({"java:S1192", "java:S2259"})
public class MovieRepositoryImpl implements MovieRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MovieRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> getAllMovies() {
        String query = """
            SELECT * FROM movies ORDER BY id
            """;
        return jdbcTemplate.query(query, MovieMapper::mapToPojo);
    }

    @Override
    public Movie getMovieById(int movieId) {
        String query = """
            SELECT * FROM movies WHERE id=:id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", movieId);

        return jdbcTemplate.query(query, mapSqlParameterSource, MovieMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public Movie getMovieByTitle(String title) {
        String query = """
            SELECT * FROM movies WHERE title=:title
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("title", title);

        return jdbcTemplate.query(query, mapSqlParameterSource, MovieMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Movie> getMoviesByGenre(Genre genre) {
        String query = """
            SELECT * FROM movies WHERE genre=:genre ORDER BY id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("genre", genre.toString());

        return jdbcTemplate.query(query, mapSqlParameterSource, MovieMapper::mapToPojo);
    }

    @Override
    public List<Movie> getMoviesInDateRange(OffsetDateTime from, OffsetDateTime to) {
        Timestamp fromTime = (from == null) ? null : Timestamp.from(from.toInstant());
        Timestamp toTime = (to == null) ? null : Timestamp.from(to.toInstant());

        String query = """
            SELECT * FROM movies
            WHERE (releaseDate BETWEEN COALESCE(:from,releaseDate) AND COALESCE(:to,releaseDate))
            ORDER BY id
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("from", fromTime)
            .addValue("to", toTime);

        return jdbcTemplate.query(query, parameters, MovieMapper::mapToPojo);
    }

    @Override
    public int addMovie(Movie movie) {
        String query = """
            INSERT INTO movies (title, description, duration, releaseDate, country, ageLimit, genre, image)
            VALUES (:title, :description, :duration, :releaseDate, :country, :ageLimit, :genre, :image)
            """;

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("title", movie.getTitle())
            .addValue("description", movie.getDescription())
            .addValue("duration", movie.getDurationInMins())
            .addValue("releaseDate", Timestamp.from(movie.getReleaseDate().toInstant()))
            .addValue("country", movie.getCountry())
            .addValue("ageLimit", movie.getAgeLimit())
            .addValue("genre", movie.getGenre().toString())
            .addValue("image", movie.getImage());
        jdbcTemplate.update(query, mapSqlParameterSource, generatedKeyHolder);

        var keys = generatedKeyHolder.getKeys();
        if (keys != null) {
            return (int) keys.get("id");
        }

        return -1;
    }

    @Override
    public void updateMovieById(Movie movie, int movieId) {
        String query = """
            UPDATE movies SET
            title=:title, description=:description, duration=:duration,
            releaseDate=:releaseDate, country=:country, ageLimit=:ageLimit, genre=:genre, image=:image
            WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("title", movie.getTitle())
            .addValue("description", movie.getDescription())
            .addValue("duration", movie.getDurationInMins())
            .addValue("releaseDate", Timestamp.from(movie.getReleaseDate().toInstant()))
            .addValue("country", movie.getCountry())
            .addValue("ageLimit", movie.getAgeLimit())
            .addValue("genre", movie.getGenre().toString())
            .addValue("image", movie.getImage())
            .addValue("id", movieId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void deleteMovieById(int movieId) {
        String query = """
            DELETE FROM movies WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", movieId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public List<Movie> getMoviesWithPagination(int pageNumber, int limit) {
        String query = """
            SELECT * FROM movies
            ORDER BY id
            LIMIT :limit
            OFFSET :offset
            """;
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("limit", limit)
            .addValue("offset", getOffset(pageNumber, limit));

        return jdbcTemplate.query(query, parameters, MovieMapper::mapToPojo);
    }

    @Override
    public int getMoviesRowsCount() {
        String query = "SELECT COUNT(*) FROM movies";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(), Integer.class);
    }

    private static int getOffset(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }
}
