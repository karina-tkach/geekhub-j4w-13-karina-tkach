package org.geekhub.ticketbooking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hall {
    private int id;
    private String name;
    private int rows;
    private int columns;
    private List<Seat> seats;
    private int cinemaId;
    private List<Show> shows;

    public Hall() {
        this.id = -1;
        this.name = null;
        this.rows = 0;
        this.columns = 0;
        this.seats = initializeSeats(0);
        this.cinemaId = -1;
        this.shows = new ArrayList<>();
    }

    public Hall(int id, String name, int rows, int columns, List<Seat> seats,
                int cinemaId, List<Show> shows) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
        this.cinemaId = cinemaId;
        this.shows = shows;
    }

    public Hall(int id, String name, int rows, int columns, int cinemaId, List<Show> shows) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.seats = initializeSeats(rows * columns);
        this.cinemaId = cinemaId;
        this.shows = shows;
    }

    public List<Seat> initializeSeats(int numberOfSeats) {
        List<Seat> seatsList = new ArrayList<>(numberOfSeats);
        for (int i = 0; i < numberOfSeats; i++) {
            Seat seat = new Seat();
            seat.setNumber(i + 1);
            seat.setHallId(id);

            seatsList.add(seat);
        }
        return seatsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats() {
        this.seats = initializeSeats(rows * columns);
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return id == hall.id && rows == hall.rows && columns == hall.columns &&
            cinemaId == hall.cinemaId && Objects.equals(name, hall.name) &&
            Objects.equals(seats, hall.seats) && Objects.equals(shows, hall.shows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rows, columns, seats, cinemaId, shows);
    }

    @Override
    public String toString() {
        return "Hall{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", rows=" + rows +
            ", columns=" + columns +
            ", seats=" + seats +
            ", cinemaId=" + cinemaId +
            ", shows=" + shows +
            '}';
    }
}
