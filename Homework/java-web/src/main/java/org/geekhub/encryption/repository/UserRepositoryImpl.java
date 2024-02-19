package org.geekhub.encryption.repository;

import org.geekhub.encryption.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> getUser(int userId) {
        String query = "SELECT * FROM users WHERE id = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("userId", userId);

        try {
            User user = jdbcTemplate.queryForObject(query, params, UserMapper::mapToPojo);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM users ORDER BY id";

        return jdbcTemplate.query(query, UserMapper::mapToPojo);
    }
}
