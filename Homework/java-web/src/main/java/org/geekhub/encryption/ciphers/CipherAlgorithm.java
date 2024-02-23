package org.geekhub.encryption.ciphers;

public enum CipherAlgorithm {
    ATBASH("Atbash"),
    CAESAR("Caesar"),
    A1Z26("A1Z26"),
    VIGENERE("Vigenere");

    private final String displayValue;

    CipherAlgorithm(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
