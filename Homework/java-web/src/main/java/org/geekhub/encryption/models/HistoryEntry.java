package org.geekhub.encryption.models;

import java.time.OffsetDateTime;

public class HistoryEntry {
    private final String originalMessage;
    private final String processedMessage;
    private final String algorithmName;
    private final OffsetDateTime date;
    private final String operationType;
    private final String status;

    public HistoryEntry(String originalMessage, String processedMessage, String algorithmName, OffsetDateTime date, String operationType, String status) {

        this.originalMessage = originalMessage;
        this.processedMessage = processedMessage;
        this.algorithmName = algorithmName;
        this.date = date;
        this.operationType = operationType;
        this.status = status;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public String getProcessedMessage() {
        return processedMessage;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public String getOperationType() {
        return operationType;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("Message '%s' was processed via the '%s' operation" +
            " and %s cipher into '%s' at %tH:%<tM:%<tS %<tY-%<tm-%<td", originalMessage, operationType, algorithmName, processedMessage, date);
    }
}
