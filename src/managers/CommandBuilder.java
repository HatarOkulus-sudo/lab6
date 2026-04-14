// Класс для создания и регистрации всех команд в CommandsManager

package managers;

/**
 * Фабрика для создания и регистрации всех поддерживаемых команд.
 */
public class CommandBuilder {
    /**
     * Создает и заполняет менеджер команд.
     *
     * @param collectionManager менеджер коллекции
     * @param fileManager файловый менеджер
     * @param inputManager менеджер ввода
     * @param historyManager менеджер истории
     * @return настроенный экземпляр менеджера команд
     */
    public static CommandsManager createCommandManager(CollectionManager collectionManager,
                                                       FileManager fileManager,
                                                       InputManager inputManager,
                                                       HistoryManager historyManager) {
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
        commandsManager.registerCommand(new commands.FilterContainsNameCommand(collectionManager));
        commandsManager.registerCommand(new commands.HistoryCommand(historyManager));
        commandsManager.registerCommand(new commands.MaxByShouldBeExpelledCommand(collectionManager));
        commandsManager.registerCommand(new commands.RemoveAllByStudentsCountCommand(collectionManager));
        commandsManager.registerCommand(new commands.RemoveLastCommand(collectionManager));
        commandsManager.registerCommand(new commands.ShuffleCommand(collectionManager));
        commandsManager.registerCommand(new commands.ExecuteScriptCommand(commandsManager));

        return commandsManager; // Возвращаем заполненный CommandsManager

    }
}