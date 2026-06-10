package Server.managers;
import Server.commands.*;

public class ServerCommandBuilder {
    public static void registerAll(ServerCommandsManager manager, CollectionManager collectionManager, FileManager fileManager) {
        if (manager == null) return;
        manager.registerCommand(new Server.commands.HelpCommand(manager));
        manager.registerCommand(new InfoCommand(collectionManager));
        manager.registerCommand(new ShowCommand(collectionManager));
        manager.registerCommand(new ClearCommand(collectionManager));
        manager.registerCommand(new Server.commands.RemoveByIdCommand(collectionManager));
        manager.registerCommand(new RemoveAllBySemesterEnumCommand(collectionManager));
        manager.registerCommand(new PrintUniqueFormOfEducationCommand(collectionManager));
        manager.registerCommand(new PrintFieldAscendingSemesterEnumCommand(collectionManager));
        manager.registerCommand(new ShuffleCommand(collectionManager));
        manager.registerCommand(new AddIfMaxCommand(collectionManager));
        manager.registerCommand(new RemoveGreaterCommand(collectionManager));
        manager.registerCommand(new AddCommand(collectionManager));
        manager.registerCommand(new UpdateCommand(collectionManager));
        manager.registerCommand(new SaveCommand(collectionManager, fileManager));
    }
}
