package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.ForgotPasswordToken;
import org.geekhub.ticketbooking.repository.interfaces.ForgotPasswordRepository;
import org.geekhub.ticketbooking.repository.mappers.TokenMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
@SuppressWarnings("java:S1192")
public class ForgotPasswordRepositoryImpl implements ForgotPasswordRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ForgotPasswordRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addToken(ForgotPasswordToken token, int userId) {
        String query = """
            INSERT INTO tokens (token, user_id, expire_time, is_used)
            VALUES (:token,:userId, :expireTime, :isUsed)
            """;


        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("token", token.getToken())
            .addValue("userId", userId)
            .addValue("expireTime", Timestamp.from(token.getExpireTime().toInstant()))
            .addValue("isUsed", token.isUsed());

        jdbcTemplate.update(query, mapSqlParameterSource, generatedKeyHolder);

        var keys = generatedKeyHolder.getKeys();
        if (keys != null) {
            return (int) keys.get("id");
        }

        return -1;
    }

    @Override
    public void updateTokenById(ForgotPasswordToken token, int id, int userId) {
        String query = """
            UPDATE tokens SET
            token=:token, user_id=:userId, expire_time=:expireTime, is_used=:isUsed
            WHERE id=:id
            """;
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("token", token.getToken())
            .addValue("userId", userId)
            .addValue("expireTime", Timestamp.from(token.getExpireTime().toInstant()))
            .addValue("isUsed", token.isUsed())
            .addValue("id", id);

        jdbcTemplate.update(query, mapSqlParameterSource);
    }

    @Override
    public ForgotPasswordToken getTokenByValue(String token) {
        String query = """
                 SELECT tokens.id, tokens.token, tokens.expire_time, tokens.is_used, tokens.user_id,
            users.firstName, users.lastName, users.password, users.email, users.role FROM tokens
                 INNER JOIN users ON tokens.user_id = users.id WHERE tokens.token=:token
                 """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("token", token);

        return jdbcTemplate.query(query, mapSqlParameterSource, TokenMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public ForgotPasswordToken getTokenById(int id) {
        String query = """
                 SELECT tokens.id, tokens.token, tokens.expire_time, tokens.is_used, tokens.user_id,
            users.firstName, users.lastName, users.password, users.email, users.role FROM tokens
                 INNER JOIN users ON tokens.user_id = users.id WHERE tokens.id=:id
                 """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", id);

        return jdbcTemplate.query(query, mapSqlParameterSource, TokenMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }
}
