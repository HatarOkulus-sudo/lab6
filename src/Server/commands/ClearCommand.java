package Server.commands;

import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.managers.CollectionManager;

public class ClearCommand implements ServerCommand {

    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Очистить коллекцию";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null) {
            return new CommandResponse(false, "Коллекция не инициализирована.");
        }

        // clear не принимает аргументы
        String[] args = request.getArgs();
        if (args != null && args.length > 0 && args[0] != null && !args[0].isBlank()) {
            return new CommandResponse(false, "Ошибка: Команда clear не принимает аргументы.\nПример: clear");
        }

        collectionManager.clear();
        return new CommandResponse(true, "Коллекция успешно очищена.");
    }
}