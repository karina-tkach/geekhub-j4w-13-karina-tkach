package org.geekhub.encryption.service;

import org.geekhub.encryption.repository.EncryptionRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogService {
    private final EncryptionRepository encryptionRepository;

    public LogService(EncryptionRepository encryptionRepository) {
        this.encryptionRepository = encryptionRepository;
    }

    public List<String> getFullHistory() {
        List<String> log = encryptionRepository.getHistoryLog();

        return log.stream()
            .map(this::getFormatedString)
            .toList();
    }

    public List<String> getHistoryByDate(String dateString) {
        List<String> log = encryptionRepository.getHistoryLog();

        return log.stream()
            .filter(encryption -> encryption.startsWith(dateString))
            .map(this::getFormatedString)
            .toList();
    }

    private String getFormatedString(String encryption) {
        String[] encryptionData = encryption.split("\\|#");
        return String.format("%s - Message '%s' was encrypted via %s into '%s'",
            encryptionData[0], encryptionData[1], encryptionData[2], encryptionData[3]);
    }

    public Map<String, Integer> getAlgorithmUsageCount() {
        List<String> log = encryptionRepository.getHistoryLog();

        return log.stream()
            .map(encryption -> encryption.split("\\|#")[2])
            .collect(Collectors.toMap(
                algorithm -> algorithm,
                algorithm -> 1,
                Integer::sum
            ));
    }

    public long getUniqueEncryptions(String originalMessage, String cipherName) {
        List<String> log = encryptionRepository.getHistoryLog();

        return log.stream()
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
