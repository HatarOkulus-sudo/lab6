package Server.commands;

import Common.data.StudyGroup;
import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.managers.CollectionManager;

import java.util.List;

public class ShowCommand implements ServerCommand {

    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "Вывести все элементы коллекции (объектами, отсортировано по name)";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null) {
            return new CommandResponse(false, "Коллекция не инициализирована.");
        }

        List<StudyGroup> data = collectionManager.getAllSortedByName();
        return new CommandResponse(true, "Коллекция (отсортирована по name):", data);
    }
}