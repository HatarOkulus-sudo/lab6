package Server.commands;

import Common.data.StudyGroup;
import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.managers.CollectionManager;

public class AddCommand implements ServerCommand {
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null) {
            return new CommandResponse(false, "Коллекция не инициализирована.");
        }

        Object payload = request.getPayload();
        if (!(payload instanceof StudyGroup)) {
            return new CommandResponse(false, "Команда add требует element типа StudyGroup.");
        }

        StudyGroup sg = (StudyGroup) payload;

        StudyGroup added = collectionManager.add(sg); // id назначает сервер (через setId внутри add)
        return new CommandResponse(true, "Элемент добавлен. id=" + added.getId());
    }
}