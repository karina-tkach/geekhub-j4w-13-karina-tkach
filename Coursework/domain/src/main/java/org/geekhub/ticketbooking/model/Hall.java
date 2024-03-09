package org.geekhub.ticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Hall {
    private int id;
    private String name;
    private int numberOfSeats;
    private List<Seat> seats;
    private Cinema cinema;
    private List<Show> shows;
}
