package org.geekhub.encryption.validators;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class HistoryParametersValidator {
    private final List<String> algorithms;
    private final List<String> operationTypes;

    public HistoryParametersValidator() {
        algorithms = List.of("CAESAR", "VIGENERE", "A1Z26", "ATBASH");
        operationTypes = List.of("ENCRYPTION", "DECRYPTION");
    }

    public boolean validateAlgorithmName(String algorithmName) {
        return algorithms.contains(algorithmName);
    }

    public boolean validateOperationType(String operationType) {
        return operationTypes.contains(operationType);
    }

    public boolean validateDate(OffsetDateTime from, OffsetDateTime to) {
        return Objects.isNull(from) || Objects.isNull(to) || from.isBefore(to);
    }

    public boolean validatePaginationParameters(int pageNumber, int limit) {
        return pageNumber > 0 && limit > 0;
    }
}
