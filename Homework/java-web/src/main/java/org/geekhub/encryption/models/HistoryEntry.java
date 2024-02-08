package org.geekhub.encryption.models;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;

public record HistoryEntry(@Nullable Integer recordId,
                           int userId,
                           @Nullable String originalMessage,
                           @Nullable String processedMessage,
                           @Nullable String algorithmName,
                           @NonNull OffsetDateTime date,
                           @Nullable String operationType,
                           @NonNull String status) {
    public HistoryEntry(int userId,
                         @Nullable String originalMessage,
                         @Nullable String processedMessage,
                         @Nullable String algorithmName,
                         @NonNull OffsetDateTime date,
                         @Nullable String operationType,
                         @NonNull String status) {
        this(null, userId, originalMessage, processedMessage, algorithmName, date, operationType, status);
    }

    public String stringRepresentation() {
        return String.format("Message '%s' was processed via the '%s' operation by user '%d'" +
            " and %s cipher into '%s' at %tH:%<tM:%<tS %<tY-%<tm-%<td. Record id: %s", originalMessage, operationType,
            userId, algorithmName, processedMessage, date, recordId);
    }
}
