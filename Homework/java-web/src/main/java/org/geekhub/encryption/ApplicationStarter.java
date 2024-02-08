package org.geekhub.encryption;

import org.geekhub.encryption.models.HistoryEntry;
import org.geekhub.encryption.service.EncryptionService;
import org.geekhub.encryption.service.HistoryService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.OffsetDateTime;
import java.util.List;

public class ApplicationStarter {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.geekhub.encryption.configurations");
        context.registerShutdownHook();

        EncryptionService encryptionService = context.getBean(EncryptionService.class);
        String originalMessage = "Hello";
        String encryptedMessage = encryptionService.performOperation(originalMessage);
        System.out.printf("%s%n", encryptedMessage);

        HistoryService historyService = context.getBean(HistoryService.class);

        printFetchName("History by algorithm:");
        printHistory(historyService.getHistoryByAlgorithm("Caesar"));
        printDelimiters();

        printFetchName("History in date range:");
        printHistory(historyService.getHistoryInDateRange(null, OffsetDateTime.now()));
        printDelimiters();

        printFetchName("History bt algorithm and operation type:");
        printHistory(historyService.getHistoryByAlgorithmAndOperationType("Vigenere", "ENCRYPTION"));
        printDelimiters();

        printFetchName("History with pagination:");
        printHistory(historyService.getFullHistoryWithPagination(1, 2));
        printDelimiters();

        printFetchName("History with pagination and user id:");
        printHistory(historyService.getFullHistoryWithPaginationAndUserId(1, 1, 2));
        printDelimiters();
    }

    private static void printHistory(List<HistoryEntry> historyEntryList) {
        for (HistoryEntry entry : historyEntryList) {
            System.out.println(entry.stringRepresentation());
        }
    }

    private static void printDelimiters() {
        System.out.println("\n\n\n");
    }

    private static void printFetchName(String fetchName) {
        System.out.println(fetchName);
    }
}
