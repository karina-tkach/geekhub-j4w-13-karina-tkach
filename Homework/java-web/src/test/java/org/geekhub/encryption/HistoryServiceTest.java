package org.geekhub.encryption;

import org.geekhub.encryption.history.HistoryEntry;
import org.geekhub.encryption.history.EncryptionRepository;
import org.geekhub.encryption.history.HistoryService;
import org.geekhub.encryption.validators.HistoryParametersValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {
    @Mock
    private EncryptionRepository encryptionRepository;
    private final HistoryParametersValidator historyParametersValidator = new HistoryParametersValidator();
    private HistoryService historyService;

    @BeforeEach
    public void setUp() {
        historyService = new HistoryService(encryptionRepository, historyParametersValidator);
    }

    @Test
    void getHistoryByAlgorithm_shouldReturnProperList_whenValidParameters() {
        List<HistoryEntry> log = new ArrayList<>();
        log.add(new HistoryEntry(1, 1, "a", "b", "Caesar",
            OffsetDateTime.now(), "ENCRYPTION", "SUCCESS"));

        when(encryptionRepository.getHistoryByAlgorithm("Caesar")).thenReturn(log);

        List<HistoryEntry> history = historyService.getHistoryByAlgorithm("Caesar");

        assertEquals(log, history, "The history by algorithm is incorrect.");
    }

    @Test
    void getHistoryByAlgorithm_shouldThrowException_whenInvalidParameters() {
        assertThatCode(() -> historyService.getHistoryByAlgorithm("abcd"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Illegal algorithm name for search.");
    }

    @Test
    void getHistoryRecordById_shouldReturnProperList_whenValidParameters() {
        HistoryEntry log = new HistoryEntry(1, 1, "a", "b",
            "Caesar", OffsetDateTime.now(), "ENCRYPTION", "SUCCESS");

        when(encryptionRepository.getHistoryRecordById(1)).thenReturn(Optional.of(log));

        HistoryEntry record = historyService.getHistoryRecordById(1);

        assertEquals(log, record, "The history by id is incorrect.");
    }

    @Test
    void getFullHistory_shouldReturnProperList_whenValidParameters() {
        List<HistoryEntry> log = new ArrayList<>();
        log.add(new HistoryEntry(1, 1, "a", "b", "Caesar",
            OffsetDateTime.now(), "ENCRYPTION", "SUCCESS"));
        log.add(new HistoryEntry(2, 1, "b", "c", "Vigenere",
            OffsetDateTime.now(), "DECRYPTION", "SUCCESS"));

        when(encryptionRepository.getFullHistory()).thenReturn(log);

        List<HistoryEntry> history = historyService.getFullHistory();

        assertEquals(log, history, "The full history is incorrect.");
    }

    @Test
    void getHistoryRecordsByUserId_shouldReturnProperList_whenValidParameters() {
        List<HistoryEntry> log = new ArrayList<>();
        log.add(new HistoryEntry(1, 1, "a", "b",
            "Caesar", OffsetDateTime.now(), "ENCRYPTION", "SUCCESS"));

        when(encryptionRepository.getHistoryRecordsByUserId(1)).thenReturn(log);

        List<HistoryEntry> record = historyService.getHistoryRecordsByUserId(1);

        assertEquals(log, record, "The history by user id is incorrect.");
    }

    @Test
    void getHistoryByAlgorithmAndOperationType_shouldReturnProperList_whenValidParameters() {
        List<HistoryEntry> log = new ArrayList<>();
        log.add(new HistoryEntry(1, 1, "a", "b", "Caesar", OffsetDateTime.now(), "ENCRYPTION", "SUCCESS"));

        when(encryptionRepository.getHistoryByAlgorithmAndOperationType("Caesar", "ENCRYPTION")).thenReturn(log);

        List<HistoryEntry> history = historyService.getHistoryByAlgorithmAndOperationType("Caesar", "ENCRYPTION");

        assertEquals(log, history, "The history by algorithm and operation type is incorrect.");
    }

    @Test
    void getHistoryByAlgorithmAndOperationType_shouldThrowException_whenInvalidParameters() {
        assertThatCode(() -> historyService.getHistoryByAlgorithmAndOperationType("Caesar", "a"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Illegal algorithm name or operation type for search.");
    }

    @Test
    void getHistoryInDateRange_shouldReturnProperList_whenValidParameters() {
        OffsetDateTime dateTime = OffsetDateTime.of(2024, 2, 8, 0, 0, 0, 0, ZoneOffset.UTC);
        List<HistoryEntry> log = new ArrayList<>();
        log.add(new HistoryEntry(1, 1, "a", "b", "Caesar", dateTime, "ENCRYPTION", "SUCCESS"));

        when(encryptionRepository.getHistoryInDateRange(null, dateTime)).thenReturn(log);

        List<HistoryEntry> history = historyService.getHistoryInDateRange(null, dateTime);

        assertEquals(log, history, "The history in date range is incorrect.");
    }

    @Test
    void getHistoryInDateRange_shouldThrowException_whenInvalidParameters() {
        assertThatCode(() -> historyService.getHistoryInDateRange(OffsetDateTime.now().plusMonths(2),
            OffsetDateTime.now()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("From date must be before to date.");
    }

    @Test
    void getFullHistoryWithPagination_shouldReturnProperList_whenValidParameters() {
        List<HistoryEntry> log = new ArrayList<>();
        log.add(new HistoryEntry(1, 1, "a", "b", "Caesar", OffsetDateTime.now(), "ENCRYPTION", "SUCCESS"));

        when(encryptionRepository.getFullHistoryWithPagination(1, 1)).thenReturn(log);

        List<HistoryEntry> history = historyService.getFullHistoryWithPagination(1, 1);

        assertEquals(log, history, "The history with pagination is incorrect.");
    }

    @Test
    void getFullHistoryWithPagination_shouldThrowException_whenInvalidParameters() {
        assertThatCode(() -> historyService.getFullHistoryWithPagination(1, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Illegal pagination parameters for search.");
    }
}
