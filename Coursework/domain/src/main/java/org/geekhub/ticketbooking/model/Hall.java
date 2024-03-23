package org.geekhub.ticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Hall {
    private int id;
    private String name;
    private int rows;
    private int columns;
    private final List<Seat> seats = initializeSeats(rows * columns);
    private int cinemaId;
    private List<Show> shows;

    private List<Seat> initializeSeats(int numberOfSeats) {
        List<Seat> seatsList = new ArrayList<>(numberOfSeats);
        for (int i = 0; i< numberOfSeats; i++) {
            seatsList.add(Seat.builder().number(i+1).hallId(id).build());
        }
        return seatsList;
    }
}
