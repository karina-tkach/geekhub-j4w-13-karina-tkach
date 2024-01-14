package org.geekhub.encryption.service;

import org.geekhub.encryption.injector.Injectable;
import org.geekhub.encryption.repository.EncryptionRepository;

import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class EncryptionService {
    public static final int NUMBER_OF_LETTERS_IN_ALPHABET = 26;
    @Injectable(propertyName = "caesarKey")
    private int caesarKey;
    @Injectable(propertyName = "rot13Key")
    private int rot13key;
    private final EncryptionRepository encryptionRepository;

    public EncryptionService(EncryptionRepository encryptionRepository) {
        this.encryptionRepository = encryptionRepository;
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

        StringBuilder sb = new StringBuilder(message.length());

        message.chars()
            .forEach(letter ->{
                if (letter >= 'a' && letter <= 'z') {
                    sb.append((char) ((letter - 'a' + finalShift) % NUMBER_OF_LETTERS_IN_ALPHABET + 'a'));
                } else if (letter >= 'A' && letter <= 'Z') {
                    sb.append((char) ((letter - 'A' + finalShift) % NUMBER_OF_LETTERS_IN_ALPHABET + 'A'));
                } else {
                    sb.append((char)letter);
                }
            });

        return sb.toString();
    }

    private String encryptViaTheAtbashCipher(String message) {
        StringBuilder sb = new StringBuilder(message.length());

        message.chars()
            .forEach(letter ->{
                if (letter >= 'a' && letter <= 'z') {
                    sb.append((char) ('a' + ('z' - letter)));
                } else if (letter >= 'A' && letter <= 'Z') {
                    sb.append((char) ('A' + ('Z' - letter)));
                } else {
                    sb.append((char)letter);
                }
            });

        return sb.toString();
    }

    private String encryptViaTheA1Z26Cipher(String message) {
        StringBuilder sb = new StringBuilder(message.length());

        message.chars()
            .forEach(letter ->{
                if (letter >= 'a' && letter <= 'z') {
                    sb.append(letter - 'a' + 1).append("-");
                } else if (letter >= 'A' && letter <= 'Z') {
                    sb.append(letter - 'A' + 1).append("-");
                } else {
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append((char)letter);
                }
            });

        if (!sb.isEmpty() && sb.charAt(sb.length() - 1) == '-') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

   // @SuppressWarnings("java:S127")
    private String encryptViaTheVigenereCipher(String message, String key) {
        String keyLower = key.toLowerCase();
        String keyUpper = key.toUpperCase();
        StringBuilder sb = new StringBuilder(message.length());
        AtomicInteger keyIndex= new AtomicInteger();

        message.chars()
            .forEach(letter ->{
                if (letter >= 'a' && letter <= 'z') {
                    sb.append((char) (((letter - 'a') + (keyLower.charAt(keyIndex.get()) - 'a')) % NUMBER_OF_LETTERS_IN_ALPHABET + 'a'));
                    keyIndex.set(keyIndex.incrementAndGet() % key.length());
                } else if (letter >= 'A' && letter <= 'Z') {
                    sb.append((char) (((letter - 'A') + (keyUpper.charAt(keyIndex.get()) - 'A')) % NUMBER_OF_LETTERS_IN_ALPHABET + 'A'));
                    keyIndex.set(keyIndex.incrementAndGet() % key.length());
                } else {
                    sb.append((char)letter);
                }
            });

        return sb.toString();
    }
}