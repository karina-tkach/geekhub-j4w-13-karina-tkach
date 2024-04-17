package org.geekhub.ticketbooking.user;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper {
    private UserMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static User mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new User(rs.getInt("id"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getString("password"),
            rs.getString("email"),
            Role.valueOf(rs.getString("role")));
    }
}
