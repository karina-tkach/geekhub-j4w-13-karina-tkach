package org.geekhub.encryption.service;

import org.geekhub.encryption.injector.Injectable;
import org.geekhub.encryption.repository.EncryptionRepository;

import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EncryptionService {
    public static final int NUMBER_OF_LETTERS_IN_ALPHABET = 26;
    @Injectable(propertyName = "caesarKey")
    private int caesarKey;
    @Injectable(propertyName = "rot13Key")
    private int rot13key;
    private final EncryptionRepository encryptionRepository;
    private final AtomicBoolean lastCharIsLetter;
    private final AtomicInteger keyIndex;

    public EncryptionService(EncryptionRepository encryptionRepository) {
        this.encryptionRepository = encryptionRepository;
        this.lastCharIsLetter = new AtomicBoolean();
        this.keyIndex = new AtomicInteger();
    }

    public String encryptMessage(String cipherName, String message, String key) {
        if (message.isBlank() || (cipherName.equals("Vigenere") && !(key.chars().allMatch(Character::isLetter)))) {
            throw new IllegalArgumentException("Incorrect data for encryption.");
        }

        String encryptedMessage;
        switch (cipherName) {
            case "Caesar" -> encryptedMessage = encryptViaTheShiftCipher(message, caesarKey);
            case "Atbash" -> encryptedMessage = encryptViaTheAtbashCipher(message);
            case "A1Z26" -> encryptedMessage = encryptViaTheA1Z26Cipher(message);
            case "ROT13" -> encryptedMessage = encryptViaTheShiftCipher(message, rot13key);
            case "Vigenere" -> encryptedMessage = encryptViaTheVigenereCipher(message, key);
            default -> throw new IllegalArgumentException("Incorrect data for encryption.");
        }

        OffsetDateTime dateTime = OffsetDateTime.now();
        encryptionRepository.addToHistoryLog(message, cipherName, encryptedMessage, dateTime);

        return encryptedMessage;
    }

    private String encryptViaTheShiftCipher(String message, int shift) {
        int finalShift = ((shift % NUMBER_OF_LETTERS_IN_ALPHABET)
            + NUMBER_OF_LETTERS_IN_ALPHABET) % NUMBER_OF_LETTERS_IN_ALPHABET;

        return message.chars()
            .mapToObj(letter -> String.valueOf(getCharForShiftEncryption(letter, finalShift)))
            .collect(Collectors.joining());
    }

    private char getCharForShiftEncryption(int letter, int shift) {
        int keyLetter = getKeyLetter(letter);
        if (keyLetter == 0) {
            return (char) letter;
        }

        return (char) ((letter - keyLetter + shift) % NUMBER_OF_LETTERS_IN_ALPHABET + keyLetter);
    }

    private String encryptViaTheAtbashCipher(String message) {
        return message.chars()
            .mapToObj(letter -> String.valueOf(getCharForAtbashEncryption(letter)))
            .collect(Collectors.joining());
    }

    private char getCharForAtbashEncryption(int letter) {
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

    private String encryptViaTheA1Z26Cipher(String message) {
        return message.chars()
            .mapToObj(this::getStringForA1Z26Encryption)
            .collect(Collectors.joining());
    }

    private String getStringForA1Z26Encryption(int letter) {
        int keyLetter = getKeyLetter(letter);
        if (keyLetter == 0) {
            lastCharIsLetter.set(false);
            return String.valueOf((char)letter);
        }

        String result = String.valueOf(letter - keyLetter + 1);
        if (lastCharIsLetter.get()) {
            result = "-" + result;
        }
        lastCharIsLetter.set(true);
        return result;
    }

    private String encryptViaTheVigenereCipher(String message, String key) {
        String keyLower = key.toLowerCase();
        String keyUpper = key.toUpperCase();

        return message.chars()
            .mapToObj(letter -> String.valueOf(getCharForVigenereEncryption(letter, keyLower, keyUpper)))
            .collect(Collectors.joining());
    }

    private char getCharForVigenereEncryption(int letter, String keyLower, String keyUpper) {
        int keyLetter = getKeyLetter(letter);
        if (keyLetter == 0) {
            return (char) letter;
        }

        String key = (keyLetter == 'a' ? keyLower : keyUpper);

        char res = (char) (((letter - keyLetter) + (key.charAt(keyIndex.get()) - keyLetter)) % NUMBER_OF_LETTERS_IN_ALPHABET + keyLetter);
        keyIndex.set(keyIndex.incrementAndGet() % key.length());
        return res;
    }
}
