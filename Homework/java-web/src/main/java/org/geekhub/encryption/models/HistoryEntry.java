package org.geekhub.encryption.models;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;

public record HistoryEntry(@Nullable String originalMessage,
                           @Nullable String processedMessage,
                           @NonNull String algorithmName,
                           @NonNull OffsetDateTime date,
                           @NonNull String operationType,
                           @NonNull String status) {

    public String stringRepresentation() {
        return String.format("Message '%s' was processed via the '%s' operation" +
            " and %s cipher into '%s' at %tH:%<tM:%<tS %<tY-%<tm-%<td", originalMessage, operationType, algorithmName, processedMessage, date);
    }
}
