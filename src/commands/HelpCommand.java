// Команда для вывода справки по доступным командам

package commands;

import managers.CommandsManager;

/**
 * Команда вывода справки по доступным командам.
 */
public class HelpCommand implements Command{

    private final CommandsManager commandsManager;

    /**
     * Создает команду help.
     *
     * @param commandsManager менеджер зарегистрированных команд
     */
    public HelpCommand(CommandsManager commandsManager){
        this.commandsManager = commandsManager;
    }

    /**
     * @return имя команды
     */
    @Override
    public String getName(){
        return "help";
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription(){
        return "Вывести справку по доступным командам";
    }

    /**
     * Выводит в консоль список команд и их описаний.
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args){
        for (Command command : commandsManager.getCommands()) { // Цикл проходит по всем командам и выводит их имя и описание
            System.out.println(command.getName() + ": " + command.getDescription()); // Выводит все команды с их именем и описанием
        }
    }
}
