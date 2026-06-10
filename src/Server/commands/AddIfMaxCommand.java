package Server.commands;

import Common.data.StudyGroup;
import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.managers.CollectionManager;

public class AddIfMaxCommand implements ServerCommand {

    private final CollectionManager collectionManager;

    public AddIfMaxCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null) {
            return new CommandResponse(false, "Коллекция не инициализирована.");
        }

        Object payload = request.getPayload();
        if (!(payload instanceof StudyGroup)) {
            return new CommandResponse(false, "Команда add_if_max требует element типа StudyGroup.");
        }

        StudyGroup candidate = (StudyGroup) payload;

        boolean added = collectionManager.addIfMax(candidate);
        if (!added) {
            return new CommandResponse(false, "Элемент не добавлен: он не превышает текущий максимальный элемент коллекции.");
        }

        // id теперь назначается сервером в CollectionManager.add()
        // Сообщение без "candidate.getId()", чтобы не врать.
        return new CommandResponse(true, "Элемент добавлен (add_if_max).");
    }
}