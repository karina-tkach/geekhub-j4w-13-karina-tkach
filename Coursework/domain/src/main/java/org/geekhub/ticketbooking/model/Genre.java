package org.geekhub.ticketbooking.model;

public enum Genre {
    DRAMA("Drama"),
    ADVENTURE("Adventure"),
    HORROR("Horror"),
    THRILLER("Thriller"),
    FANTASY("Fantasy"),
    CRIME("Crime"),
    ROMANCE("Romance"),
    COMEDY("Comedy"),
    WESTERN("Western"),
    MUSICAL("Musical"),
    HISTORY("History"),
    MYSTERY("Mystery");

    private final String displayValue;

    Genre(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
