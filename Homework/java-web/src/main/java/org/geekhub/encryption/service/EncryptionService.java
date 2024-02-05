package org.geekhub.encryption.service;

import org.geekhub.encryption.ciphers.Cipher;
import org.geekhub.encryption.exception.OperationFailedException;
import org.geekhub.encryption.models.HistoryEntry;
import org.geekhub.encryption.repository.EncryptionRepositoryInMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class EncryptionService {
    private final EncryptionRepositoryInMemory encryptionRepository;
    private static final String SUCCESS_OPERATION_STATUS = "SUCCESS";
    private static final String FAIL_OPERATION_STATUS = "FAIL";
    private final Cipher cipher;
    private String operationType;

    public EncryptionService(EncryptionRepositoryInMemory encryptionRepository, Cipher cipher,
                             @Value("${operation.type:}") String operationType) {
        this.encryptionRepository = encryptionRepository;
        this.cipher = cipher;
        this.operationType = operationType;
    }

    public String performOperation(String message) {
        String processedMessage = null;
        try {
            if (message.isBlank()) {
                throw new IllegalArgumentException("Incorrect data for encryption.");
            }

            operationType = operationType.toUpperCase();
            if (operationType.equals("ENCRYPTION")) {
                processedMessage = cipher.encrypt(message);
            } else if (operationType.equals("DECRYPTION")) {
                processedMessage = cipher.decrypt(message);
            } else {
                throw new IllegalArgumentException("Illegal operation type.");
            }

            OffsetDateTime dateTime = OffsetDateTime.now();
            HistoryEntry entry = new HistoryEntry(message, processedMessage, cipher.getCipherName(), dateTime, operationType, SUCCESS_OPERATION_STATUS);
            encryptionRepository.saveEncoding(entry);
        } catch (Exception ex) {
            OffsetDateTime dateTime = OffsetDateTime.now();
            HistoryEntry entry = new HistoryEntry(message, processedMessage, cipher.getCipherName(), dateTime, operationType, FAIL_OPERATION_STATUS);
            encryptionRepository.saveEncoding(entry);
            throw new OperationFailedException(ex);
        }

        return processedMessage;
    }
}
