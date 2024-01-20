package org.geekhub.hw12.ciphers;

import org.geekhub.hw12.ciphers.model.CaesarCipherDto;

import java.util.stream.Collectors;

public class CaesarCipher implements Cipher<CaesarCipherDto> {
    @Override
    public String encode(CaesarCipherDto encodeData) {
        return encodeData.getMessage().chars()
            .mapToObj(letter -> String.valueOf(getCharForShiftEncryption(letter, encodeData.getShift())))
            .collect(Collectors.joining());
    }

    private char getCharForShiftEncryption(int letter, int shift) {
        int keyLetter = getKeyLetter(letter);
        if (keyLetter == 0) {
            return (char) letter;
        }

        return (char) ((letter - keyLetter + shift) % 26 + keyLetter);
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

    @Override
    public String decode(CaesarCipherDto decodeData) {
        return decodeData.getMessage().chars()
            .mapToObj(letter -> String.valueOf(getCharForShiftEncryption(letter, 26 - (decodeData.getShift()))))
            .collect(Collectors.joining());
    }
}
