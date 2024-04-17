package org.geekhub.ticketbooking.model;

import java.time.OffsetDateTime;
import java.util.Objects;

public class Show {
    private int id;
    private int price;
    private OffsetDateTime start;
    private OffsetDateTime end;
    private Movie movie;
    private int hallId;

    public Show() {
        this.id = -1;
        this.price = 0;
        this.start = null;
        this.end = null;
        this.movie = null;
        this.hallId = -1;
    }

    public Show(int id, int price, OffsetDateTime start, OffsetDateTime end, Movie movie, int hallId) {
        this.id = id;
        this.price = price;
        this.start = start;
        this.end = end;
        this.movie = movie;
        this.hallId = hallId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public OffsetDateTime getStart() {
        return start;
    }

    public void setStart(OffsetDateTime start) {
        this.start = start;
    }

    public OffsetDateTime getEnd() {
        return end;
    }

    public void setEnd(OffsetDateTime end) {
        this.end = end;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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
        Show show = (Show) o;
        return id == show.id && price == show.price &&
            hallId == show.hallId && Objects.equals(start, show.start) &&
            Objects.equals(end, show.end) && Objects.equals(movie, show.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, start, end, movie, hallId);
    }

    @Override
    public String toString() {
        return "Show{" +
            "id=" + id +
            ", price=" + price +
            ", start=" + start +
            ", end=" + end +
            ", movie=" + movie +
            ", hallId=" + hallId +
            '}';
    }
}
