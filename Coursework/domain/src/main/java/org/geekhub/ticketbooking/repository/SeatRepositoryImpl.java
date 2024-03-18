package org.geekhub.ticketbooking.repository;

import org.geekhub.ticketbooking.model.Hall;
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
    public List<Seat> getSeatsByHall(Hall hall) {
        return null;
    }

    @Override
    public Seat getSeatById(int seatId) {
        return null;
    }

    @Override
    public Seat getSeatByHallAndNumber(Hall hall, int number) {
        return null;
    }

    @Override
    public List<Seat> getSeatsByHallAndStatus(Hall hall, boolean isBooked) {
        return null;
    }

    @Override
    public int addSeat(Seat seat) {
        return 0;
    }

    @Override
    public void updateSeatById(int seatId) {

    }

    @Override
    public void deleteSeatById(int seatId) {

    }
}
