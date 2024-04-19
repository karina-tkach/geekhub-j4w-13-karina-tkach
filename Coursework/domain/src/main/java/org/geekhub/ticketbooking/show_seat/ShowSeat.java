package org.geekhub.ticketbooking.show_seat;

import java.util.Objects;

public class ShowSeat {
    private int id;
    private int number;
    private boolean booked;
    private int hallId;
    private int showId;

    public ShowSeat() {
        this.id = -1;
        this.number = 0;
        this.booked = false;
        this.hallId = -1;
        this.showId = -1;
    }

    public ShowSeat(int id, int number, boolean booked, int hallId, int showId) {
        this.id = id;
        this.number = number;
        this.booked = booked;
        this.hallId = hallId;
        this.showId = showId;
    }

    public ShowSeat(int number, int hallId, int showId) {
        this.id = -1;
        this.number = number;
        this.booked = false;
        this.hallId = hallId;
        this.showId = showId;
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

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowSeat showSeat = (ShowSeat) o;
        return id == showSeat.id && number == showSeat.number &&
            booked == showSeat.booked && hallId == showSeat.hallId && showId == showSeat.showId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, booked, hallId, showId);
    }

    @Override
    public String toString() {
        return "ShowSeat{" +
            "id=" + id +
            ", number=" + number +
            ", booked=" + booked +
            ", hallId=" + hallId +
            ", showId=" + showId +
            '}';
    }
}
