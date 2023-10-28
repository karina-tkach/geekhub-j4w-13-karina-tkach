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
                String[] studentsInfo = collectStudentsInfo(numberOfStudents);
                displayAverageGrades(studentsInfo, numberOfStudents);
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

    public static void displayAverageGrades(String[] studentsInfo, int numberOfStudents) {
        double sumOfMathGrades = 0;
        double sumOfScienceGrades = 0;
        double sumOfHistoryGrades = 0;
        System.out.println();
        System.out.println("Average Grades for Each Student:");
        for (int i = 0; i < studentsInfo.length; i += 4) {
            int mathGrade = Integer.parseInt(studentsInfo[i + 1]);
            sumOfMathGrades += mathGrade;
            int scienceGrade = Integer.parseInt(studentsInfo[i + 2]);
            sumOfScienceGrades += scienceGrade;
            int historyGrade = Integer.parseInt(studentsInfo[i + 3]);
            sumOfHistoryGrades += historyGrade;
            double average = calculateAverage(mathGrade, scienceGrade, historyGrade);
            System.out.printf("%s: %f%n", studentsInfo[i], average);
        }
        System.out.println();
        System.out.println("Average Grades for Each Subject:");
        System.out.printf("Math: %f%n", sumOfMathGrades / numberOfStudents);
        System.out.printf("Science: %f%n", sumOfScienceGrades / numberOfStudents);
        System.out.printf("History: %f%n", sumOfHistoryGrades / numberOfStudents);
    }

    public static double calculateAverage(int firstNumber, int secondNumber, int thirdNumber) {
        final double COUNT_OF_NUMBERS = 3.0;
        return (firstNumber + secondNumber + thirdNumber) / COUNT_OF_NUMBERS;
    }
}
