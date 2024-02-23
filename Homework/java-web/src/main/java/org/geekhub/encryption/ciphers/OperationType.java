package org.geekhub.encryption.ciphers;

public enum OperationType {
    ENCRYPTION("Encryption"),
    DECRYPTION("Decryption");

    private final String displayValue;

    OperationType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
