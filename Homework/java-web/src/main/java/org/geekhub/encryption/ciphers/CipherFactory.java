package org.geekhub.encryption.ciphers;

import org.geekhub.encryption.models.CipherAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class CipherFactory {
    private final CaesarCipher caesarCipher;
    private final A1Z26Cipher a1Z26Cipher;
    private final AtbashCipher atbashCipher;
    private final VigenereCipher vigenereCipher;

    public CipherFactory(CaesarCipher caesarCipher, A1Z26Cipher a1Z26Cipher,
                         AtbashCipher atbashCipher, VigenereCipher vigenereCipher) {
        this.caesarCipher = caesarCipher;
        this.a1Z26Cipher = a1Z26Cipher;
        this.atbashCipher = atbashCipher;
        this.vigenereCipher = vigenereCipher;
    }

    public Cipher getCipher(CipherAlgorithm cipherAlgorithm) {
        return switch (cipherAlgorithm) {
            case CAESAR -> caesarCipher;
            case A1Z26 -> a1Z26Cipher;
            case ATBASH -> atbashCipher;
            case VIGENERE -> vigenereCipher;
        };
    }
}
