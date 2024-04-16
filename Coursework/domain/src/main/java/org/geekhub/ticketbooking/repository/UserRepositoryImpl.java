package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.User;
import org.geekhub.ticketbooking.repository.interfaces.UserRepository;
import org.geekhub.ticketbooking.repository.mappers.UserMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings({"java:S1192", "java:S2259"})
public class UserRepositoryImpl implements UserRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addUser(User user) {
        String query = """
            INSERT INTO users (firstName, lastName, password, email, role)
            VALUES (:firstName, :lastName, :password, :email, :role)
            """;

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("firstName", user.getFirstName())
            .addValue("lastName", user.getLastName())
            .addValue("password", user.getPassword())
            .addValue("email", user.getEmail())
            .addValue("role", user.getRole().toString());

        jdbcTemplate.update(query, mapSqlParameterSource, generatedKeyHolder);

        var keys = generatedKeyHolder.getKeys();
        if (keys != null) {
            return (int) keys.get("id");
        }

        return -1;
    }

    @Override
    public User getUserById(int id) {
        String query = """
            SELECT * FROM users WHERE id=:id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", id);

        return jdbcTemplate.query(query, mapSqlParameterSource, UserMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        String query = """
            SELECT * FROM users WHERE email=:email
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("email", email);

        return jdbcTemplate.query(query, mapSqlParameterSource, UserMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public void deleteUserById(int id) {
        String query = """
            DELETE FROM users WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", id);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void updateUserById(User user, int id) {
        String query = """
            UPDATE users SET
            firstName=:firstName, lastName=:lastName, password=:password, email=:email, role=:role
            WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("firstName", user.getFirstName())
            .addValue("lastName", user.getLastName())
            .addValue("password", user.getPassword())
            .addValue("email", user.getEmail())
            .addValue("role", user.getRole().toString())
            .addValue("id", id);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public void updateUserWithoutPasswordChangeById(User user, int id) {
        String query = """
            UPDATE users SET
            firstName=:firstName, lastName=:lastName, email=:email, role=:role
            WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("firstName", user.getFirstName())
            .addValue("lastName", user.getLastName())
            .addValue("email", user.getEmail())
            .addValue("role", user.getRole().toString())
            .addValue("id", id);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public List<User> getUsersWithPagination(int pageNumber, int limit) {
        String query = """
            SELECT * FROM users
            ORDER BY id
            LIMIT :limit
            OFFSET :offset
            """;
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("limit", limit)
            .addValue("offset", getOffset(pageNumber, limit));

        return jdbcTemplate.query(query, parameters, UserMapper::mapToPojo);
    }

    @Override
    public int getUsersRowsCount() {
        String query = "SELECT COUNT(*) FROM users";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(), Integer.class);
    }

    private static int getOffset(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }

}
