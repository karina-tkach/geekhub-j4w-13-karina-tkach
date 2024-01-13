package org.geekhub.encryption.consoleapi;

import org.geekhub.encryption.service.LogService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GetEncryption {
    public static final String ERROR_MESSAGE = "No logs are available";
    private static final String OPTIONS = """
        1. Caesar cipher
        2. Atbash cipher
        3. A1Z26 cipher
        4. ROT13 cipher
        5. Vigenere cipher""";
    private final Scanner scanner;
    private final LogService logService;

    public GetEncryption(Scanner scanner, LogService logService) {
        this.scanner = scanner;
        this.logService = logService;
    }

    public void displayHistory() {
        List<String> log = logService.getFullHistory();

        if (log.isEmpty()) {
            System.out.println(ERROR_MESSAGE);
        } else {
            for (String encryptionData : log) {
                System.out.println(encryptionData);
            }
        }
    }

    public void displayHistoryByDate() {
        System.out.println("Enter the date (yyyy-MM-dd) to display the history log:");
        String dateString = scanner.nextLine();

        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            List<String> log = logService.getHistoryByDate(dateString);

            if (!log.isEmpty()) {
                for (String encryptionData : log) {
                    System.out.println(encryptionData);
                }
            } else {
                System.out.println("There's no log for such date.");
            }
        } catch (ParseException ex) {
            System.out.println("Invalid date format.");
        }
    }

    public void displayAlgorithmUsageCount() {
        Map<String, Integer> algorithmUsageCount = logService.getAlgorithmUsageCount();

        if (algorithmUsageCount.isEmpty()) {
            System.out.println(ERROR_MESSAGE);
        } else {
            for (Map.Entry<String, Integer> entry : algorithmUsageCount.entrySet()) {
                System.out.println(entry.getKey() + " was used " + entry.getValue() + " times");
            }
        }
    }

    public void displayUniqueEncryptions() {
        System.out.println("Enter original message:");
        String originalMessage = scanner.nextLine();
        System.out.println("Choose algorithm name:");
        String cipherName = getCipherName();

        int encryptionCount = logService.getUniqueEncryptions(originalMessage, cipherName);
        if (encryptionCount == 0) {
            System.out.println(ERROR_MESSAGE);
        } else {
            System.out.printf("Message '%s' was encrypted via %s %d times%n", originalMessage, cipherName, encryptionCount);
        }
    }

    private String getCipherName() {
        do {
            System.out.println(OPTIONS);
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> {
                        return "Caesar";
                    }
                    case 2 -> {
                        return "Atbash";
                    }
                    case 3 -> {
                        return "A1Z26";
                    }
                    case 4 -> {
                        return "ROT13";
                    }
                    case 5 -> {
                        return "Vigenere";
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (true);
    }
}
