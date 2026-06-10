package client.commands;

import Common.data.*;
import Common.data.StudyGroup;
import client.ClConsole;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.*;

public class ExecuteScriptCommand {

    private final ClConsole console;
    private static final Set<String> scriptsInProgress = new HashSet<>();

    private static final Set<String> NEED_PAYLOAD = Set.of(
            "add",
            "update",
            "add_if_max",
            "remove_greater"
    );

    public ExecuteScriptCommand(ClConsole console) {
        this.console = console;
    }

    public void execute(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            System.out.println("Использование: execute_script <file_name>");
            return;
        }

        File file = new File(fileName.trim());
        try {
            String canonicalPath = file.getCanonicalPath();

            if (scriptsInProgress.contains(canonicalPath)) {
                System.out.println("Обнаружена рекурсия: скрипт уже выполняется: " + canonicalPath);
                System.out.println("Выполнение прервано, чтобы избежать зацикливания.");
                return;
            }

            if (!file.exists() || !file.isFile() || !file.canRead()) {
                System.out.println("Файл скрипта недоступен для чтения: " + fileName);
                return;
            }

            scriptsInProgress.add(canonicalPath);

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;

                    String[] tokens = line.split("\\s+");
                    String commandName = tokens[0].toLowerCase(Locale.ROOT);
                    String[] cmdArgs = Arrays.copyOfRange(tokens, 1, tokens.length);

                    if ("exit".equals(commandName)) {
                        System.out.println("В скрипте встретилась команда exit. Скрипт остановлен, возвращаюсь в консоль.");
                        return;
                    }

                    if ("execute_script".equals(commandName)) {
                        // можно поддержать вложенные скрипты (с защитой от рекурсии)
                        if (cmdArgs.length != 1) {
                            System.out.println("Некорректная команда execute_script в скрипте: " + line);
                            continue;
                        }
                        execute(cmdArgs[0]);
                        continue;
                    }

                    if (NEED_PAYLOAD.contains(commandName)) {
                        // update требует id аргумент
                        if ("update".equals(commandName)) {
                            if (cmdArgs.length != 1) {
                                System.out.println("Некорректная команда update в скрипте. Нужно: update <id>");
                                continue;
                            }
                        }

                        StudyGroup payload = readStudyGroupFromScript(reader);
                        console.executePrepared(commandName, cmdArgs, payload);
                    } else {
                        console.executePrepared(commandName, cmdArgs, null);
                    }
                }
            } finally {
                scriptsInProgress.remove(canonicalPath);
            }

        } catch (IOException e) {
            System.out.println("Ошибка при выполнении скрипта: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка в данных скрипта: " + e.getMessage());
        }
    }

    private StudyGroup readStudyGroupFromScript(BufferedReader reader) throws IOException {
        String name = requireLine(reader, "name");

        Long x = parseLongObj(requireLine(reader, "coordinates.x"));
        long y = parseLong(requireLine(reader, "coordinates.y"));
        Coordinates coordinates = new Coordinates(x, y);

        long studentsCount = parseLong(requireLine(reader, "studentsCount"));
        int shouldBeExpelled = parseInt(requireLine(reader, "shouldBeExpelled"));

        FormOfEducation formOfEducation = parseEnum(requireLine(reader, "formOfEducation"), FormOfEducation.class, false);
        Semester semesterEnum = parseEnum(requireLine(reader, "semesterEnum"), Semester.class, false);

        String adminName = requireLine(reader, "groupAdmin.name");
        Long adminWeight = parseLongObj(requireLine(reader, "groupAdmin.weight"));
        String passportID = requireLine(reader, "groupAdmin.passportID");
        Color eyeColor = parseEnum(requireLine(reader, "groupAdmin.eyeColor"), Color.class, false);
        Color hairColor = parseEnum(requireLine(reader, "groupAdmin.hairColor"), Color.class, true);

        Person groupAdmin = new Person(adminName, adminWeight, passportID, eyeColor, hairColor);

        // id/creationDate сервер всё равно перезапишет
        return new StudyGroup(
                1L,
                name,
                coordinates,
                ZonedDateTime.now(),
                studentsCount,
                shouldBeExpelled,
                formOfEducation,
                semesterEnum,
                groupAdmin
        );
    }

    private static String requireLine(BufferedReader reader, String fieldName) throws IOException {
        String line = reader.readLine();
        if (line == null) {
            throw new IOException("Скрипт закончился, не хватает поля: " + fieldName);
        }
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            throw new IOException("Пустое значение в скрипте для поля: " + fieldName);
        }
        return trimmed;
    }

    private static long parseLong(String s) {
        return Long.parseLong(s.trim());
    }

    private static Long parseLongObj(String s) {
        return Long.valueOf(s.trim());
    }

    private static int parseInt(String s) {
        return Integer.parseInt(s.trim());
    }

    private static <E extends Enum<E>> E parseEnum(String s, Class<E> enumClass, boolean allowEmpty) {
        String trimmed = s == null ? "" : s.trim();
        if (allowEmpty && trimmed.isEmpty()) return null;
        return Enum.valueOf(enumClass, trimmed.toUpperCase(Locale.ROOT));
    }
}