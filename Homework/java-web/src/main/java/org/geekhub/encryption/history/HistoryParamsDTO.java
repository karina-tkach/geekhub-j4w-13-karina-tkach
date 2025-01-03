package org.geekhub.encryption.history;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class HistoryParamsDTO {
    private String algorithm;
    private String operationType;
    private int userId;
    private int recordId;
    private OffsetDateTime dateFrom;
    private OffsetDateTime dateTo;

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public OffsetDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        if (!dateFrom.isEmpty()) {
            this.dateFrom = LocalDateTime.parse(dateFrom, DateTimeFormatter.ISO_DATE_TIME).minusHours(2).atOffset(ZoneOffset.UTC);
        } else {
            this.dateFrom = null;
        }
    }

    public OffsetDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        if (!dateTo.isEmpty()) {
            this.dateTo = LocalDateTime.parse(dateTo, DateTimeFormatter.ISO_DATE_TIME).minusHours(2).atOffset(ZoneOffset.UTC);
        } else {
            this.dateTo = null;
        }
    }
}
