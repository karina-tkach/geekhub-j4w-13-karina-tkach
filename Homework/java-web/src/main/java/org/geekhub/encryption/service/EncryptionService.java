package org.geekhub.encryption.service;

import org.geekhub.ciphers.Cipher;
import org.geekhub.encryption.ciphers.CipherFactory;
import org.geekhub.encryption.exception.OperationFailedException;
import org.geekhub.encryption.models.EncodeDataDTO;
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
    private final int activeUserId;

    public EncryptionService(EncryptionRepository encryptionRepository,
                             @Value("${active.user.id}") int activeUserId) {
        this.encryptionRepository = encryptionRepository;
        this.activeUserId = activeUserId;
    }

    public String performOperation(EncodeDataDTO encodeDataDTO) {
        String processedMessage = null;
        Cipher cipher = CipherFactory.getCipher(encodeDataDTO.getCipherAlgorithm(),
            encodeDataDTO.getShift(), encodeDataDTO.getKey());

        try {
            if (encodeDataDTO.getOriginalMessage().isBlank()) {
                throw new IllegalArgumentException("Incorrect data for encryption.");
            }

            if (Objects.requireNonNull(encodeDataDTO.getOperationType()) == OperationType.ENCRYPTION) {
                processedMessage = cipher.encrypt(encodeDataDTO.getOriginalMessage());
            } else {
                processedMessage = cipher.decrypt(encodeDataDTO.getOriginalMessage());
            }

            OffsetDateTime dateTime = OffsetDateTime.now();
            HistoryEntry entry = new HistoryEntry(activeUserId, encodeDataDTO.getOriginalMessage(),
                processedMessage, encodeDataDTO.getCipherAlgorithm().name(), dateTime,
                encodeDataDTO.getOperationType().name(), SUCCESS_OPERATION_STATUS);

            encryptionRepository.saveEncoding(entry);
        } catch (Exception ex) {
            OffsetDateTime dateTime = OffsetDateTime.now();
            HistoryEntry entry = new HistoryEntry(activeUserId, encodeDataDTO.getOriginalMessage(),
                processedMessage, Objects.requireNonNull(encodeDataDTO.getCipherAlgorithm()).name(), dateTime,
                Objects.requireNonNull(encodeDataDTO.getOperationType()).name(), FAIL_OPERATION_STATUS);

            encryptionRepository.saveEncoding(entry);
            throw new OperationFailedException(ex);
        }

        return processedMessage;
    }
}
