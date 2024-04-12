package org.geekhub.ticketbooking.repository.mappers;

import org.geekhub.ticketbooking.model.ForgotPasswordToken;
import org.geekhub.ticketbooking.model.Role;
import org.geekhub.ticketbooking.model.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;

@Component
public class TokenMapper {
    private TokenMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static ForgotPasswordToken mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new ForgotPasswordToken(rs.getInt("id"),
            rs.getString("token"),
            new User(rs.getInt("user_id"), rs.getString("firstName"),
                rs.getString("lastName"), rs.getString("password"),
                rs.getString("email"), Role.valueOf(rs.getString("role"))),
            rs.getTimestamp("expire_time").toInstant().atOffset(ZoneOffset.UTC),
            rs.getBoolean("is_used"));
    }
}
