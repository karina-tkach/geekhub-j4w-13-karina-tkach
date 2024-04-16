package org.geekhub.ticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Cinema {
    private int id;
    private String name;
    private City city;
    private String street;
    private List<Hall> halls;

    public Cinema() {
        this.id = 0;
        this.name = null;
        this.city = new City();
        this.street = null;
        this.halls = new ArrayList<>();
        halls.add(new Hall());
    }
}
