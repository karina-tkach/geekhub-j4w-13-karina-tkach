package org.geekhub.encryption.users;

import java.sql.ResultSet;
import java.sql.SQLException;

class UserMapper {

    private UserMapper() {
    }

    @SuppressWarnings("java:S1172")
    static User mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email")
        );
    }
}
