package Server.commands;

import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.managers.CollectionManager;

public class ShuffleCommand implements ServerCommand {

    private final CollectionManager collectionManager;

    public ShuffleCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String getDescription() {
        return "перемешать элементы коллекции в случайном порядке";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null) {
            return new CommandResponse(false, "Коллекция не инициализирована.");
        }

        collectionManager.shuffle();
        return new CommandResponse(true, "Коллекция перемешана.");
    }
}