package org.geekhub.ticketbooking.hall;

import java.util.Objects;

public class Hall {
    private int id;
    private String hallName;
    private int rows;
    private int columns;
    private int cinemaId;

    public Hall() {
        this.id = -1;
        this.hallName = null;
        this.rows = 0;
        this.columns = 0;
        this.cinemaId = -1;
    }

    public Hall(int id, String hallName, int rows, int columns, int cinemaId) {
        this.id = id;
        this.hallName = hallName;
        this.rows = rows;
        this.columns = columns;
        this.cinemaId = cinemaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
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

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return id == hall.id && rows == hall.rows && columns == hall.columns &&
            cinemaId == hall.cinemaId && Objects.equals(hallName, hall.hallName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hallName, rows, columns, cinemaId);
    }

    @Override
    public String toString() {
        return "Hall{" +
            "id=" + id +
            ", name='" + hallName + '\'' +
            ", rows=" + rows +
            ", columns=" + columns +
            ", cinemaId=" + cinemaId +
            '}';
    }
}
