package org.geekhub.ticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Cinema {
    private int id;
    private String name;
    private City city;
    private int numberOfHalls;
    private List<Hall> halls;
}
