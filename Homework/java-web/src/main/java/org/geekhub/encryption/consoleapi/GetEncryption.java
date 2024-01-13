package org.geekhub.encryption.consoleapi;

import org.geekhub.encryption.service.LogService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class GetEncryption {
    public static final String ERROR_MESSAGE = "No logs are available";
    private final Scanner scanner;
    private final LogService logService;

    public GetEncryption(Scanner scanner, LogService logService) {
        this.scanner = scanner;
        this.logService = logService;
    }

    public void displayHistory() {
        List<String> log = logService.getHistory();

        if (log.isEmpty()) {
            System.out.println(ERROR_MESSAGE);
        } else {
            for (String encryption : log) {
                String[] encryptionData = encryption.split("\\|#");
                String res = String.format("%s - Message '%s' was encrypted via %s into '%s'",
                    encryptionData[0], encryptionData[1], encryptionData[2], encryptionData[3]);
                System.out.println(res);
            }
        }
    }

    public void displayHistoryByDate() {
        System.out.println("Enter the date (yyyy-MM-dd) to display the history log:");
        String dateString = scanner.nextLine();

        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            List<String> log = logService.getHistory();
            boolean found = false;

            if (!log.isEmpty()) {
                for (String encryption : log) {
                    String[] encryptionData = encryption.split("\\|#");
                    String date = encryptionData[0].substring(0, 10);

                    if (date.equals(dateString)) {
                        found = true;
                        String res = String.format("%s - Message '%s' was encrypted via %s into '%s'",
                            encryptionData[0], encryptionData[1], encryptionData[2], encryptionData[3]);
                        System.out.println(res);
                    }
                }
            }
            if (!found) {
                System.out.println("There's no log for such date.");
            }
        } catch (ParseException ex) {
            System.out.println("Invalid date format.");
        }
    }

    public void displayAlgorithmUsageCount() {
        List<String> log = logService.getHistory();

        if (log.isEmpty()) {
            System.out.println(ERROR_MESSAGE);
        } else {
            Map<String, Integer> algorithmUsageCount = new HashMap<>();

            for (String encryption : log) {
                String[] encryptionData = encryption.split("\\|#");
                String algorithm = encryptionData[2];
                algorithmUsageCount.put(algorithm, algorithmUsageCount.getOrDefault(algorithm, 0) + 1);
            }

            for (Map.Entry<String, Integer> entry : algorithmUsageCount.entrySet()) {
                System.out.println(entry.getKey() + " was used " + entry.getValue() + " times");
            }
        }
    }

    public void displayUniqueEncryptions() {
        List<String> log = logService.getHistory();

        if (log.isEmpty()) {
            System.out.println(ERROR_MESSAGE);
        } else {
            Map<String, Set<String>> uniqueEncryptions = new HashMap<>();

            for (String encryption : log) {
                String[] encryptionData = encryption.split("\\|#");

                String originalMessage = encryptionData[1];
                String algorithm = encryptionData[2];

                Set<String> algorithms = uniqueEncryptions.getOrDefault(originalMessage, new HashSet<>());
                algorithms.add(algorithm);
                uniqueEncryptions.put(originalMessage, algorithms);
            }

            for (Map.Entry<String, Set<String>> entry : uniqueEncryptions.entrySet()) {
                System.out.println("Message '" + entry.getKey() + "' was encrypted via " + entry.getValue() + " " + entry.getValue().size() + " times");
            }
        }
    }
}
