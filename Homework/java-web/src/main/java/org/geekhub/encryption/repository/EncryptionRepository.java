package org.geekhub.encryption.repository;

import org.geekhub.encryption.models.HistoryEntry;

import java.sql.Timestamp;
import java.util.List;

public interface EncryptionRepository {
    void saveEncoding(HistoryEntry entry);
    List<HistoryEntry> getHistoryByAlgorithm(String algorithm);
    List<HistoryEntry> getHistoryInDateRange(Timestamp from, Timestamp to);
    List<HistoryEntry> getHistoryByAlgorithmAndOperationType(String algorithm, String operationType);
    List<HistoryEntry> getFullHistoryWithPagination(int pageNumber, int limit);
    List<HistoryEntry> getFullHistoryWithPaginationAndUserId(int userId, int pageNumber, int limit);
}
