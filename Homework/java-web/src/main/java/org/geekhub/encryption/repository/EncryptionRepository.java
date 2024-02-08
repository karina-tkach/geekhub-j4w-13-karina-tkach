package org.geekhub.encryption.repository;

import org.geekhub.encryption.models.HistoryEntry;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface EncryptionRepository {
    void saveEncoding(HistoryEntry entry);
    void deleteHistoryRecordById(int id);
    Optional<HistoryEntry> getHistoryRecordById(int id);
    List<HistoryEntry> getFullHistory();
    List<HistoryEntry> getHistoryRecordsByUserId(int userId);
    List<HistoryEntry> getHistoryByAlgorithm(String algorithm);
    List<HistoryEntry> getHistoryInDateRange(@Nullable OffsetDateTime from, @Nullable OffsetDateTime to);
    List<HistoryEntry> getHistoryByAlgorithmAndOperationType(String algorithm, String operationType);
    List<HistoryEntry> getFullHistoryWithPagination(int pageNumber, int limit);
    List<HistoryEntry> getFullHistoryWithPaginationAndUserId(int userId, int pageNumber, int limit);
}
