package org.geekhub.encryption;

import org.geekhub.encryption.ciphers.A1Z26Cipher;
import org.geekhub.encryption.ciphers.AtbashCipher;
import org.geekhub.encryption.ciphers.CaesarCipher;
import org.geekhub.encryption.ciphers.Cipher;
import org.geekhub.encryption.ciphers.VigenereCipher;
import org.geekhub.encryption.repository.EncryptionRepository;
import org.geekhub.encryption.service.EncryptionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

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
    private static Cipher caesar;
    private static Cipher vigenere;

    @BeforeAll
    public static void setUp(){
        caesar = new CaesarCipher();
        vigenere = new VigenereCipher();
        ReflectionTestUtils.setField(caesar,"caesarKey", 3);
        ReflectionTestUtils.setField(vigenere,"key", "key");
    }

    @Test
    void encryptMessage_shouldProperlyEncrypt_whenCaesarCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, caesar);
        String message = "Hello";
        String expectedEncryptedMessage = "Khoor";

        String encryptedMessage = encryptionService.encryptMessage(message);

        assertEquals(expectedEncryptedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).addToHistoryLog(eq(message), eq("Caesar"), eq(encryptedMessage), any());
    }

    @Test
    void encryptMessage_shouldProperlyEncrypt_whenAtbashCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, new AtbashCipher());
        String message = "Hello";
        String expectedEncryptedMessage = "Svool";

        String encryptedMessage = encryptionService.encryptMessage(message);

        assertEquals(expectedEncryptedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).addToHistoryLog(eq(message), eq("Atbash"), eq(encryptedMessage), any());
    }

    @Test
    void encryptMessage_shouldProperlyEncrypt_whenA1Z26CipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, new A1Z26Cipher());
        String message = "Hello";
        String expectedEncryptedMessage = "8-5-12-12-15";

        String encryptedMessage = encryptionService.encryptMessage(message);

        assertEquals(expectedEncryptedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).addToHistoryLog(eq(message), eq("A1Z26"), eq(encryptedMessage), any());
    }

    @Test
    void encryptMessage_shouldProperlyEncrypt_whenVigenereCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, vigenere);
        String message = "Hello";
        String expectedEncryptedMessage = "Rijvs";

        String encryptedMessage = encryptionService.encryptMessage(message);

        assertEquals(expectedEncryptedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).addToHistoryLog(eq(message), eq("Vigenere"), eq(encryptedMessage), any());
    }

    @Test
    void encryptMessage_shouldThrowException_whenInvalidMessageIsPassed() {
        encryptionService = new EncryptionService(encryptionRepository, caesar);
        assertThatCode(() -> encryptionService.encryptMessage(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Incorrect data for encryption.");
        verify(encryptionRepository, never()).addToHistoryLog(any(), any(), any(), any());
    }
}
