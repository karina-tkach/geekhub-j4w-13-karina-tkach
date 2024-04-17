package org.geekhub.ticketbooking.model;

import java.util.Objects;

public class Seat {
    private int id;
    private int number;
    private boolean isBooked;
    private int hallId;

    public Seat() {
        this.id = -1;
        this.number = 0;
        this.isBooked = false;
        this.hallId = -1;
    }

    public Seat(int id, int number, boolean isBooked, int hallId) {
        this.id = id;
        this.number = number;
        this.isBooked = isBooked;
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
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
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
            isBooked == seat.isBooked && hallId == seat.hallId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, isBooked, hallId);
    }

    @Override
    public String toString() {
        return "Seat{" +
            "id=" + id +
            ", number=" + number +
            ", isBooked=" + isBooked +
            ", hallId=" + hallId +
            '}';
    }
}
