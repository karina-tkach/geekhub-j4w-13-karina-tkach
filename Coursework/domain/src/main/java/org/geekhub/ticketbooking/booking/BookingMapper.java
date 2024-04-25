package org.geekhub.ticketbooking.booking;

import org.geekhub.ticketbooking.user.Role;
import org.geekhub.ticketbooking.user.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class BookingMapper {
    private BookingMapper() {
    }

    @SuppressWarnings("java:S1172")
    public static Booking mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new Booking(rs.getInt("id"),
            rs.getInt("show_id"),
            rs.getInt("seat_id"),
            new User(rs.getInt("user_id"), rs.getString("firstName"),
                rs.getString("lastName"), rs.getString("password"),
                rs.getString("email"), Role.valueOf(rs.getString("role"))),
            UUID.fromString(rs.getString("uuid")),
            rs.getString("show_details"));
    }
}
