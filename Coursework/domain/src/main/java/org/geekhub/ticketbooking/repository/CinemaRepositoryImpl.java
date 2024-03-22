package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.Cinema;
import org.geekhub.ticketbooking.repository.interfaces.CinemaRepository;
import org.geekhub.ticketbooking.repository.mappers.CinemaMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CinemaRepositoryImpl implements CinemaRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CinemaRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//inner join
    @Override
    public List<Cinema> getAllCinemas() {
        String query = """
            SELECT cinemas.id, cinemas.name, cinemas.city_id, cities.name AS city_name, cinemas.street FROM cinemas
            INNER JOIN cities ON cinemas.city_id = cities.id ORDER BY cinemas.id
            """;
        return jdbcTemplate.query(query, CinemaMapper::mapToPojo);
    }

    @Override
    public Cinema getCinemaById(int cinemaId) {
        String query = """
            SELECT cinemas.id, cinemas.name, cinemas.city_id, cities.name, cinemas.street FROM cinemas
            INNER JOIN cities ON cinemas.city_id = cities.id WHERE cinemas.id=:id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", cinemaId);

        return jdbcTemplate.query(query, mapSqlParameterSource, CinemaMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Cinema> getCinemasByCity(int cityId) {
        String query = """
            SELECT cinemas.id, cinemas.name, cinemas.city_id, cities.name, cinemas.street FROM cinemas
            INNER JOIN cities ON cinemas.city_id = cities.id WHERE cinemas.city_id=:cityId
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("cityId", cityId);

        return jdbcTemplate.query(query, mapSqlParameterSource, CinemaMapper::mapToPojo);
    }

    @Override
    public Cinema getCinemaByCityAndName(int cityId, String name) {
        String query = """
            SELECT cinemas.id, cinemas.name, cinemas.city_id, cities.name, cinemas.street FROM cinemas
            INNER JOIN cities ON cinemas.city_id = cities.id WHERE cinemas.city_id=:cityId AND cinemas.name=:name
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("cityId", cityId)
            .addValue("name", name);

        return jdbcTemplate.query(query, mapSqlParameterSource, CinemaMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public int addCinema(Cinema cinema, int cityId) {
        String query = """
            INSERT INTO cinemas (name, city_id, street)
            VALUES (:name,:cityId, :street)
            """;


        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("name", cinema.getName())
            .addValue("cityId", cityId)
            .addValue("street", cinema.getStreet());

        jdbcTemplate.update(query, mapSqlParameterSource, generatedKeyHolder);

        var keys = generatedKeyHolder.getKeys();
        if (keys != null) {
            return (int) keys.get("id");
        }

        return -1;
    }

    @Override
    public void updateCinemaById(Cinema cinema, int cinemaId, int cityId) {
        String query = """
            UPDATE cinemas SET
            name=:name, city_id=:city_id, street=:street
            WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("name", cinema.getName())
            .addValue("city_id", cityId)
            .addValue("street", cinema.getStreet())
            .addValue("id", cinemaId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void deleteCinemaById(int cinemaId) {
        String query = """
            DELETE FROM cinemas WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", cinemaId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }
}
