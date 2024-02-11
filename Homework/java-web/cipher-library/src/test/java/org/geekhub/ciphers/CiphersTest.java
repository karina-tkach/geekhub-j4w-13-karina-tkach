package org.geekhub.ciphers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CiphersTest {
    @Test
    void encrypt_shouldProperlyEncrypt_whenCaesarCipherIsChosen() {
        Cipher cipher = new CaesarCipher(3);
        String message = "Hello World";
        String expectedMessage = "Khoor Zruog";

        String encryptedMessage = cipher.encrypt(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly via Caesar.");
    }

    @Test
    void decrypt_shouldProperlyDecrypt_whenCaesarCipherIsChosen() {
        Cipher cipher = new CaesarCipher(3);
        String message = "Khoor Zruog";
        String expectedMessage = "Hello World";

        String encryptedMessage = cipher.decrypt(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly via Caesar.");
    }

    @Test
    void encrypt_shouldProperlyEncrypt_whenAtbashCipherIsChosen() {
        Cipher cipher = new AtbashCipher();
        String message = "Hello World";
        String expectedMessage = "Svool Dliow";

        String encryptedMessage = cipher.encrypt(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly via Atbash.");
    }

    @Test
    void decrypt_shouldProperlyDecrypt_whenAtbashCipherIsChosen() {
        Cipher cipher = new AtbashCipher();
        String message = "Svool Dliow";
        String expectedMessage = "Hello World";

        String encryptedMessage = cipher.decrypt(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly via Atbash.");
    }

    @Test
    void encrypt_shouldProperlyEncrypt_whenA1Z26CipherIsChosen() {
        Cipher cipher = new A1Z26Cipher();
        String message = "Hello, World!";
        String expectedMessage = "8-5-12-12-15, 23-15-18-12-4!";

        String encryptedMessage = cipher.encrypt(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly via A1Z26.");
    }

    @Test
    void decrypt_shouldProperlyDecrypt_whenA1Z26CipherIsChosen() {
        Cipher cipher = new A1Z26Cipher();
        String message = "8-5-12-12-15, 23-15-18-12-4! 1+2";
        String expectedMessage = "HELLO, WORLD! A+B";

        String encryptedMessage = cipher.decrypt(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly via A1Z26.");
    }

    @Test
    void encrypt_shouldProperlyEncrypt_whenVigenereCipherIsChosen() {
        Cipher cipher = new VigenereCipher("key");
        String message = "Hello World";
        String expectedMessage = "Rijvs Uyvjn";

        String encryptedMessage = cipher.encrypt(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly via Vigenere.");
    }

    @Test
    void decrypt_shouldProperlyDecrypt_whenVigenereCipherIsChosen() {
        Cipher cipher = new VigenereCipher("key");
        String message = "Rijvs Uyvjn";
        String expectedMessage = "Hello World";

        String encryptedMessage = cipher.decrypt(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly via Vigenere.");
    }

    @Test
    void encrypt_shouldThrowException_whenInvalidVigenereKey() {
        assertThatCode(() -> new VigenereCipher("a1z26").encrypt("Hello"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Incorrect Vigenere key.");

        assertThatCode(() -> new VigenereCipher(null).encrypt("Hello"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Incorrect Vigenere key.");
    }

    @Test
    void decrypt_shouldThrowException_whenInvalidVigenereKey() {
        assertThatCode(() -> new VigenereCipher("a1z26").decrypt("Hello"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Incorrect Vigenere key.");

        assertThatCode(() -> new VigenereCipher(null).decrypt("Hello"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Incorrect Vigenere key.");
    }

    @Test
    void getCipherName_shouldReturnProperName_whenCaesarCipherIsChosen() {
        Cipher cipher = new CaesarCipher(3);
        String expectedName = "Caesar";

        String name = cipher.getCipherName();

        assertEquals(expectedName, name, "Incorrect name for Caesar cipher.");
    }

    @Test
    void getCipherName_shouldReturnProperName_whenAtbashCipherIsChosen() {
        Cipher cipher = new AtbashCipher();
        String expectedName = "Atbash";

        String name = cipher.getCipherName();

        assertEquals(expectedName, name, "Incorrect name for Atbash cipher.");
    }

    @Test
    void getCipherName_shouldReturnProperName_whenA1Z26CipherIsChosen() {
        Cipher cipher = new A1Z26Cipher();
        String expectedName = "A1Z26";

        String name = cipher.getCipherName();

        assertEquals(expectedName, name, "Incorrect name for A1Z26 cipher.");
    }

    @Test
    void getCipherName_shouldReturnProperName_whenVigenereCipherIsChosen() {
        Cipher cipher = new VigenereCipher("key");
        String expectedName = "Vigenere";

        String name = cipher.getCipherName();

        assertEquals(expectedName, name, "Incorrect name for Vigenere cipher.");
    }
}
