package org.geekhub.encryption.service;

import org.geekhub.encryption.repository.EncryptionRepositoryInMemory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LogService {
    private final EncryptionRepositoryInMemory encryptionRepository;

    public LogService(EncryptionRepositoryInMemory encryptionRepository) {
        this.encryptionRepository = encryptionRepository;
    }

    /*public List<String> getFullHistory() {
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

    public List<String> getAlgorithmUsageCount() {
        List<String> log = encryptionRepository.getHistoryLog();

        Map<String, Integer> algorithmUsageCount = log.stream()
            .map(encryption -> encryption.split("\\|#")[2])
            .collect(Collectors.toMap(
                algorithm -> algorithm,
                algorithm -> 1,
                Integer::sum
            ));

        return algorithmUsageCount.entrySet().stream()
            .map(entry -> entry.getKey() + " was used " + entry.getValue() + " times")
            .toList();
    }

    public String getUniqueEncryptions(String originalMessage, String cipherName) {
        List<String> log = encryptionRepository.getHistoryLog();

        long encryptionCount = log.stream()
            .map(encryption -> encryption.split("\\|#"))
            .filter(encryptionData -> isEqual(encryptionData, originalMessage, cipherName))
            .count();

        return String.format("Message '%s' was encrypted via %s %d times", originalMessage, cipherName, encryptionCount);
    }

    private boolean isEqual(String[] encryptionData, String originalMessage, String cipherName) {
        String message = encryptionData[1];
        String algorithm = encryptionData[2];
        return message.equals(originalMessage) && algorithm.equals(cipherName);
    }*/
}
