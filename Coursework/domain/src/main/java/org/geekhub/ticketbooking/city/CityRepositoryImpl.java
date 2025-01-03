package org.geekhub.ticketbooking.city;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings({"java:S1192", "java:S2259"})
public class CityRepositoryImpl implements CityRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CityRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<City> getAllCities() {
        String query = """
            SELECT * FROM cities ORDER BY id
            """;
        return jdbcTemplate.query(query, CityMapper::mapToPojo);
    }

    @Override
    public City getCityById(int cityId) {
        String query = """
            SELECT * FROM cities WHERE id=:id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", cityId);

        return jdbcTemplate.query(query, mapSqlParameterSource, CityMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public City getCityByName(String name) {
        String query = """
            SELECT * FROM cities WHERE name=:name
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("name", name);

        return jdbcTemplate.query(query, mapSqlParameterSource, CityMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public int addCity(City city) {
        String query = """
            INSERT INTO cities (name)
            VALUES (:name)
            """;

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("name", city.getName());

        jdbcTemplate.update(query, mapSqlParameterSource, generatedKeyHolder);

        var keys = generatedKeyHolder.getKeys();
        if (keys != null) {
            return (int) keys.get("id");
        }

        return -1;
    }

    @Override
    public void updateCityById(City city, int cityId) {
        String query = """
            UPDATE cities SET
            name=:name WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("name", city.getName())
            .addValue("id", cityId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void deleteCityById(int cityId) {
        String query = """
            DELETE FROM cities WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", cityId);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public List<City> getCitiesWithPagination(int pageNumber, int limit) {
        String query = """
            SELECT * FROM cities
            ORDER BY id
            LIMIT :limit
            OFFSET :offset
            """;
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("limit", limit)
            .addValue("offset", getOffset(pageNumber, limit));

        return jdbcTemplate.query(query, parameters, CityMapper::mapToPojo);
    }

    @Override
    public int getCitiesRowsCount() {
        String query = "SELECT COUNT(*) FROM cities";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(), Integer.class);
    }

    private static int getOffset(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }
}
