package org.geekhub.encryption.service;

import org.geekhub.encryption.repository.EncryptionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogService {
    private final EncryptionRepository encryptionRepository;

    public LogService(EncryptionRepository encryptionRepository) {
        this.encryptionRepository = encryptionRepository;
    }

    public List<String> getFullHistory() {
        List<String> fullHistory = new ArrayList<>();
        List<String> log = encryptionRepository.getHistoryLog();

        for (String encryption : log) {
            String[] encryptionData = encryption.split("\\|#");
            String res = String.format("%s - Message '%s' was encrypted via %s into '%s'",
                encryptionData[0], encryptionData[1], encryptionData[2], encryptionData[3]);
            fullHistory.add(res);
        }
        return fullHistory;
    }

    public List<String> getHistoryByDate(String dateString) {
        List<String> historyByDate = new ArrayList<>();
        List<String> log = encryptionRepository.getHistoryLog();

        for (String encryption : log) {
            String[] encryptionData = encryption.split("\\|#");
            String date = encryptionData[0].substring(0, 10);

            if (date.equals(dateString)) {
                String res = String.format("%s - Message '%s' was encrypted via %s into '%s'",
                    encryptionData[0], encryptionData[1], encryptionData[2], encryptionData[3]);
                historyByDate.add(res);
            }
        }
        return historyByDate;
    }

    public Map<String, Integer> getAlgorithmUsageCount() {
        List<String> log = encryptionRepository.getHistoryLog();
        Map<String, Integer> algorithmUsageCount = new HashMap<>();

        for (String encryption : log) {
            String[] encryptionData = encryption.split("\\|#");
            String algorithm = encryptionData[2];
            algorithmUsageCount.put(algorithm, algorithmUsageCount.getOrDefault(algorithm, 0) + 1);
        }

        return algorithmUsageCount;
    }

    public int getUniqueEncryptions(String originalMessage, String cipherName) {
        List<String> log = encryptionRepository.getHistoryLog();

        return (int) log.stream()
            .map(encryption -> encryption.split("\\|#"))
            .filter(encryptionData -> isEquals(encryptionData, originalMessage, cipherName))
            .count();
    }

    private boolean isEquals(String[] encryptionData, String originalMessage, String cipherName) {
        String message = encryptionData[1];
        String algorithm = encryptionData[2];
        return message.equals(originalMessage) && algorithm.equals(cipherName);
    }
}
