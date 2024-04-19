package org.geekhub.ticketbooking.cinema;

import org.geekhub.ticketbooking.city.City;

import java.util.Objects;

public class Cinema {
    private int id;
    private String name;
    private City city;
    private String street;

    public Cinema() {
        this.id = -1;
        this.name = null;
        this.city = new City();
        this.street = null;
    }

    public Cinema(int id, String name, City city, String street) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(name, cinema.name) && Objects.equals(city, cinema.city) &&
            Objects.equals(street, cinema.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, street);
    }

    @Override
    public String toString() {
        return "Cinema{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", city=" + city +
            ", street='" + street + '\'' +
            '}';
    }
}
