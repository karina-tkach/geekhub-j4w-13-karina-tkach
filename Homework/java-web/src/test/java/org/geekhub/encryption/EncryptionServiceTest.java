package org.geekhub.encryption;

import org.geekhub.encryption.exception.OperationFailedException;
import org.geekhub.encryption.models.CipherAlgorithm;
import org.geekhub.encryption.models.EncodeDataDTO;
import org.geekhub.encryption.models.OperationType;
import org.geekhub.encryption.repository.EncryptionRepositoryImpl;
import org.geekhub.encryption.service.EncryptionService;
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

    @Test
    void performOperation_shouldProperlyEncrypt_whenCaesarCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, 1);
        EncodeDataDTO encodeDataDTO = new EncodeDataDTO();
        encodeDataDTO.setCipherAlgorithm(CipherAlgorithm.CAESAR);
        encodeDataDTO.setOperationType(OperationType.ENCRYPTION);
        encodeDataDTO.setShift(3);
        encodeDataDTO.setOriginalMessage("Hello");
        String expectedMessage = "Khoor";

        String encryptedMessage = encryptionService.performOperation(encodeDataDTO);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyDecrypt_whenCaesarCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, 1);
        EncodeDataDTO encodeDataDTO = new EncodeDataDTO();
        encodeDataDTO.setCipherAlgorithm(CipherAlgorithm.CAESAR);
        encodeDataDTO.setOperationType(OperationType.DECRYPTION);
        encodeDataDTO.setShift(3);
        encodeDataDTO.setOriginalMessage("Khoor");
        String expectedMessage = "Hello";

        String encryptedMessage = encryptionService.performOperation(encodeDataDTO);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyEncrypt_whenAtbashCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, 1);
        EncodeDataDTO encodeDataDTO = new EncodeDataDTO();
        encodeDataDTO.setCipherAlgorithm(CipherAlgorithm.ATBASH);
        encodeDataDTO.setOperationType(OperationType.ENCRYPTION);
        encodeDataDTO.setOriginalMessage("Hello");
        String expectedMessage = "Svool";

        String encryptedMessage = encryptionService.performOperation(encodeDataDTO);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyDecrypt_whenAtbashCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, 1);
        EncodeDataDTO encodeDataDTO = new EncodeDataDTO();
        encodeDataDTO.setCipherAlgorithm(CipherAlgorithm.ATBASH);
        encodeDataDTO.setOperationType(OperationType.DECRYPTION);
        encodeDataDTO.setOriginalMessage("Svool");
        String expectedMessage = "Hello";

        String encryptedMessage = encryptionService.performOperation(encodeDataDTO);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyEncrypt_whenA1Z26CipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, 1);
        EncodeDataDTO encodeDataDTO = new EncodeDataDTO();
        encodeDataDTO.setCipherAlgorithm(CipherAlgorithm.A1Z26);
        encodeDataDTO.setOperationType(OperationType.ENCRYPTION);
        encodeDataDTO.setOriginalMessage("Hello!");
        String expectedMessage = "8-5-12-12-15!";

        String encryptedMessage = encryptionService.performOperation(encodeDataDTO);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyDecrypt_whenA1Z26CipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, 1);
        EncodeDataDTO encodeDataDTO = new EncodeDataDTO();
        encodeDataDTO.setCipherAlgorithm(CipherAlgorithm.A1Z26);
        encodeDataDTO.setOperationType(OperationType.DECRYPTION);
        encodeDataDTO.setOriginalMessage("8-5-12-12-15!");
        String expectedMessage = "HELLO!";

        String encryptedMessage = encryptionService.performOperation(encodeDataDTO);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyEncrypt_whenVigenereCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, 1);
        EncodeDataDTO encodeDataDTO = new EncodeDataDTO();
        encodeDataDTO.setCipherAlgorithm(CipherAlgorithm.VIGENERE);
        encodeDataDTO.setOperationType(OperationType.ENCRYPTION);
        encodeDataDTO.setKey("Key");
        encodeDataDTO.setOriginalMessage("Hello");
        String expectedMessage = "Rijvs";

        String encryptedMessage = encryptionService.performOperation(encodeDataDTO);

        assertEquals(expectedMessage, encryptedMessage, "The message was not encrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldProperlyDecrypt_whenVigenereCipherIsChosen() {
        encryptionService = new EncryptionService(encryptionRepository, 1);
        EncodeDataDTO encodeDataDTO = new EncodeDataDTO();
        encodeDataDTO.setCipherAlgorithm(CipherAlgorithm.VIGENERE);
        encodeDataDTO.setOperationType(OperationType.DECRYPTION);
        encodeDataDTO.setKey("Key");
        encodeDataDTO.setOriginalMessage("Rijvs");
        String expectedMessage = "Hello";

        String encryptedMessage = encryptionService.performOperation(encodeDataDTO);

        assertEquals(expectedMessage, encryptedMessage, "The message was not decrypted correctly.");
        verify(encryptionRepository, times(1)).saveEncoding(any());
    }

    @Test
    void performOperation_shouldThrowException_whenInvalidMessageIsPassed() {
        EncodeDataDTO encodeDataDTO = new EncodeDataDTO();
        encodeDataDTO.setCipherAlgorithm(CipherAlgorithm.CAESAR);
        encodeDataDTO.setOperationType(OperationType.ENCRYPTION);
        encodeDataDTO.setShift(3);
        encodeDataDTO.setOriginalMessage("");
        encryptionService = new EncryptionService(encryptionRepository, 1);
        assertThatCode(() -> encryptionService.performOperation(encodeDataDTO))
            .isInstanceOf(OperationFailedException.class)
            .hasMessage("java.lang.IllegalArgumentException: Incorrect data for encryption.");
    }
}
