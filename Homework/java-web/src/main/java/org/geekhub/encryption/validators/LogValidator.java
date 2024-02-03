package org.geekhub.encryption.validators;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogValidator {
    private final List<String> algorithms;
    private final List<String> operationTypes;

    public LogValidator() {
        algorithms = List.of("Caesar", "Vigenere", "A1Z26", "Atbash");
        operationTypes = List.of("ENCRYPTION", "DECRYPTION");
    }

    public boolean validateAlgorithmName(String algorithmName) {
        return algorithms.contains(algorithmName);
    }

    public boolean validateOperationType(String operationType) {
        return operationTypes.contains(operationType);
    }

    public boolean validatePaginationParameters(int pageNumber, int limit) {
        return pageNumber > 0 && limit > 0;
    }
}
