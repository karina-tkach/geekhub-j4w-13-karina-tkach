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
    private List<Seat> seats;
    private int cinemaId;
    private List<Show> shows;

    public Hall(int id, String name, int rows, int columns, int cinemaId, List<Show> shows) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.seats = initializeSeats(rows * columns);
        this.cinemaId = cinemaId;
        this.shows = shows;
    }

    public Hall() {
        this.id = 0;
        this.name = null;
        this.rows = 0;
        this.columns = 0;
        this.seats = initializeSeats(0);
        this.cinemaId = 0;
        this.shows = new ArrayList<>();
    }

    public List<Seat> initializeSeats(int numberOfSeats) {
        List<Seat> seatsList = new ArrayList<>(numberOfSeats);
        for (int i = 0; i < numberOfSeats; i++) {
            seatsList.add(Seat.builder().number(i + 1).hallId(id).build());
        }
        return seatsList;
    }

    public void setSeats() {
        this.seats = initializeSeats(rows * columns);
    }
}
