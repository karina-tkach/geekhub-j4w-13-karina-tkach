package org.geekhub.hw12.ciphers;

import org.geekhub.hw12.ciphers.model.VigenereCipherDto;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class VigenereCipher implements Cipher<VigenereCipherDto> {
    private final AtomicInteger keyIndex;
    public VigenereCipher(){
        this.keyIndex = new AtomicInteger();
    }
    @Override
    public String encode(VigenereCipherDto encodeData) {
        String keyLower = encodeData.getKeyword().toLowerCase();
        String keyUpper = encodeData.getKeyword().toUpperCase();

        return encodeData.getMessage().chars()
            .mapToObj(letter -> String.valueOf(getCharForVigenereEncryption(letter, keyLower, keyUpper)))
            .collect(Collectors.joining());
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

    private char getCharForVigenereEncryption(int letter, String keyLower, String keyUpper) {
        int keyLetter = getKeyLetter(letter);
        if (keyLetter == 0) {
            return (char) letter;
        }

        String key = getKeyInValidCase(keyLetter, keyLower, keyUpper);

        char res = (char) (((letter - keyLetter) + (key.charAt(keyIndex.get()) - keyLetter)) % 26 + keyLetter);
        keyIndex.set(keyIndex.incrementAndGet() % key.length());
        return res;
    }

    private String getKeyInValidCase(int keyLetter, String keyLower, String keyUpper){
        return keyLetter == 'a' ? keyLower : keyUpper;
    }

    @Override
    public String decode(VigenereCipherDto decodeData) {
        keyIndex.set(0);
        String keyLower = decodeData.getKeyword().toLowerCase();
        String keyUpper = decodeData.getKeyword().toUpperCase();

        return decodeData.getMessage().chars()
            .mapToObj(letter -> String.valueOf(getCharForVigenereDecryption(letter, keyLower, keyUpper)))
            .collect(Collectors.joining());
    }

    private char getCharForVigenereDecryption(int letter, String keyLower, String keyUpper) {
        int keyLetter = getKeyLetter(letter);
        if (keyLetter == 0) {
            return (char) letter;
        }

        String key = getKeyInValidCase(keyLetter, keyLower, keyUpper);

        char res = (char) ((letter - key.charAt(keyIndex.get()) +26) % 26 + keyLetter);
        keyIndex.set(keyIndex.incrementAndGet() % key.length());
        return res;
    }
}
