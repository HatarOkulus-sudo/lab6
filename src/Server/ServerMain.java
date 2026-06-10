package Server;

import Common.data.StudyGroup;
import Server.managers.CollectionManager;
import Server.managers.FileManager;
import Server.managers.ServerCommandBuilder;
import Server.managers.ServerCommandsManager;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class ServerMain {
    public static final int PORT = 1984;

    public static void main(String[] args) {
        System.out.println("[SERVER] Запуск... порт=" + PORT);

        // 1) путь к файлу
        String filePath = resolveFilePath(args);
        if (filePath == null) {
            System.out.println("[SERVER] Не задан путь к файлу коллекции.");
            System.out.println("[SERVER] Передай путь первым аргументом или задай переменную окружения LAB6_FILE.");
            return;
        }
        System.out.println("[SERVER] Файл коллекции: " + filePath);

        // 2) менеджеры
        System.out.println("[SERVER] Инициализация менеджеров...");
        CollectionManager collectionManager = new CollectionManager();
        ServerCommandsManager commandsManager = new ServerCommandsManager();
        FileManager fileManager = new FileManager(filePath);
        ServerCommandBuilder.registerAll(commandsManager, collectionManager, fileManager);

        // Лог зарегистрированных команд
        String commandsList = commandsManager.getCommands().stream()
                .map(cmd -> cmd.getName() == null ? "" : cmd.getName().trim())
                .filter(s -> !s.isEmpty())
                .sorted()
                .collect(Collectors.joining(", "));
        System.out.println("[SERVER] Команды зарегистрированы (" + commandsManager.getCommands().size() + "): " + commandsList);

        // 3) загрузка коллекции
        try {
            System.out.println("[SERVER] Загрузка коллекции из файла...");
            LinkedList<StudyGroup> loaded = fileManager.load();
            for (StudyGroup sg : loaded) {
                collectionManager.addFromFile(sg);
            }
            System.out.println("[SERVER] Загружено элементов: " + loaded.size());
            System.out.println("[SERVER] Текущий размер коллекции: " + collectionManager.size());
        } catch (Exception e) {
            System.out.println("[SERVER] Не удалось загрузить коллекцию из файла: " + filePath);
            System.out.println("[SERVER] Причина: " + e.getMessage());
            System.out.println("[SERVER] Стартую с пустой коллекцией.");
        }

        // 4) сохранение при завершении
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("[SERVER] Завершение работы. Сохраняю коллекцию...");
            try {
                fileManager.save(collectionManager.getCollection());
                System.out.println("[SERVER] Коллекция сохранена в " + filePath);
            } catch (Exception e) {
                System.out.println("[SERVER] Ошибка сохранения коллекции в " + filePath + ": " + e.getMessage());
            }
        }));

        // 5) старт сети
        System.out.println("[SERVER] Запуск сетевого модуля...");
        ServerNetworkManager server = new ServerNetworkManager(PORT, commandsManager);
        server.start();
    }

    private static String resolveFilePath(String[] args) {
        if (args != null && args.length > 0 && args[0] != null && !args[0].isBlank()) {
            return args[0].trim();
        }
        String env = System.getenv("LAB6_FILE");
        if (env != null && !env.isBlank()) {
            return env.trim();
        }
        return null;
    }
}