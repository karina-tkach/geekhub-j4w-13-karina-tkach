package org.geekhub.ticketbooking.show;

import org.geekhub.ticketbooking.movie.Movie;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Show {
    private int id;
    private Long price;
    private OffsetDateTime start;
    private OffsetDateTime end;
    private Movie movie;
    private int hallId;

    public Show() {
        this.id = -1;
        this.price = 0L;
        this.start = null;
        this.end = null;
        this.movie = new Movie();
        this.hallId = -1;
    }

    public Show(int id, Long price, OffsetDateTime start, OffsetDateTime end,
                Movie movie, int hallId) {
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
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

    public void setFormattedStartDate(String date) {
        if (!date.isEmpty()) {
            this.start = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME).atOffset(ZoneOffset.UTC);
        } else {
            this.start = null;
        }
    }

    public String getFormattedStartDate() {
        if (start != null) {
            String str = start.format(DateTimeFormatter.ISO_DATE_TIME);
            return str.substring(0, str.length() - 1);
        } else {
            return "";
        }
    }

    public void setFormattedEndDate(String date) {
        if (!date.isEmpty()) {
            this.end = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME).atOffset(ZoneOffset.UTC);
        } else {
            this.end = null;
        }
    }

    public String getFormattedEndDate() {
        if (end != null) {
            String str = end.format(DateTimeFormatter.ISO_DATE_TIME);
            return str.substring(0, str.length() - 1);
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return id == show.id && hallId == show.hallId &&
            Objects.equals(price, show.price) && Objects.equals(start, show.start) &&
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
