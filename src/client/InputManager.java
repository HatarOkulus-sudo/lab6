
package client;

import Common.data.Color;
import Common.data.Coordinates;
import Common.data.FormOfEducation;
import Common.data.Person;
import Common.data.Semester;
import Common.data.StudyGroup;

import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Scanner;

public class InputManager {
    private final Scanner scanner;

    public InputManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public StudyGroup readStudyGroup() {
        String name = readNonEmptyString("Введите name (не пусто): ");

        Coordinates coordinates = readCoordinates();

        long studentsCount = readLongGreaterThanZero("Введите studentsCount (> 0): ");
        int shouldBeExpelled = readIntGreaterThanZero("Введите shouldBeExpelled (> 0): ");

        FormOfEducation formOfEducation = readEnum("Введите formOfEducation", FormOfEducation.class, false);
        Semester semesterEnum = readEnum("Введите semesterEnum", Semester.class, false);

        Person groupAdmin = readPerson();
        long tmpId = 1L;
        ZonedDateTime tmpCreationDate = ZonedDateTime.now();

        return new StudyGroup(
                tmpId,
                name,
                coordinates,
                tmpCreationDate,
                studentsCount,
                shouldBeExpelled,
                formOfEducation,
                semesterEnum,
                groupAdmin
        );
    }

    private Coordinates readCoordinates() {
        Long x = readLongObjectNotNull("Введите coordinates.x (Long, не null): ");
        long y = readLongAny("Введите coordinates.y (long): ");
        return new Coordinates(x, y);
    }

    private Person readPerson() {
        String name = readNonEmptyString("Введите groupAdmin.name (не пусто): ");
        Long weight = readLongObjectGreaterThanZero("Введите groupAdmin.weight (Long > 0): ");
        String passportId = readNonEmptyString("Введите groupAdmin.passportID (не пусто): ");

        Color eyeColor = readEnum("Введите groupAdmin.eyeColor", Color.class, false);
        Color hairColor = readEnum("Введите groupAdmin.hairColor (можно пусто)", Color.class, true);

        return new Person(name, weight, passportId, eyeColor, hairColor);
    }

    private String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine();
            if (s == null) continue;
            s = s.trim();
            if (!s.isEmpty()) return s;
            System.out.println("Строка не может быть пустой. Повторите ввод.");
        }
    }

    private long readLongGreaterThanZero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine();
            if (s == null) continue;
            s = s.trim();
            try {
                long v = Long.parseLong(s);
                if (v > 0) return v;
                System.out.println("Число должно быть > 0.");
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число (long).");
            }
        }
    }

    private int readIntGreaterThanZero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine();
            if (s == null) continue;
            s = s.trim();
            try {
                int v = Integer.parseInt(s);
                if (v > 0) return v;
                System.out.println("Число должно быть > 0.");
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число (int).");
            }
        }
    }

    private Long readLongObjectNotNull(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine();
            if (s == null) continue;
            s = s.trim();
            try {
                return Long.valueOf(s);
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число (Long).");
            }
        }
    }

    private Long readLongObjectGreaterThanZero(String prompt) {
        while (true) {
            Long v = readLongObjectNotNull(prompt);
            if (v != null && v > 0) return v;
            System.out.println("Число должно быть > 0.");
        }
    }

    private long readLongAny(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine();
            if (s == null) continue;
            s = s.trim();
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число (long).");
            }
        }
    }
    private <E extends Enum<E>> E readEnum(String title, Class<E> enumClass, boolean allowNull) {
        E[] values = enumClass.getEnumConstants();

        while (true) {
            System.out.println(title + ":");
            System.out.print("Допустимо: ");
            for (int i = 0; i < values.length; i++) {
                System.out.print(values[i].name());
                if (i + 1 < values.length) System.out.print(", ");
            }
            if (allowNull) System.out.print(" (или пусто)");
            System.out.println();
            System.out.print("> ");

            String s = scanner.nextLine();
            if (s == null) continue;

            String trimmed = s.trim();
            if (allowNull && trimmed.isEmpty()) return null;

            String normalized = trimmed.toUpperCase(Locale.ROOT);
            for (E v : values) {
                if (v.name().equals(normalized)) return v;
            }

            System.out.println("Неверное значение. Повторите ввод.");
        }
    }
}