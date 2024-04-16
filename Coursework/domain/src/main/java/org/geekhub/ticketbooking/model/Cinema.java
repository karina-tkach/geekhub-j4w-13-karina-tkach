package org.geekhub.ticketbooking.model;

import java.util.ArrayList;
import java.util.List;

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
}
