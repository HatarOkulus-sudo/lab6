package Server.commands;

import Common.dto.*;
import Server.managers.CollectionManager;
import java.time.format.DateTimeFormatter;

public class InfoCommand implements ServerCommand{

    private final CollectionManager collectionManager;
    public InfoCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Вывести информацию о коллекции";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null){
            return new CommandResponse(false, "Коллекция не инициализирована.");
        }
        return new CommandResponse(true, collectionManager.getInfo());
    }
}
