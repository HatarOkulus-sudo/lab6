// Класс для создания и регистрации всех команд в CommandsManager

package managers;

public class CommandBuilder {
    public static CommandsManager createCommandManager(CollectionManager collectionManager,
                                                       FileManager fileManager,
                                                       InputManager inputManager) {
        CommandsManager commandsManager = new CommandsManager();


        // Регистрируем команды, передавая необходимые зависимости
        commandsManager.registerCommand(new commands.HelpCommand(commandsManager));
        commandsManager.registerCommand(new commands.InfoCommand(collectionManager));
        commandsManager.registerCommand(new commands.ShowCommand(collectionManager));
        commandsManager.registerCommand(new commands.AddCommand(collectionManager, inputManager));
        commandsManager.registerCommand(new commands.UpdateIdCommand(collectionManager, inputManager));
        commandsManager.registerCommand(new commands.RemoveByIdCommand(collectionManager, inputManager));
        commandsManager.registerCommand(new commands.ClearCommand(collectionManager));
        commandsManager.registerCommand(new commands.SaveCommand(collectionManager, fileManager));
        commandsManager.registerCommand(new commands.ExitCommand());

        return commandsManager; // Возвращаем заполненный CommandsManager

    }
}