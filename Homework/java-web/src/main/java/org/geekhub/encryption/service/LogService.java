package org.geekhub.encryption.service;

import org.geekhub.encryption.models.HistoryEntry;
import org.geekhub.encryption.repository.EncryptionRepositoryInMemory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LogService {
    private final EncryptionRepositoryInMemory encryptionRepository;

    public LogService(EncryptionRepositoryInMemory encryptionRepository) {
        this.encryptionRepository = encryptionRepository;
    }

    public void saveEncoding(HistoryEntry entry){

    }
    public List<HistoryEntry> getHistoryByAlgorithm(String algorithm){
        return null;

    }
    public List<HistoryEntry> getHistoryInDateRange(OffsetDateTime from, OffsetDateTime to){
        return null;

    }
    public List<HistoryEntry> getHistoryByAlgorithmAndOperationType(String algorithm, String operationType){
        return null;

    }
    public List<HistoryEntry> getFullHistoryWithPagination(int pageNumber, int limit){
        return null;

    }
    public List<HistoryEntry> getFullHistoryWithPaginationAndUserId(int userId, int pageNumber, int limit){
        return null;
    }
}
