package org.geekhub.encryption.repository;

import org.geekhub.encryption.models.HistoryEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
@SuppressWarnings("java:S1192")
public class EncryptionRepositoryInMemory implements EncryptionRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final int activeUserId;

    public EncryptionRepositoryInMemory(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                        @Value("${active.user.id}") int activeUserId) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.activeUserId = activeUserId;
    }

    @Override
    public void saveEncoding(HistoryEntry entry) {
        String query = """
            INSERT INTO history (original_message, processed_message, algorithm, date, operation_type, status, user_id)
            VALUES (:original_message, :processed_message, :algorithm, :date, :operation_type, :status, :user_id)
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("original_message", entry.originalMessage())
            .addValue("processed_message", entry.processedMessage())
            .addValue("algorithm", entry.algorithmName())
            .addValue("date", Timestamp.from(entry.date().toInstant()))
            .addValue("operation_type", entry.operationType())
            .addValue("status", entry.status())
            .addValue("user_id", activeUserId);

        namedParameterJdbcTemplate.update(query, parameters);
    }

    @Override
    public List<HistoryEntry> getHistoryByAlgorithm(String algorithm) {
        String query = """
                SELECT * FROM history
                WHERE algorithm = :algorithm
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("algorithm", algorithm);

        return namedParameterJdbcTemplate.query(query, parameters, HistoryEntryMapper::mapRow);
    }

    @Override
    public List<HistoryEntry> getHistoryInDateRange(OffsetDateTime from, OffsetDateTime to) {
        Timestamp fromTime = (from == null) ? null : Timestamp.from(from.toInstant());
        Timestamp toTime = (to == null) ? null : Timestamp.from(to.toInstant());

        String query = """
            SELECT * FROM history
            WHERE (date BETWEEN COALESCE(:from,date) AND COALESCE(:to,date))
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("from", fromTime)
            .addValue("to", toTime);

        return namedParameterJdbcTemplate.query(query, parameters, HistoryEntryMapper::mapRow);
    }

    @Override
    public List<HistoryEntry> getHistoryByAlgorithmAndOperationType(String algorithm, String operationType) {
        String query = """
                SELECT * FROM history
                WHERE algorithm = :algorithm AND operation_type = :operationType
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("algorithm", algorithm)
            .addValue("operationType", operationType);

        return namedParameterJdbcTemplate.query(query, parameters, HistoryEntryMapper::mapRow);
    }

    @Override
    public List<HistoryEntry> getFullHistoryWithPagination(int pageNumber, int limit) {
        String query = """
            SELECT * FROM history
            LIMIT :limit
            OFFSET :limit * :pageNumber
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("limit", limit)
            .addValue("pageNumber", pageNumber - 1);

        return namedParameterJdbcTemplate.query(query, parameters, HistoryEntryMapper::mapRow);
    }

    @Override
    public List<HistoryEntry> getFullHistoryWithPaginationAndUserId(int userId, int pageNumber, int limit) {
        String query = """
            SELECT * FROM history
            WHERE user_id = :userId
            LIMIT :limit
            OFFSET :limit * :pageNumber
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("limit", limit)
            .addValue("pageNumber", pageNumber - 1);

        return namedParameterJdbcTemplate.query(query, parameters, HistoryEntryMapper::mapRow);
    }
}
