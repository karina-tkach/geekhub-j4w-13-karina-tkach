package org.geekhub.ticketbooking.movie;

public enum Genre {
    ACTION("Action"),
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

    public String getDisplayValue() {
        return displayValue;
    }

    Genre(String displayValue) {
        this.displayValue = displayValue;
    }

}
