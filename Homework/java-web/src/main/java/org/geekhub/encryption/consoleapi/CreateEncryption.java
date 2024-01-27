package org.geekhub.encryption.consoleapi;

import org.geekhub.encryption.ciphers.A1Z26Cipher;
import org.geekhub.encryption.ciphers.AtbashCipher;
import org.geekhub.encryption.ciphers.CaesarCipher;
import org.geekhub.encryption.ciphers.Cipher;
import org.geekhub.encryption.ciphers.VigenereCipher;
import org.geekhub.encryption.service.EncryptionService;

import java.util.Scanner;

public class CreateEncryption {
    private static final String SUBMENU_OPTIONS = """
        Submenu:
        1. Encrypt a message using Caesar cipher
        2. Encrypt a message using Atbash cipher
        3. Encrypt a message using A1Z26 cipher
        4. Encrypt a message using Vigenere cipher""";
    private final Scanner scanner;
    private final EncryptionService encryptionService;

    public CreateEncryption(Scanner scanner, EncryptionService encryptionService) {
        this.scanner = scanner;
        this.encryptionService = encryptionService;
    }

    public void encryptMessage() {
        Cipher cipher = getInputCipher();
        String message = getInputMessage();
        encryptionService.setCipher(cipher);

        try {
            String encryptedMessage = encryptionService.encryptMessage(message);
            System.out.println("Encrypted message: " + encryptedMessage);
        } catch (IllegalArgumentException ex) {
            System.out.println("Incorrect input message.");
        }
    }

    @SuppressWarnings("EnhancedSwitchMigration")
    private Cipher getInputCipher() {
        while (true) {
            System.out.println(SUBMENU_OPTIONS);
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1: return new CaesarCipher();
                    case 2: return new AtbashCipher();
                    case 3: return new A1Z26Cipher();
                    case 4: return new VigenereCipher();
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private String getInputMessage() {
        System.out.println("Enter the message to encrypt:");
        return scanner.nextLine();
    }
}
