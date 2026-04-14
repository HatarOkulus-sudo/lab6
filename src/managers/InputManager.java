// Класс для управления вводом данных от пользователя. Содержит методы для чтения различных типов данных и создания объектов StudyGroup на основе пользовательского ввода.

package managers;

import data.*;

import java.time.ZonedDateTime;
import java.util.Scanner;

/**
 * Менеджер интерактивного ввода данных из консоли.
 */
public class InputManager {
    private final Scanner scanner;

    /**
     * Создает менеджер ввода.
     *
     * @param scanner источник пользовательского ввода
     */
    public InputManager(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Считывает строку с выводом приглашения.
     *
     * @param prompt текст приглашения
     * @param canBeEmpty признак допустимости пустого ввода
     * @return введенная строка или null, если пустое значение допустимо
     */
    private String readString(String prompt, boolean canBeEmpty) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                if (canBeEmpty){
                    return null;
                } else {
                    System.err.println("Error: input cannot be empty");
                    return this.readString(prompt,false);
                }
            } else {
                return input;
            }
        }
    }

    /**
     * Считывает значение типа long.
     *
     * @param prompt текст приглашения
     * @param mustBePositive признак требования положительного значения
     * @return введенное число
     */
    private long readLong(String prompt, boolean mustBePositive) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                long value = Long.parseLong(input);
                if (mustBePositive && value <= 0) {
                    System.err.println("Error: value must be greater than 0");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.err.println("Error: invalid number format");
            }
        }
    }

    /**
     * Считывает значение типа int.
     *
     * @param prompt текст приглашения
     * @param mustBePositive признак требования положительного значения
     * @return введенное число
     */
    private int readInt(String prompt, boolean mustBePositive) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (mustBePositive && value <= 0) {
                    System.err.println("Error: value must be greater than 0");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: invalid number format");
            }
        }
    }

    /**
     * Считывает поля из консоли и формирует объект {@link StudyGroup}.
     *
     * @return новый объект учебной группы
     */
    public StudyGroup readStudyGroup() {
        String name = readString("Enter group name: ", false);
        long x = readLong("Enter coordinate X: ", false);
        long y = readLong("Enter coordinate Y: ", false);
        Coordinates coordinates = new Coordinates(x, y);
        long studentsCount = readLong("Enter students count: ", true);
        int shouldBeExpelled = readInt("Enter should be expelled count: ", true);
        FormOfEducation formOfEducation = FormOfEducation.valueOf(readString("Enter form of education (DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES): ", false).toUpperCase());
        Semester semesterEnum = Semester.valueOf(readString("Enter semester (FIRST, SECOND, FIFTH, EIGHTH): ", false).toUpperCase());
        System.out.println("Enter group admin details:");
        String adminName = readString("Admin name: ", false);
        Long adminWeight = readLong("Admin weight: ", true);
        String adminPassportID = readString("Admin passport ID: ", false);
        Color adminEyeColor = Color.valueOf(readString("Admin eye color (RED, BLUE, BLACK, YELLOW, BROWN, ORANGE): ", false).toUpperCase());
        Color adminHairColor = Color.valueOf(readString("Admin hair color (RED, BLUE, BLACK, YELLOW, BROWN, ORANGE) or leave empty for null: ", true).toUpperCase());
        Person groupAdmin = new Person(adminName, adminWeight, adminPassportID, adminEyeColor, adminHairColor);
        return new StudyGroup(1L, name, coordinates, ZonedDateTime.now(), studentsCount, shouldBeExpelled, formOfEducation, semesterEnum, groupAdmin);
    }
}
