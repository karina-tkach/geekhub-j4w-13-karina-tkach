package org.geekhub.encryption.ciphers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Profile("Caesar-cipher")
public class CaesarCipher implements Cipher {
    public static final int NUMBER_OF_LETTERS_IN_ALPHABET = 26;
    @Value("${caesar.key}")
    private int caesarKey;

    @Override
    public String encrypt(String message) {
        int finalShift = ((caesarKey % NUMBER_OF_LETTERS_IN_ALPHABET)
            + NUMBER_OF_LETTERS_IN_ALPHABET) % NUMBER_OF_LETTERS_IN_ALPHABET;

        return message.chars()
            .mapToObj(letter -> String.valueOf(getCharForCaesarEncryption(letter, finalShift)))
            .collect(Collectors.joining());
    }

    @Override
    public String getCipherName() {
        return "Caesar";
    }

    private char getCharForCaesarEncryption(int letter, int shift) {
        int keyLetter = getKeyLetter(letter);
        if (keyLetter == 0) {
            return (char) letter;
        }

        return (char) ((letter - keyLetter + shift) % NUMBER_OF_LETTERS_IN_ALPHABET + keyLetter);
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
