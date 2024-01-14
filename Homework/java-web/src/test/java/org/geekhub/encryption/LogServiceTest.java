package org.geekhub.encryption;

import org.geekhub.encryption.repository.EncryptionRepository;
import org.geekhub.encryption.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {
    @Mock
    private EncryptionRepository encryptionRepository;

    private LogService logService;

    @BeforeEach
    public void setUp() {
        logService = new LogService(encryptionRepository);
    }

    @Test
    void getFullHistory_shouldReturnProperlyFormattedMessages_whenAreAvailable() {
        List<String> log = new ArrayList<>();
        log.add("2024-01-12|#message1|#algorithm1|#encrypted1");
        log.add("2024-01-13|#message2|#algorithm2|#encrypted2");

        when(encryptionRepository.getHistoryLog()).thenReturn(log);

        List<String> fullHistory = logService.getFullHistory();

        assertEquals(2, fullHistory.size(), "The size of the full history is incorrect.");

        assertEquals("2024-01-12 - Message 'message1' was encrypted via algorithm1 into 'encrypted1'", fullHistory.get(0),
            "The first entry in the full history is incorrect.");

        assertEquals("2024-01-13 - Message 'message2' was encrypted via algorithm2 into 'encrypted2'", fullHistory.get(1),
            "The second entry in the full history is incorrect.");
    }

    @Test
    void getHistoryByDate_shouldReturnProperlyFormattedMessagesWithSpecifiedDate_whenAreAvailable() {
        List<String> log = new ArrayList<>();
        log.add("2024-01-01|#message1|#algorithm1|#encrypted1");
        log.add("2024-01-02|#message2|#algorithm2|#encrypted2");
        log.add("2024-01-02|#message3|#algorithm3|#encrypted3");

        when(encryptionRepository.getHistoryLog()).thenReturn(log);

        List<String> historyByDate = logService.getHistoryByDate("2024-01-02");

        assertEquals(2, historyByDate.size(), "The size of the history by date is incorrect.");

        assertEquals("2024-01-02 - Message 'message2' was encrypted via algorithm2 into 'encrypted2'", historyByDate.get(0),
            "The first entry in the history by date is incorrect.");

        assertEquals("2024-01-02 - Message 'message3' was encrypted via algorithm3 into 'encrypted3'", historyByDate.get(1),
            "The second entry in the history by date is incorrect.");
    }

    @Test
    void getAlgorithmUsageCount_shouldReturnCountOfAlgorithmUsage_always() {
        List<String> log = new ArrayList<>();
        log.add("2024-01-01|#message1|#algorithm1|#encrypted1");
        log.add("2024-01-02|#message2|#algorithm2|#encrypted2");
        log.add("2024-01-02|#message3|#algorithm3|#encrypted3");
        log.add("2024-01-03|#message4|#algorithm1|#encrypted4");
        log.add("2024-01-03|#message5|#algorithm3|#encrypted5");
        log.add("2024-01-03|#message6|#algorithm1|#encrypted6");

        when(encryptionRepository.getHistoryLog()).thenReturn(log);

        Map<String, Integer> algorithmUsageCount = logService.getAlgorithmUsageCount();

        assertEquals(3, algorithmUsageCount.size(), "The size of the algorithm usage count is incorrect.");

        assertEquals(3, algorithmUsageCount.get("algorithm1"), "The count for algorithm1 is incorrect.");
        assertEquals(1, algorithmUsageCount.get("algorithm2"), "The count for algorithm2 is incorrect.");
        assertEquals(2, algorithmUsageCount.get("algorithm3"), "The count for algorithm3 is incorrect.");
    }

    @Test
    void getUniqueEncryptions_shouldReturnStringByOriginalMessageAndCipher_whenAvailable() {
        List<String> log = new ArrayList<>();
        log.add("2024-01-01|#message1|#algorithm1|#encrypted1");
        log.add("2024-01-02|#message2|#algorithm2|#encrypted2");
        log.add("2024-01-02|#message2|#algorithm2|#encrypted3");
        log.add("2024-01-03|#message3|#algorithm1|#encrypted4");

        when(encryptionRepository.getHistoryLog()).thenReturn(log);

        long uniqueEncryptions1 = logService.getUniqueEncryptions("message1", "algorithm1");
        long uniqueEncryptions2 = logService.getUniqueEncryptions("message2", "algorithm2");
        long uniqueEncryptions3 = logService.getUniqueEncryptions("message2", "algorithm3");
        long uniqueEncryptions4 = logService.getUniqueEncryptions("message3", "algorithm1");
        long uniqueEncryptions5 = logService.getUniqueEncryptions("message4", "algorithm3");

        assertEquals(1, uniqueEncryptions1, "The count of unique encryptions for message1 and algorithm1 is incorrect.");
        assertEquals(2, uniqueEncryptions2, "The count of unique encryptions for message2 and algorithm2 is incorrect.");
        assertEquals(0, uniqueEncryptions3, "The count of unique encryptions for message2 and algorithm1 is incorrect.");
        assertEquals(1, uniqueEncryptions4, "The count of unique encryptions for message3 and algorithm1 is incorrect.");
        assertEquals(0, uniqueEncryptions5, "The count of unique encryptions for message4 and algorithm3 is incorrect.");
    }
}
