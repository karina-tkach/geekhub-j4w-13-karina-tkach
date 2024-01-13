package org.geekhub.encryption;

import org.geekhub.encryption.injector.InjectionExecutor;
import org.geekhub.encryption.repository.EncryptionRepository;
import org.geekhub.encryption.service.EncryptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EncryptionServiceTest {
    @Mock
    private EncryptionRepository encryptionRepository;

    private EncryptionService encryptionService;

    @BeforeEach
    public void setUp() {
        encryptionService = new EncryptionService(encryptionRepository);
        new InjectionExecutor(ClassLoader.getSystemResource("application.properties").getPath().substring(1)).execute(encryptionService);
    }

    @Test
    void encryptMessage_shouldProperlyEncrypt_whenCaesarCipherIsChosen() {
        String message = "Hello";
        String key = "";
        String expectedEncryptedMessage = "Khoor";

        String encryptedMessage = encryptionService.encryptMessage("Caesar", message, key);

        assertEquals(expectedEncryptedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).addToHistoryLog(eq(message),eq("Caesar"),eq(encryptedMessage),any());
    }

    @Test
    void encryptMessage_shouldProperlyEncrypt_whenAtbashCipherIsChosen() {
        String message = "Hello";
        String expectedEncryptedMessage = "Svool";

        String encryptedMessage = encryptionService.encryptMessage("Atbash", message, "");

        assertEquals(expectedEncryptedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).addToHistoryLog(eq(message),eq("Atbash"),eq(encryptedMessage),any());
    }

    @Test
    void encryptMessage_shouldProperlyEncrypt_whenA1Z26CipherIsChosen() {
        String message = "Hello";
        String expectedEncryptedMessage = "8-5-12-12-15";

        String encryptedMessage = encryptionService.encryptMessage("A1Z26", message, "");

        assertEquals(expectedEncryptedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).addToHistoryLog(eq(message),eq("A1Z26"),eq(encryptedMessage),any());
    }

    @Test
    void encryptMessage_shouldProperlyEncrypt_whenROT13CipherIsChosen() {
        String message = "Hello";
        String key = "";
        String expectedEncryptedMessage = "Uryyb";

        String encryptedMessage = encryptionService.encryptMessage("ROT13", message, key);

        assertEquals(expectedEncryptedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).addToHistoryLog(eq(message),eq("ROT13"),eq(encryptedMessage),any());
    }

    @Test
    void encryptMessage_shouldProperlyEncrypt_whenVigenereCipherIsChosen() {
        String message = "Hello";
        String key = "key";
        String expectedEncryptedMessage = "Rijvs";

        String encryptedMessage = encryptionService.encryptMessage("Vigenere", message, key);

        assertEquals(expectedEncryptedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).addToHistoryLog(eq(message),eq("Vigenere"),eq(encryptedMessage),any());
    }

    @ParameterizedTest
    @CsvSource({
        "Caesar, '',key",
        "Vigenere, Hello, 123",
        "InvalidCipher, Hello, 123"
    })
    void encryptMessage_shouldThrowException_whenInvalidDataIsPassed(String cipher, String message, String key) {
        assertThatCode(() -> encryptionService.encryptMessage(cipher, message, key))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Incorrect data for encryption.");
        verify(encryptionRepository, never()).addToHistoryLog(any(), any(), any(), any());
    }
}
