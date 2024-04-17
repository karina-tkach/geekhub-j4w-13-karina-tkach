package org.geekhub.ticketbooking.cinema;

import org.geekhub.ticketbooking.city.City;
import org.geekhub.ticketbooking.hall.Hall;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cinema {
    private int id;
    private String name;
    private City city;
    private String street;
    private List<Hall> halls;

    public Cinema() {
        this.id = -1;
        this.name = null;
        this.city = new City();
        this.street = null;
        this.halls = new ArrayList<>();
        halls.add(new Hall());
    }

    public Cinema(int id, String name, City city, String street, List<Hall> halls) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.halls = halls;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return id == cinema.id && Objects.equals(name, cinema.name) && Objects.equals(city, cinema.city) &&
            Objects.equals(street, cinema.street) && Objects.equals(halls, cinema.halls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city, street, halls);
    }

    @Override
    public String toString() {
        return "Cinema{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", city=" + city +
            ", street='" + street + '\'' +
            ", halls=" + halls +
            '}';
    }
}
