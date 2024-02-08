package org.geekhub.encryption.ciphers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class VigenereCipher implements Cipher {
    public static final int NUMBER_OF_LETTERS_IN_ALPHABET = 26;
    private final String key;
    private final AtomicInteger keyIndex;

    public VigenereCipher(@Value("${vigenere.key}") String key) {
        this.key = key;
        this.keyIndex = new AtomicInteger();
    }

    @Override
    public String encrypt(String message) {
        validateKey();

        String keyLower = key.toLowerCase();
        String keyUpper = key.toUpperCase();

        return message.chars()
            .mapToObj(letter -> String.valueOf(getCharForVigenereEncryption(letter, keyLower, keyUpper)))
            .collect(Collectors.joining());
    }

    @Override
    public String decrypt(String message) {
        validateKey();

        String keyLower = key.toLowerCase();
        String keyUpper = key.toUpperCase();

        return message.chars()
            .mapToObj(letter -> String.valueOf(getCharForVigenereDecryption(letter, keyLower, keyUpper)))
            .collect(Collectors.joining());
    }

    @Override
    public String getCipherName() {
        return "Vigenere";
    }

    private void validateKey() {
        if (key == null || !(key.chars().allMatch(Character::isLetter))) {
            throw new IllegalArgumentException("Incorrect Vigenere key.");
        }

        keyIndex.set(0);
    }

    private char getCharForVigenereEncryption(int letter, String keyLower, String keyUpper) {
        int keyLetter = getKeyLetter(letter);
        if (keyLetter == 0) {
            return (char) letter;
        }

        String validKey = getKeyInValidCase(keyLetter, keyLower, keyUpper);

        char res = (char) (((letter - keyLetter) + (validKey.charAt(keyIndex.get()) - keyLetter)) % NUMBER_OF_LETTERS_IN_ALPHABET + keyLetter);
        keyIndex.set(keyIndex.incrementAndGet() % validKey.length());
        return res;
    }

    private char getCharForVigenereDecryption(int letter, String keyLower, String keyUpper) {
        int keyLetter = getKeyLetter(letter);
        if (keyLetter == 0) {
            return (char) letter;
        }

        String validKey = getKeyInValidCase(keyLetter, keyLower, keyUpper);

        char res = (char) ((letter - validKey.charAt(keyIndex.get()) + NUMBER_OF_LETTERS_IN_ALPHABET) % NUMBER_OF_LETTERS_IN_ALPHABET + keyLetter);
        keyIndex.set(keyIndex.incrementAndGet() % validKey.length());
        return res;
    }

    private String getKeyInValidCase(int keyLetter, String keyLower, String keyUpper) {
        return keyLetter == 'a' ? keyLower : keyUpper;
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
