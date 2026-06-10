package Server.commands;

import Common.data.StudyGroup;
import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.managers.CollectionManager;

public class UpdateCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции по id (update <id> {element})";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null) {
            return new CommandResponse(false, "Коллекция не инициализирована.");
        }

        String[] args = request.getArgs();
        if (args == null || args.length != 1 || args[0] == null || args[0].isBlank()) {
            return new CommandResponse(false, "Использование: update <id>");
        }

        long id;
        try {
            id = Long.parseLong(args[0].trim());
        } catch (NumberFormatException e) {
            return new CommandResponse(false, "id должен быть числом. Использование: update <id>");
        }

        if (id <= 0) {
            return new CommandResponse(false, "id должен быть > 0");
        }

        Object payload = request.getPayload();
        if (!(payload instanceof StudyGroup)) {
            return new CommandResponse(false, "Команда update требует element типа StudyGroup.");
        }

        StudyGroup newValue = (StudyGroup) payload;

        boolean updated = collectionManager.updateById(id, newValue); // внутри фиксируем id и replace
        if (!updated) {
            return new CommandResponse(false, "Элемент с id=" + id + " не найден.");
        }

        return new CommandResponse(true, "Элемент с id=" + id + " обновлён.");
    }
}