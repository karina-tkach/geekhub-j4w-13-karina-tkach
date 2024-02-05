package org.geekhub.encryption.service;

import org.geekhub.encryption.models.HistoryEntry;
import org.geekhub.encryption.repository.EncryptionRepositoryInMemory;
import org.geekhub.encryption.validators.LogValidator;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class LogService {
    private final EncryptionRepositoryInMemory encryptionRepository;
    private final LogValidator logValidator;

    public LogService(EncryptionRepositoryInMemory encryptionRepository, LogValidator logValidator) {
        this.encryptionRepository = encryptionRepository;
        this.logValidator = logValidator;
    }

    public List<HistoryEntry> getHistoryByAlgorithm(String algorithm) {
        if (!logValidator.validateAlgorithmName(algorithm)) {
            throw new IllegalArgumentException("Illegal algorithm name for search.");
        }

        return encryptionRepository.getHistoryByAlgorithm(algorithm);
    }

    public List<HistoryEntry> getHistoryInDateRange(OffsetDateTime from, OffsetDateTime to) {
        return encryptionRepository.getHistoryInDateRange(from, to);
    }

    public List<HistoryEntry> getHistoryByAlgorithmAndOperationType(String algorithm, String operationType) {
        if (!(logValidator.validateAlgorithmName(algorithm) && logValidator.validateOperationType(operationType))) {
            throw new IllegalArgumentException("Illegal algorithm name or operation type for search.");
        }

        return encryptionRepository.getHistoryByAlgorithmAndOperationType(algorithm, operationType);
    }

    public List<HistoryEntry> getFullHistoryWithPagination(int pageNumber, int limit) {
        if (!logValidator.validatePaginationParameters(pageNumber, limit)) {
            throw new IllegalArgumentException("Illegal pagination parameters for search.");
        }

        return encryptionRepository.getFullHistoryWithPagination(pageNumber, limit);
    }

    public List<HistoryEntry> getFullHistoryWithPaginationAndUserId(int userId, int pageNumber, int limit) {
        if (!logValidator.validatePaginationParameters(pageNumber, limit)) {
            throw new IllegalArgumentException("Illegal pagination parameters for search.");
        }

        return encryptionRepository.getFullHistoryWithPaginationAndUserId(userId, pageNumber, limit);
    }
}
