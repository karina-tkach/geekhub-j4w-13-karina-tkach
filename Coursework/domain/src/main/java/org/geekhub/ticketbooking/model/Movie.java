package org.geekhub.ticketbooking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Movie {
    private int id;
    private String title;
    private String description;
    private int durationInMins;
    private Language language;
    private OffsetDateTime releaseDate;
    private String country;
    private int ageLimit;
    private List<Genre> genres;
}
