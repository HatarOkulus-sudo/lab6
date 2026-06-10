package Server.commands;

import Common.data.StudyGroup;
import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.managers.CollectionManager;

public class RemoveGreaterCommand implements ServerCommand {

    private final CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, превышающие заданный";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null) {
            return new CommandResponse(false, "Коллекция не инициализирована.");
        }

        Object payload = request.getPayload();
        if (!(payload instanceof StudyGroup)) {
            return new CommandResponse(false, "Команда remove_greater требует element типа StudyGroup.");
        }

        StudyGroup pivot = (StudyGroup) payload;

        int removed = collectionManager.removeGreater(pivot);
        return new CommandResponse(true, "Удалено элементов: " + removed);
    }
}