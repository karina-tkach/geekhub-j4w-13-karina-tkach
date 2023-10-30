package org.geekhub.hw2;

import java.util.Scanner;

public class ApplicationStarter {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("The number of students is not provided");
            System.exit(-1);
        } else {
            int numberOfStudents = Integer.parseInt(args[0]);
            if (numberOfStudents <= 0) {
                System.out.println("The number of students must be greater than zero");
                System.exit(-2);
            } else {
                Scanner scanner = new Scanner(System.in);
                String[] gradesNames = {"Math Grade (0-100)", "Science Grade (0-100)", "History Grade (0-100)"};
                String[] studentsNames = new String[numberOfStudents];
                int[][] studentsGrades = new int[numberOfStudents][];
                for (int i = 0; i < numberOfStudents; i++) {
                    System.out.printf("Enter information for student #%d%n", i + 1);
                    studentsNames[i] = getStudentNameAndCheck(scanner);
                    studentsGrades[i] = getStudentGradesAndCheck(gradesNames, scanner);
                }
                scanner.close();
                displayAverageGrades(studentsNames, studentsGrades, numberOfStudents);
            }
        }
    }

    public static String getStudentNameAndCheck(Scanner scanner) {
        boolean isWrong;
        String name;
        do {
            System.out.print("Student's Name: ");
            name = scanner.nextLine();
            isWrong = name.isBlank();
            if (isWrong) {
                System.out.println("Student's Name is wrong, please input it again");
            }
        } while (isWrong);
        return name;
    }

    public static int[] getStudentGradesAndCheck(String[] gradesNames, Scanner scanner) {
        final int NUMBER_OF_GRADES = 3;
        int[] studentGrades = new int[NUMBER_OF_GRADES];
        boolean isWrong;
        int grade;
        for (int i = 0; i < NUMBER_OF_GRADES; i++) {
            do {
                System.out.printf("%s: ", gradesNames[i]);
                //we use Integer.parseInt(), so not to skip next nextLine() call
                grade = Integer.parseInt(scanner.nextLine());
                isWrong = !(grade >= 0 && grade <= 100);
                if (isWrong) {
                    System.out.printf("%s is wrong, please input it again%n", gradesNames[i]);
                }
            } while (isWrong);
            studentGrades[i] = grade;
        }
        return studentGrades;
    }

    public static void displayAverageGrades(String[] studentsNames, int[][] studentsGrades, int numberOfStudents) {
        double sumOfMathGrades = 0;
        double sumOfScienceGrades = 0;
        double sumOfHistoryGrades = 0;
        System.out.println();
        System.out.println("Average Grades for Each Student:");
        for (int i = 0; i < numberOfStudents; i++) {
            int mathGrade = studentsGrades[i][0];
            sumOfMathGrades += mathGrade;
            int scienceGrade = studentsGrades[i][1];
            sumOfScienceGrades += scienceGrade;
            int historyGrade = studentsGrades[i][2];
            sumOfHistoryGrades += historyGrade;
            double average = calculateAverage(mathGrade, scienceGrade, historyGrade);
            System.out.printf("%s: %f%n", studentsNames[i], average);
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
