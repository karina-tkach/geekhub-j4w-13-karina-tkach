package org.geekhub.encryption;

import org.geekhub.ciphers.A1Z26Cipher;
import org.geekhub.ciphers.AtbashCipher;
import org.geekhub.ciphers.CaesarCipher;
import org.geekhub.ciphers.VigenereCipher;
import org.geekhub.encryption.ciphers.CipherFactory;
import org.geekhub.encryption.exception.OperationFailedException;
import org.geekhub.encryption.repository.EncryptionRepositoryImpl;
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
    private EncryptionRepositoryImpl encryptionRepository;
    private EncryptionService encryptionService;
    private static CipherFactory cipherFactory;

    @BeforeAll
    public static void setUp() {
        cipherFactory = new CipherFactory(new CaesarCipher(3), new A1Z26Cipher(),
            new AtbashCipher(), new VigenereCipher("key"));
    }

    @Test
    void performOperation_shouldProperlyEncrypt_whenCaesarCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, cipherFactory, "CAESAR", "ENCRYPTION", 1);
        String message = "Hello";
        String expectedMessage = "Khoor";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyDecrypt_whenCaesarCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, cipherFactory, "CAESAR", "DECRYPTION", 1);
        String message = "Khoor";
        String expectedMessage = "Hello";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyEncrypt_whenAtbashCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, cipherFactory, "ATBASH", "ENCRYPTION", 1);
        String message = "Hello";
        String expectedMessage = "Svool";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyDecrypt_whenAtbashCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, cipherFactory, "ATBASH", "DECRYPTION", 1);
        String message = "Svool";
        String expectedMessage = "Hello";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyEncrypt_whenA1Z26CipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, cipherFactory, "A1Z26", "ENCRYPTION", 1);
        String message = "Hello!";
        String expectedMessage = "8-5-12-12-15!";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyDecrypt_whenA1Z26CipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, cipherFactory, "A1Z26", "DECRYPTION", 1);
        String message = "8-5-12-12-15";
        String expectedMessage = "HELLO";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyEncrypt_whenVigenereCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, cipherFactory, "VIGENERE", "ENCRYPTION", 1);
        String message = "Hello";
        String expectedMessage = "Rijvs";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyDecrypt_whenVigenereCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, cipherFactory, "VIGENERE", "DECRYPTION", 1);
        String message = "Rijvs";
        String expectedMessage = "Hello";

        String encryptedMessage = encryptionService.performOperation(message);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldThrowException_whenInvalidMessageIsPassed() {
        encryptionService = new EncryptionService(encryptionRepository, cipherFactory, "CAESAR", "ENCRYPTION", 1);
        assertThatCode(() -> encryptionService.performOperation(""))
            .isInstanceOf(OperationFailedException.class)
            .hasMessage("java.lang.IllegalArgumentException: Incorrect data for encryption.");
    }

    @Test
    void performOperation_shouldThrowException_whenNoOperationTypeIsProvided() {
        assertThatCode(() -> new EncryptionService(encryptionRepository, cipherFactory, "CAESAR", "", 1))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
