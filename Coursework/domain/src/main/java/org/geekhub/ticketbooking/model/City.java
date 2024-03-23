package org.geekhub.ticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class City {
    private int id;
    private String name;
}
