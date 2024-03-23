package org.geekhub.ticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Seat {
    private int id;
    private int number;
    private boolean isBooked;
    private int hallId;
}
