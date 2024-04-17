package org.geekhub.ticketbooking.seat;

import java.util.Objects;

public class Seat {
    private int id;
    private int number;
    private boolean booked;
    private int hallId;

    public Seat() {
        this.id = -1;
        this.number = 0;
        this.booked = false;
        this.hallId = -1;
    }

    public Seat(int id, int number, boolean isBooked, int hallId) {
        this.id = id;
        this.number = number;
        this.booked = isBooked;
        this.hallId = hallId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return id == seat.id && number == seat.number &&
            booked == seat.booked && hallId == seat.hallId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, booked, hallId);
    }

    @Override
    public String toString() {
        return "Seat{" +
            "id=" + id +
            ", number=" + number +
            ", isBooked=" + booked +
            ", hallId=" + hallId +
            '}';
    }
}
