package org.geekhub.encryption;

import org.geekhub.encryption.ciphers.A1Z26Cipher;
import org.geekhub.encryption.ciphers.AtbashCipher;
import org.geekhub.encryption.ciphers.CaesarCipher;
import org.geekhub.encryption.ciphers.Cipher;
import org.geekhub.encryption.ciphers.VigenereCipher;
import org.geekhub.encryption.repository.EncryptionRepositoryInMemory;
import org.geekhub.encryption.service.EncryptionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EncryptionServiceTest {
    @Mock
    private EncryptionRepositoryInMemory encryptionRepository;
    private EncryptionService encryptionService;
    private static Cipher caesar;
    private static Cipher vigenere;

    @BeforeAll
    public static void setUp() {
        caesar = new CaesarCipher(3);
        vigenere = new VigenereCipher("key");
    }

    @Test
    void performOperation_shouldProperlyEncrypt_whenCaesarCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, caesar, "ENCRYPTION");
        String message = "Hello";
        String expectedMessage = "Khoor";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyDecrypt_whenCaesarCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, caesar, "DECRYPTION");
        String message = "Khoor";
        String expectedMessage = "Hello";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyEncrypt_whenAtbashCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, new AtbashCipher(), "ENCRYPTION");
        String message = "Hello";
        String expectedMessage = "Svool";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyDecrypt_whenAtbashCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, new AtbashCipher(), "DECRYPTION");
        String message = "Svool";
        String expectedMessage = "Hello";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyEncrypt_whenA1Z26CipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, new A1Z26Cipher(), "ENCRYPTION");
        String message = "Hello!";
        String expectedMessage = "8-5-12-12-15!";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyDecrypt_whenA1Z26CipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, new A1Z26Cipher(), "DECRYPTION");
        String message = "8-5-12-12-15";
        String expectedMessage = "HELLO";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyEncrypt_whenVigenereCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, vigenere, "ENCRYPTION");
        String message = "Hello";
        String expectedMessage = "Rijvs";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyDecrypt_whenVigenereCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, vigenere, "DECRYPTION");
        String message = "Rijvs";
        String expectedMessage = "Hello";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldThrowException_whenInvalidMessageIsPassed() {
        encryptionService = new EncryptionService(encryptionRepository, caesar, "ENCRYPTION");
        assertThatCode(() -> encryptionService.performOperation(""))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("java.lang.IllegalArgumentException: Incorrect data for encryption.");
    }

    @Test
    void performOperation_shouldThrowException_whenNoOperationTypeIsProvided() {
        encryptionService = new EncryptionService(encryptionRepository, caesar, "abc");
        assertThatCode(() -> encryptionService.performOperation("abcd"))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("java.lang.IllegalArgumentException: Illegal operation type.");
    }
}
