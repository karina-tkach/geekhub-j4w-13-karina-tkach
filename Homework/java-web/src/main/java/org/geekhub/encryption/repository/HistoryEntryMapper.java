package org.geekhub.encryption.repository;

import org.geekhub.encryption.models.HistoryEntry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;

class HistoryEntryMapper {
    private HistoryEntryMapper() {

    }

    @SuppressWarnings("java:S1172")
    static HistoryEntry mapToPojo(ResultSet rs, int ignoredRowNum) throws SQLException {
        return new HistoryEntry(rs.getInt("id"),
        rs.getInt("user_id"),
        rs.getString("original_message"),
        rs.getString("processed_message"),
        rs.getString("algorithm"),
        rs.getTimestamp("date").toLocalDateTime().atOffset(ZoneOffset.ofHours(2)),
        rs.getString("operation_type"),
        rs.getString("status"));
    }
}
