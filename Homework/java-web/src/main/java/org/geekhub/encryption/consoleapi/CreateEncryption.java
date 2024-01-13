package org.geekhub.encryption.consoleapi;

import org.geekhub.encryption.service.EncryptionService;

import java.util.Scanner;

public class CreateEncryption {
    private static final String SUBMENU_OPTIONS = """
        Submenu:
        1. Encrypt a message using Caesar cipher
        2. Encrypt a message using Atbash cipher
        3. Encrypt a message using A1Z26 cipher
        4. Encrypt a message using ROT13 cipher
        5. Encrypt a message using Vigenere cipher""";
    private final Scanner scanner;
    private final EncryptionService encryptionService;

    public CreateEncryption(Scanner scanner, EncryptionService encryptionService) {
        this.scanner = scanner;
        this.encryptionService = encryptionService;
    }

    public void encryptMessage() {
        String cipherName = getInputCipherName();
        String message = getInputMessage();
        String key = "";
        if (cipherName.equals("Vigenere")) {
            key = getKeyForVigenereCipher();
        }
        try {
            String encryptedMessage = encryptionService.encryptMessage(cipherName, message, key);
            System.out.println("Encrypted message: " + encryptedMessage);
        } catch (IllegalArgumentException ex) {
            System.out.println("Incorrect input message or key.");
        }
    }

    private String getInputCipherName() {
        do {
            System.out.println(SUBMENU_OPTIONS);
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

    private String getInputMessage() {
        System.out.println("Enter the message to encrypt:");
        return scanner.nextLine();
    }

    private String getKeyForVigenereCipher() {
        System.out.println("Enter the key to encrypt a message with Vigenere cipher:");
        return scanner.nextLine();
    }
}
