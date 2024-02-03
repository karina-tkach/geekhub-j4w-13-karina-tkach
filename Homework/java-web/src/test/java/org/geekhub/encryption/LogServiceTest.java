package org.geekhub.encryption;

import org.geekhub.encryption.models.HistoryEntry;
import org.geekhub.encryption.repository.EncryptionRepositoryInMemory;
import org.geekhub.encryption.service.LogService;
import org.geekhub.encryption.validators.LogValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {
    @Mock
    private EncryptionRepositoryInMemory encryptionRepository;
    private final LogValidator logValidator = new LogValidator();
    private LogService logService;

    @BeforeEach
    public void setUp() {
        logService = new LogService(encryptionRepository, logValidator);
    }

    @Test
    void getHistoryByAlgorithm_shouldReturnProperList_whenValidParameters() {
        List<HistoryEntry> log = new ArrayList<>();
        log.add(new HistoryEntry("a", "b", "Caesar", OffsetDateTime.now(), "ENCRYPTION", "SUCCESS"));

        when(encryptionRepository.getHistoryByAlgorithm("Caesar")).thenReturn(log);

        List<HistoryEntry> history = logService.getHistoryByAlgorithm("Caesar");

        assertEquals(log, history, "The history by algorithm is incorrect.");
    }

    @Test
    void getHistoryByAlgorithm_shouldThrowException_whenInvalidParameters() {
        assertThatCode(() -> logService.getHistoryByAlgorithm("abcd"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Illegal algorithm name for search.");
    }

    @Test
    void getHistoryByAlgorithmAndOperationType_shouldReturnProperList_whenValidParameters() {
        List<HistoryEntry> log = new ArrayList<>();
        log.add(new HistoryEntry("a", "b", "Caesar", OffsetDateTime.now(), "ENCRYPTION", "SUCCESS"));

        when(encryptionRepository.getHistoryByAlgorithmAndOperationType("Caesar", "ENCRYPTION")).thenReturn(log);

        List<HistoryEntry> history = logService.getHistoryByAlgorithmAndOperationType("Caesar", "ENCRYPTION");

        assertEquals(log, history, "The history by algorithm and operation type is incorrect.");
    }

    @Test
    void getHistoryByAlgorithmAndOperationType_shouldThrowException_whenInvalidParameters() {
        assertThatCode(() -> logService.getHistoryByAlgorithmAndOperationType("Caesar", "a"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Illegal algorithm name or operation type for search.");
    }

    @Test
    void getFullHistoryWithPagination_shouldReturnProperList_whenValidParameters() {
        List<HistoryEntry> log = new ArrayList<>();
        log.add(new HistoryEntry("a", "b", "Caesar", OffsetDateTime.now(), "ENCRYPTION", "SUCCESS"));

        when(encryptionRepository.getFullHistoryWithPagination(1, 1)).thenReturn(log);

        List<HistoryEntry> history = logService.getFullHistoryWithPagination(1, 1);

        assertEquals(log, history, "The history with pagination is incorrect.");
    }

    @Test
    void getFullHistoryWithPagination_shouldThrowException_whenInvalidParameters() {
        assertThatCode(() -> logService.getFullHistoryWithPagination(1, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Illegal pagination parameters for search.");
    }
}
