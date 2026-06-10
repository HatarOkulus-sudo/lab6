package client;

import Common.data.StudyGroup;
import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import client.commands.ExecuteScriptCommand;

import java.io.IOException;
import java.util.*;

public class ClConsole {
    private final ClientNetworkManager net;
    private final Scanner scanner;
    private final InputManager inputManager;

    private final ExecuteScriptCommand executeScriptCommand;

    private static final Set<String> NEED_PAYLOAD = Set.of(
            "add",
            "update",
            "add_if_max",
            "remove_greater"
    );

    public ClConsole(ClientNetworkManager net) {
        this.net = net;
        this.scanner = new Scanner(System.in);
        this.inputManager = new InputManager(scanner);
        this.executeScriptCommand = new ExecuteScriptCommand(this);
    }

    public void run() {
        System.out.println("Клиент запущен. Введите команду (help для списка).");
        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) break;

            String line = scanner.nextLine();
            ConsoleResult r = executeLine(line);

            if (r == ConsoleResult.EXIT_APP) {
                System.out.println("Клиент завершает работу.");
                break;
            }
        }
    }

    public void executePrepared(String commandName, String[] cmdArgs, Object payload) {
        CommandRequest request = new CommandRequest(commandName, cmdArgs, payload);

        try {
            CommandResponse response = net.send(request);

            if (response.getMessage() != null && !response.getMessage().isBlank()) {
                System.out.println(response.getMessage());
            }

            if (response.getData() != null) {
                if (response.getData().isEmpty()) {
                    System.out.println("(Коллекция пуста)");
                } else {
                    for (StudyGroup sg : response.getData()) {
                        System.out.println(sg);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Сервер недоступен или занят другим клиентом: " + e.getMessage());
        }
    }

    public ConsoleResult executeLine(String rawLine) {
        if (rawLine == null) return ConsoleResult.OK;
        String line = rawLine.trim();
        if (line.isEmpty() || line.startsWith("#")) return ConsoleResult.OK;

        String[] tokens = line.split("\\s+");
        String commandName = tokens[0].toLowerCase(Locale.ROOT);
        String[] cmdArgs = Arrays.copyOfRange(tokens, 1, tokens.length);

        // локальные команды
        if ("exit".equals(commandName)) {
            return ConsoleResult.EXIT_APP;
        }

        if ("execute_script".equals(commandName)) {
            if (cmdArgs.length != 1 || cmdArgs[0].isBlank()) {
                System.out.println("Использование: execute_script <file_name>");
                return ConsoleResult.OK;
            }
            executeScriptCommand.execute(cmdArgs[0]);
            return ConsoleResult.OK;
        }

        // серверные команды
        Object payload = null;

        if (NEED_PAYLOAD.contains(commandName)) {
            if ("update".equals(commandName)) {
                if (cmdArgs.length != 1 || cmdArgs[0].isBlank()) {
                    System.out.println("Использование: update <id>");
                    return ConsoleResult.OK;
                }
            }
            payload = inputManager.readStudyGroup();
        }

        CommandRequest request = new CommandRequest(commandName, cmdArgs, payload);

        try {
            CommandResponse response = net.send(request);

            if (response.getMessage() != null && !response.getMessage().isBlank()) {
                System.out.println(response.getMessage());
            }

            // Если сервер прислал коллекцию объектов (show)
            if (response.getData() != null) {
                if (response.getData().isEmpty()) {
                    System.out.println("(Коллекция пуста)");
                } else {
                    for (StudyGroup sg : response.getData()) {
                        System.out.println(sg);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Сервер временно недоступен или занят другим клиентом: " + e.getMessage());
        }

        return ConsoleResult.OK;
    }

    public ConsoleResult executeLineFromScript(String rawLine) {
        ConsoleResult r = executeLine(rawLine);
        if (r == ConsoleResult.EXIT_APP) return ConsoleResult.STOP_SCRIPT;
        return r;
    }

    public enum ConsoleResult {
        OK,
        STOP_SCRIPT,
        EXIT_APP
    }
}