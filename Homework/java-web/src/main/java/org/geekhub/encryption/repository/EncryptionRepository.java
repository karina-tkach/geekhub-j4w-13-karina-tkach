package org.geekhub.encryption.repository;

import org.geekhub.encryption.exception.FileException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class EncryptionRepository {
    private static final Path PATH_TO_LOG_FILE = Path.of("historyLog.txt");
    private final List<String> log;

    public EncryptionRepository() {
        try {
            if (!Files.exists(PATH_TO_LOG_FILE)) {
                Files.createFile(PATH_TO_LOG_FILE);
                this.log = new ArrayList<>();
            } else {
                this.log = Files.readAllLines(PATH_TO_LOG_FILE);
            }
        } catch (Exception ex) {
            throw new FileException("Error occurred while processing log file", ex);
        }
    }

    public void addToHistoryLog(String originalMessage, String algorithm, String encryptedMessage, OffsetDateTime dateTime) {
        try {
            String delimiter = "|#";
            String res = String.format("%1$s%2$s%3$s%2$s%4$s%2$s%5$s", dateTime.toString(), delimiter,
                originalMessage, algorithm, encryptedMessage);
            log.add(res);
            res = String.format("%s%n", res);
            Files.write(PATH_TO_LOG_FILE, res.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception ex) {
            throw new FileException("Error occurred while processing log file", ex);
        }
    }

    public List<String> getHistoryLog() {
        return log;
    }
}
