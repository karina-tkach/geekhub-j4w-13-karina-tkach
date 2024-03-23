package org.geekhub.ticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
public class Show {
    private int id;
    private int price;
    private OffsetDateTime start;
    private OffsetDateTime end;
    private Movie movie;
    private int hallId;
}
