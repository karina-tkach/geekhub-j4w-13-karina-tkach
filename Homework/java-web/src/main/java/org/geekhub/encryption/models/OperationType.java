package org.geekhub.encryption.models;

public enum OperationType {
    ENCRYPTION("Encryption"),
    DECRYPTION("Decryption");

    private final String displayValue;

    private OperationType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
