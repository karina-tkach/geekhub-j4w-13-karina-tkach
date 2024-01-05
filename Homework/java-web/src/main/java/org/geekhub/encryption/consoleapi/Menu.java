package org.geekhub.encryption.consoleapi;

import java.util.Scanner;

public class Menu {
    private static final String MENU_OPTIONS = """
        Menu:
        1. Encrypt a message
        2. Display history of all encrypted messages
        3. Display history by date
        4. Display algorithms' usage count
        5. Display unique encryptions
        0. Exit""";
    private final Scanner scanner;
    private final CreateEncryption createEncryption;
    private final GetEncryption getEncryption;


    public Menu(Scanner scanner, CreateEncryption createEncryption, GetEncryption getEncryption) {
        this.scanner = scanner;
        this.createEncryption = createEncryption;
        this.getEncryption = getEncryption;
    }

    public void printMenu() {
        boolean running = true;

        while (running) {
            System.out.println(MENU_OPTIONS);

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createEncryption.encryptMessage();
                case 2 -> getEncryption.displayHistory();
                case 3 -> getEncryption.displayHistoryByDate();
                case 4 -> getEncryption.displayAlgorithmUsageCount();
                case 5 -> getEncryption.displayUniqueEncryptions();
                case 0 -> running = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
