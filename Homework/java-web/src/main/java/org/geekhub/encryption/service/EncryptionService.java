package org.geekhub.encryption.service;

import org.geekhub.encryption.ciphers.Cipher;
import org.geekhub.encryption.models.HistoryEntry;
import org.geekhub.encryption.repository.EncryptionRepositoryInMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@SuppressWarnings("java:S6857")
public class EncryptionService {
    private final EncryptionRepositoryInMemory encryptionRepository;
    private final Cipher cipher;
    @Value("${operation.type:}")
    private String operationType;

    public EncryptionService(EncryptionRepositoryInMemory encryptionRepository, Cipher cipher) {
        this.encryptionRepository = encryptionRepository;
        this.cipher = cipher;
    }

    public String performOperation(String message) {
        if (message.isBlank()) {
            throw new IllegalArgumentException("Incorrect data for encryption.");
        }

        String processedMessage;
        operationType = operationType.toUpperCase();
        if (operationType.equals("ENCRYPTION")) {
            processedMessage = cipher.encrypt(message);
        } else if (operationType.equals("DECRYPTION")) {
            processedMessage = cipher.decrypt(message);
        } else {
            throw new IllegalArgumentException("Illegal operation type.");
        }

        OffsetDateTime dateTime = OffsetDateTime.now();
        HistoryEntry entry = new HistoryEntry(message, processedMessage, cipher.getCipherName(), dateTime, operationType);
        encryptionRepository.saveEncoding(entry);

        return processedMessage;
    }
}
