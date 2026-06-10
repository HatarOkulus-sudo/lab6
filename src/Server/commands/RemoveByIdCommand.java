package Server.commands;

import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.commands.ServerCommand;
import Server.managers.CollectionManager;

public class RemoveByIdCommand implements ServerCommand {

    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null) {
            return new CommandResponse(false, "Коллекция не инициализирована (CollectionManager == null).");
        }

        String[] args = request.getArgs();
        if (args == null || args.length != 1 || args[0] == null || args[0].isBlank()) {
            return new CommandResponse(false, "Использование: remove_by_id <id>");
        }

        long id;
        try {
            id = Long.parseLong(args[0].trim());
        } catch (NumberFormatException e) {
            return new CommandResponse(false, "id должен быть числом. Использование: remove_by_id <id>");
        }

        if (id <= 0) {
            return new CommandResponse(false, "id должен быть > 0");
        }

        boolean removed = collectionManager.removeById(id);
        if (!removed) {
            return new CommandResponse(false, "Элемент с id=" + id + " не найден.");
        }

        return new CommandResponse(true, "Элемент с id=" + id + " удалён.");
    }
}