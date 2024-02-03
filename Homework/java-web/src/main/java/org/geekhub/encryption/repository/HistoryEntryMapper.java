package org.geekhub.encryption.repository;

import org.geekhub.encryption.models.HistoryEntry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

class HistoryEntryMapper {
    private HistoryEntryMapper() {

    }
    @SuppressWarnings("java:S1172")
    static HistoryEntry mapRow(ResultSet rs, int ignoredRowNum) throws SQLException {
        String originalMessage = rs.getString("original_message");
        String processedMessage = rs.getString("processed_message");
        String algorithmName = rs.getString("algorithm");
        OffsetDateTime date = rs.getTimestamp("date").toLocalDateTime().atOffset(ZoneOffset.ofHours(2));
        String operationType = rs.getString("operation_type");

        return new HistoryEntry(originalMessage, processedMessage, algorithmName, date, operationType);
    }
}
