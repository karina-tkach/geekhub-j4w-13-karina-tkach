package org.geekhub.encryption.service;

import org.geekhub.ciphers.Cipher;
import org.geekhub.encryption.models.CipherAlgorithm;
import org.geekhub.encryption.ciphers.CipherFactory;
import org.geekhub.encryption.exception.OperationFailedException;
import org.geekhub.encryption.models.HistoryEntry;
import org.geekhub.encryption.models.OperationType;
import org.geekhub.encryption.repository.EncryptionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Objects;

@Service
public class EncryptionService {
    private final EncryptionRepository encryptionRepository;
    private static final String SUCCESS_OPERATION_STATUS = "SUCCESS";
    private static final String FAIL_OPERATION_STATUS = "FAIL";
    private final CipherFactory cipherFactory;
    private final CipherAlgorithm cipherAlgorithm;
    private final OperationType operationType;
    private final int activeUserId;

    public EncryptionService(EncryptionRepository encryptionRepository, CipherFactory cipherFactory,
                             @Value("${active.cipher}") String cipherAlgorithm,
                             @Value("${operation.type}") String operationType,
                             @Value("${active.user.id}") int activeUserId) {
        this.encryptionRepository = encryptionRepository;
        this.cipherFactory = cipherFactory;
        this.cipherAlgorithm = CipherAlgorithm.valueOf(cipherAlgorithm);
        this.operationType = OperationType.valueOf(operationType);
        this.activeUserId = activeUserId;
    }

    public String performOperation(String message) {
        Cipher cipher = cipherFactory.getCipher(cipherAlgorithm);
        String processedMessage = null;

        try {
            if (message.isBlank()) {
                throw new IllegalArgumentException("Incorrect data for encryption.");
            }

            if (Objects.requireNonNull(operationType) == OperationType.ENCRYPTION) {
                processedMessage = cipher.encrypt(message);
            } else {
                processedMessage = cipher.decrypt(message);
            }

            OffsetDateTime dateTime = OffsetDateTime.now();
            HistoryEntry entry = new HistoryEntry(activeUserId, message, processedMessage, cipherAlgorithm.name(), dateTime, operationType.name(), SUCCESS_OPERATION_STATUS);

            encryptionRepository.saveEncoding(entry);
        } catch (Exception ex) {
            OffsetDateTime dateTime = OffsetDateTime.now();
            HistoryEntry entry = new HistoryEntry(activeUserId, message, processedMessage, cipherAlgorithm.name(), dateTime, Objects.requireNonNull(operationType).name(), FAIL_OPERATION_STATUS);

            encryptionRepository.saveEncoding(entry);
            throw new OperationFailedException(ex);
        }

        return processedMessage;
    }
}
