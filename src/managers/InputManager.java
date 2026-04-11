// Класс для управления вводом данных от пользователя. Содержит методы для чтения различных типов данных и создания объектов StudyGroup на основе пользовательского ввода.

package managers;

import data.*;
import java.util.Scanner;

public class InputManager {
    private final Scanner scanner;

    public InputManager(Scanner scanner) {
        this.scanner = scanner; // Инициализация сканера для чтения пользовательского ввода
    }

    private String readString(String prompt, boolean canBeEmpty) { // Метод для чтения строки с возможностью указать, может ли строка быть пустой
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                if (canBeEmpty){
                    return null;
                } else {
                    System.out.println("Error: input cannot be empty");
                    return this.readString(prompt,false);
                }
            } else {
                return input;
            }
        }
    }

    private long readLong(String prompt, boolean mustBePositive) { // Метод для чтения long значения с возможностью указать, должно ли значение быть положительным
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                long value = Long.parseLong(input);
                if (mustBePositive && value <= 0) {
                    System.out.println("Error: value must be greater than 0");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: invalid number format");
            }
        }
    }

    private int readInt(String prompt, boolean mustBePositive) { // Метод для чтения int значения с возможностью указать, должно ли значение быть положительным
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (mustBePositive && value <= 0) {
                    System.out.println("Error: value must be greater than 0");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: invalid number format");
            }
        }
    }

    public StudyGroup readStudyGroup() { // Метод для чтения данных и создания объекта StudyGroup на основе пользовательского ввода
        String name = readString("Enter group name: ", false);
        long x = readLong("Enter coordinate X: ", false);
        long y = readLong("Enter coordinate Y: ", false);
        Coordinates coordinates = new Coordinates(x, y);
        long studentsCount = readLong("Enter students count: ", true);
        int shouldBeExpelled = readInt("Enter should be expelled count: ", true);
        FormOfEducation formOfEducation = FormOfEducation.valueOf(readString("Enter form of education (DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES): ", false).toUpperCase());
        Semester semesterEnum = Semester.valueOf(readString("Enter semester (FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH, EIGHTH): ", false).toUpperCase());
        System.out.println("Enter group admin details:");
        String adminName = readString("Admin name: ", false);
        Long adminWeight = readLong("Admin weight: ", true);
        String adminPassportID = readString("Admin passport ID: ", false);
        Color adminEyeColor = Color.valueOf(readString("Admin eye color (GREEN, RED, BLACK, BLUE, YELLOW): ", false).toUpperCase());
        Color adminHairColor = Color.valueOf(readString("Admin hair color (GREEN, RED, BLACK, BLUE, YELLOW) or leave empty for null: ", true).toUpperCase());
        Person groupAdmin = new Person(adminName, adminWeight, adminPassportID, adminEyeColor, adminHairColor);
        return new StudyGroup(0L, name, coordinates, null, studentsCount, shouldBeExpelled, formOfEducation, semesterEnum, groupAdmin);
    }
}
