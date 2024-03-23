package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.Seat;
import org.geekhub.ticketbooking.repository.interfaces.SeatRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeatRepositoryImpl implements SeatRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SeatRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Seat> getSeatsByHall(int hallId) {
        return null;
    }

    @Override
    public Seat getSeatById(int seatId) {
        return null;
    }

    @Override
    public Seat getSeatByHallAndNumber(int hallId, int number) {
        return null;
    }

    @Override
    public List<Seat> getSeatsByHallAndStatus(int hallId, boolean isBooked) {
        return null;
    }

    @Override
    public int addSeat(Seat seat, int hallId) {
        return 0;
    }

    @Override
    public void updateSeatById(Seat seat, int hallId, int seatId) {

    }

    @Override
    public void deleteSeatById(int seatId) {

    }
}
