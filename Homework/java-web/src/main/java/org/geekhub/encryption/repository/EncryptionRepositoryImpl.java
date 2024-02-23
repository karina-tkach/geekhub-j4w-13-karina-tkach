package org.geekhub.encryption.repository;

import org.geekhub.encryption.models.HistoryEntry;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings({"java:S1192", "java:S2259"})
public class EncryptionRepositoryImpl implements EncryptionRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EncryptionRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
            .addValue("user_id", entry.userId());

        namedParameterJdbcTemplate.update(query, parameters);
    }

    @Override
    public void deleteHistoryRecordById(int id) {
        String query = "DELETE FROM history WHERE id = :id";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id);

        namedParameterJdbcTemplate.update(query, params);
    }

    @Override
    public Optional<HistoryEntry> getHistoryRecordById(int id) {
        String query = "SELECT * FROM history WHERE id = :id";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id);

        HistoryEntry historyRecord = namedParameterJdbcTemplate.queryForObject(query, params, HistoryEntryMapper::mapToPojo);
        return Optional.ofNullable(historyRecord);
    }

    @Override
    public List<HistoryEntry> getFullHistory() {
        String query = "SELECT * FROM history ORDER BY id";

        return namedParameterJdbcTemplate.query(query, HistoryEntryMapper::mapToPojo);
    }

    @Override
    public List<HistoryEntry> getHistoryRecordsByUserId(int userId) {
        String query = "SELECT * FROM history WHERE user_id = :userId ORDER BY id";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("userId", userId);

        return namedParameterJdbcTemplate.query(query, params, HistoryEntryMapper::mapToPojo);
    }

    @Override
    public List<HistoryEntry> getHistoryByAlgorithm(String algorithm) {
        String query = """
                SELECT * FROM history
                WHERE algorithm = :algorithm
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("algorithm", algorithm);

        return namedParameterJdbcTemplate.query(query, parameters, HistoryEntryMapper::mapToPojo);
    }

    @Override
    public List<HistoryEntry> getHistoryInDateRange(@Nullable OffsetDateTime from, @Nullable OffsetDateTime to) {
        Timestamp fromTime = (from == null) ? null : Timestamp.from(from.toInstant());
        Timestamp toTime = (to == null) ? null : Timestamp.from(to.toInstant());

        String query = """
            SELECT * FROM history
            WHERE (date BETWEEN COALESCE(:from,date) AND COALESCE(:to,date))
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("from", fromTime)
            .addValue("to", toTime);

        return namedParameterJdbcTemplate.query(query, parameters, HistoryEntryMapper::mapToPojo);
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

        return namedParameterJdbcTemplate.query(query, parameters, HistoryEntryMapper::mapToPojo);
    }

    @Override
    public List<HistoryEntry> getFullHistoryWithPagination(int pageNumber, int pageSize) {
        String query = """
            SELECT * FROM history
            LIMIT :limit
            OFFSET :offset
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("limit", pageSize)
            .addValue("offset", getOffset(pageNumber, pageSize));

        return namedParameterJdbcTemplate.query(query, parameters, HistoryEntryMapper::mapToPojo);
    }

    @Override
    public List<HistoryEntry> getFullHistoryWithPaginationAndUserId(int userId, int pageNumber, int pageSize) {
        String query = """
            SELECT * FROM history
            WHERE user_id = :userId
            LIMIT :limit
            OFFSET :offset
            """;

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("limit", pageSize)
            .addValue("offset", getOffset(pageNumber, pageSize));

        return namedParameterJdbcTemplate.query(query, parameters, HistoryEntryMapper::mapToPojo);
    }

    @Override
    public int getHistoryRowsCount() {
        String query = "SELECT COUNT(*) FROM history";
        return namedParameterJdbcTemplate.queryForObject(query, new MapSqlParameterSource(),
            Integer.class);
    }

    private static int getOffset(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }
}
