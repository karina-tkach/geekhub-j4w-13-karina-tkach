package org.geekhub.encryption.repository;

import org.geekhub.encryption.models.HistoryEntry;

import java.time.OffsetDateTime;
import java.util.List;

public interface EncryptionRepository {
    void saveEncoding(HistoryEntry entry);
    List<HistoryEntry> getHistoryByAlgorithm(String algorithm);
    List<HistoryEntry> getHistoryInDateRange(OffsetDateTime from, OffsetDateTime to);
    List<HistoryEntry> getHistoryByAlgorithmAndOperationType(String algorithm, String operationType);
    List<HistoryEntry> getFullHistoryWithPagination(int pageNumber, int pageSize);
    List<HistoryEntry> getFullHistoryWithPaginationAndUserId(int userId, int pageNumber, int pageSize);
}
