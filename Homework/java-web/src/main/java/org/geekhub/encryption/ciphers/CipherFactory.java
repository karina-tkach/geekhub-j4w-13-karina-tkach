package org.geekhub.encryption.ciphers;

import org.geekhub.ciphers.A1Z26Cipher;
import org.geekhub.ciphers.AtbashCipher;
import org.geekhub.ciphers.CaesarCipher;
import org.geekhub.ciphers.Cipher;
import org.geekhub.ciphers.VigenereCipher;
import org.springframework.stereotype.Component;

@Component
public class CipherFactory {
    private CipherFactory() {}
    public static Cipher getCipher(CipherAlgorithm cipherAlgorithm, int shift, String key) {
        return switch (cipherAlgorithm) {
            case CAESAR -> new CaesarCipher(shift);
            case A1Z26 -> new A1Z26Cipher();
            case ATBASH -> new AtbashCipher();
            case VIGENERE -> new VigenereCipher(key);
        };
    }
}
