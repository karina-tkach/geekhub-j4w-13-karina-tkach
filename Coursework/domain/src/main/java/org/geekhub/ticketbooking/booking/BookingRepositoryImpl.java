package org.geekhub.ticketbooking.booking;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings({"java:S2259"})
public class BookingRepositoryImpl implements BookingRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookingRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Booking getBookingById(int bookingId) {
        String query = """
            SELECT bookings.id, bookings.show_id, bookings.seat_id, bookings.user_id, users.firstName,
            users.lastName, users.password, users.email, users.role, bookings.uuid, bookings.show_details
            FROM bookings
            INNER JOIN users ON bookings.user_id = users.id WHERE bookings.id=:id
            """;

        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("id", bookingId);

        return jdbcTemplate.query(query, mapSqlParameterSource, BookingMapper::mapToPojo)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Booking> getBookingsWithPagination(int pageNumber, int limit) {
        String query = """
            SELECT bookings.id, bookings.show_id, bookings.seat_id, bookings.user_id, users.firstName,
            users.lastName, users.password, users.email, users.role, bookings.uuid, bookings.show_details
            FROM bookings
            INNER JOIN users ON bookings.user_id = users.id ORDER BY bookings.id
            LIMIT :limit
            OFFSET :offset
            """;
        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("limit", limit)
            .addValue("offset", getOffset(pageNumber, limit));

        return jdbcTemplate.query(query, parameters, BookingMapper::mapToPojo);
    }

    @Override
    public int getBookingsRowsCount() {
        String query = "SELECT COUNT(*) FROM bookings";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource(), Integer.class);
    }

    @Override
    public int addBooking(Booking booking) {
        String query = """
            INSERT INTO bookings (show_id, seat_id, user_id, uuid, show_details)
            VALUES (:showId, :seatId, :userId,:uuid, :showDetails)
            """;

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
            .addValue("showId", booking.getShowId())
            .addValue("seatId", booking.getSeatId())
            .addValue("userId", booking.getUser().getId())
            .addValue("uuid", booking.getUuid())
            .addValue("showDetails", booking.getShowDetails());

        jdbcTemplate.update(query, mapSqlParameterSource, generatedKeyHolder);

        var keys = generatedKeyHolder.getKeys();
        if (keys != null) {
            return (int) keys.get("id");
        }

        return -1;
    }

    private static int getOffset(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }
}
