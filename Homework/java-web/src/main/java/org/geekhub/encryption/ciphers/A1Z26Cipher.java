package org.geekhub.encryption.ciphers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
@Profile("A1Z26-cipher")
public class A1Z26Cipher implements Cipher {
    private final AtomicBoolean lastCharIsLetter;

    public A1Z26Cipher() {
        this.lastCharIsLetter = new AtomicBoolean();
    }

    @Override
    public String encrypt(String message) {
        lastCharIsLetter.set(false);
        return message.chars()
            .mapToObj(this::getStringForA1Z26Encryption)
            .collect(Collectors.joining());
    }

    @Override
    public String getCipherName() {
        return "A1Z26";
    }

    private String getStringForA1Z26Encryption(int letter) {
        int keyLetter = getKeyLetter(letter);
        if (keyLetter == 0) {
            lastCharIsLetter.set(false);
            return String.valueOf((char) letter);
        }

        String result = String.valueOf(letter - keyLetter + 1);
        if (lastCharIsLetter.get()) {
            result = "-" + result;
        }
        lastCharIsLetter.set(true);
        return result;
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
