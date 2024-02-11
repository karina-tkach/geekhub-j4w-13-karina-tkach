package org.geekhub.ciphers;

import java.util.stream.Collectors;

public class AtbashCipher implements Cipher {
    public static final int NUMBER_OF_LETTERS_IN_ALPHABET = 26;

    @Override
    public String encrypt(String message) {
        return performOperation(message);
    }

    @Override
    public String decrypt(String message) {
        return performOperation(message);
    }

    @Override
    public String getCipherName() {
        return "Atbash";
    }

    private String performOperation(String message) {
        return message.chars()
            .mapToObj(letter -> String.valueOf(getCharForAtbashOperation(letter)))
            .collect(Collectors.joining());
    }

    private char getCharForAtbashOperation(int letter) {
        int keyLetter = getKeyLetter(letter);
        if (keyLetter == 0) {
            return (char) letter;
        }

        return (char) (keyLetter + (keyLetter + NUMBER_OF_LETTERS_IN_ALPHABET - 1 - letter));
    }

    private int getKeyLetter(int letter) {
        if (letter >= 'a' && letter <= 'z') {
            return 'a';
        }

        if (letter >= 'A' && letter <= 'Z') {
            return 'A';
        }

        return 0;
    }
}
