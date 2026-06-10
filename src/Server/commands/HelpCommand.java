package Server.commands;

import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.commands.ServerCommand;
import Server.managers.ServerCommandsManager;

import java.util.Comparator;
import java.util.stream.Collectors;

public class HelpCommand implements ServerCommand {

    private final ServerCommandsManager manager;

    public HelpCommand(ServerCommandsManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (manager == null) {
            return new CommandResponse(false, "Команды не инициализированы (ServerCommandsManager == null).");
        }

        String text = manager.getCommands().stream()
                .sorted(Comparator.comparing(ServerCommand::getName))
                .map(cmd -> {
                    String desc = cmd.getDescription();
                    if (desc == null || desc.isBlank()) return cmd.getName();
                    return cmd.getName() + " : " + desc;
                })
                .collect(Collectors.joining("\n"));

        if (text.isBlank()) {
            text = "Команды не зарегистрированы.";
        }
        text = text + "\n\nЛокальные команды клиента: execute_script, exit";

        return new CommandResponse(true, text);
    }
}