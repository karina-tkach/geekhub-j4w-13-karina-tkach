package org.geekhub.ticketbooking.seat;

import java.util.Objects;

public class Seat {
    private int id;
    private int number;
    private int hallId;

    public Seat() {
        this.id = -1;
        this.number = 0;
        this.hallId = -1;
    }

    public Seat(int id, int number, int hallId) {
        this.id = id;
        this.number = number;
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
        return id == seat.id && number == seat.number && hallId == seat.hallId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, hallId);
    }

    @Override
    public String toString() {
        return "Seat{" +
            "id=" + id +
            ", number=" + number +
            ", hallId=" + hallId +
            '}';
    }
}
