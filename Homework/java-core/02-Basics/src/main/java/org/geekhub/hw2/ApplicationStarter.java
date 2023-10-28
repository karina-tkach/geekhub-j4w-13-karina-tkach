package org.geekhub.hw2;

import java.util.Scanner;

public class ApplicationStarter {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("The number of students is not provided");
            System.exit(0);
        } else {
            int numberOfStudents = Integer.parseInt(args[0]);
            if (numberOfStudents <= 0) {
                System.out.println("The number of students must greater than zero");
                System.exit(0);
            } else {
               //
            }
        }
    }

    public static String[] collectStudentsInfo(int numberOfStudents) {
        Scanner scanner = new Scanner(System.in);
        final int NUMBER_OF_PROPERTIES = 4;
        String[] info = new String[numberOfStudents * NUMBER_OF_PROPERTIES];
        for (int i = 1, j = 0; i <= numberOfStudents; i++, j += 4) {
            System.out.printf("Enter information for student #%d%n", i);
            info[j] = getStudentInfoAndCheck("Student's Name", scanner);
            info[j + 1] = getStudentInfoAndCheck("Math Grade (0-100)", scanner);
            info[j + 2] = getStudentInfoAndCheck("Science Grade (0-100)", scanner);
            info[j + 3] = getStudentInfoAndCheck("History Grade (0-100)", scanner);
        }
        scanner.close();
        return info;
    }

    public static String getStudentInfoAndCheck(String infoName, Scanner scanner) {
        boolean isWrong;
        String property;
        do {
            System.out.printf("%s: ", infoName);
            property = scanner.nextLine();
            isWrong = property.isBlank();
            if (!(isWrong || infoName.equals("Student's Name"))) {
                isWrong = !(Integer.parseInt(property) >= 0 && Integer.parseInt(property) <= 100);
            }
            if (isWrong) {
                System.out.printf("%s is wrong, please input it again%n", infoName);
            }
        } while (isWrong);
        return property;
    }
}
