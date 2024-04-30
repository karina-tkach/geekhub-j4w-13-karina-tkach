package org.geekhub.ticketbooking.movie;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
    public List<Movie> getByTitleIgnoreCaseWithPagination(String keyword, int pageNumber, int limit) {
        String query = """
            SELECT * FROM movies
            WHERE title iLIKE concat('%', :keyword, '%')
            ORDER BY id
            LIMIT :limit
            OFFSET :offset
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("keyword", keyword)
            .addValue("limit", limit)
            .addValue("offset", getOffset(pageNumber, limit));

        return jdbcTemplate.query(query, mapSqlParameterSource, MovieMapper::mapToPojo);
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

    @Override
    public int getMoviesByTitleRowsCount(String keyword) {
        String query = "SELECT COUNT(*) FROM movies WHERE title iLIKE concat('%', :keyword, '%')";

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("keyword", keyword);

        return jdbcTemplate.queryForObject(query, mapSqlParameterSource, Integer.class);
    }

    private static int getOffset(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }
}
