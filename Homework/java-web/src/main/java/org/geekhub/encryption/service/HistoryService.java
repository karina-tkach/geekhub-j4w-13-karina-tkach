package org.geekhub.encryption.service;

import org.geekhub.encryption.models.HistoryEntry;
import org.geekhub.encryption.repository.EncryptionRepository;
import org.geekhub.encryption.validators.HistoryParametersValidator;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class HistoryService {
    private final EncryptionRepository encryptionRepository;
    private final HistoryParametersValidator historyParametersValidator;

    public HistoryService(EncryptionRepository encryptionRepository, HistoryParametersValidator historyParametersValidator) {
        this.encryptionRepository = encryptionRepository;
        this.historyParametersValidator = historyParametersValidator;
    }

    public List<HistoryEntry> getHistoryByAlgorithm(String algorithm) {
        if (historyParametersValidator.validateAlgorithmName(algorithm)) {
            return encryptionRepository.getHistoryByAlgorithm(algorithm);
        }

        throw new IllegalArgumentException("Illegal algorithm name for search.");
    }

    public void deleteHistoryRecordById(int id) {
        encryptionRepository.deleteHistoryRecordById(id);
    }

    public HistoryEntry getHistoryRecordById(int id) {
        return encryptionRepository.getHistoryRecordById(id)
            .orElseThrow(() -> new IllegalArgumentException("Record with id " + id + " was not found."));
    }

    public List<HistoryEntry> getFullHistory() {
        return encryptionRepository.getFullHistory();
    }

    public List<HistoryEntry> getHistoryRecordsByUserId(int userId) {
        return encryptionRepository.getHistoryRecordsByUserId(userId);
    }

    public List<HistoryEntry> getHistoryInDateRange(OffsetDateTime from, OffsetDateTime to) {
        if (historyParametersValidator.validateDate(from, to)) {
            return encryptionRepository.getHistoryInDateRange(from, to);
        }

        throw new IllegalArgumentException("From date must be before to date.");
    }

    public List<HistoryEntry> getHistoryByAlgorithmAndOperationType(String algorithm, String operationType) {
        if (historyParametersValidator.validateAlgorithmName(algorithm)
            && historyParametersValidator.validateOperationType(operationType)) {
            return encryptionRepository.getHistoryByAlgorithmAndOperationType(algorithm, operationType);
        }

        throw new IllegalArgumentException("Illegal algorithm name or operation type for search.");
    }

    public List<HistoryEntry> getFullHistoryWithPagination(int pageNumber, int limit) {
        if (historyParametersValidator.validatePaginationParameters(pageNumber, limit)) {
            return encryptionRepository.getFullHistoryWithPagination(pageNumber, limit);
        }

        throw new IllegalArgumentException("Illegal pagination parameters for search.");
    }

    public List<HistoryEntry> getFullHistoryWithPaginationAndUserId(int userId, int pageNumber, int limit) {
        if (historyParametersValidator.validatePaginationParameters(pageNumber, limit)) {
            return encryptionRepository.getFullHistoryWithPaginationAndUserId(userId, pageNumber, limit);
        }

        throw new IllegalArgumentException("Illegal pagination parameters for search.");
    }
    public int getHistoryRowsCount() {
        return encryptionRepository.getHistoryRowsCount();
    }
}
