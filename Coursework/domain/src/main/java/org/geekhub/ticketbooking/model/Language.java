package org.geekhub.ticketbooking.model;

public enum Language {
    ENGLISH("English"),
    UKRAINIAN("Ukrainian");
    private final String displayValue;

    Language(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
