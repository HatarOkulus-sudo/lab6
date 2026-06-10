package Server.commands;

import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.managers.CollectionManager;
import Server.managers.FileManager;

public class SaveCommand implements ServerCommand {
    private final CollectionManager collectionManager;
    private final FileManager fileManager;

    public SaveCommand(CollectionManager collectionManager, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить текущую коллекцию в файл (save)";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null) {
            return new CommandResponse(false, "Коллекция не инициализирована.");
        }
        if (fileManager == null) {
            return new CommandResponse(false, "Файловый менеджер не инициализирован.");
        }

        // save не требует аргументов
        String[] args = request == null ? null : request.getArgs();
        if (args != null && args.length > 0) {
            return new CommandResponse(false, "Команда save не принимает аргументы. Использование: save");
        }

        try {
            fileManager.save(collectionManager.getCollection());
            return new CommandResponse(true, "Коллекция сохранена.");
        } catch (Exception e) {
            return new CommandResponse(false, "Ошибка сохранения коллекции: " + e.getMessage());
        }
    }
}