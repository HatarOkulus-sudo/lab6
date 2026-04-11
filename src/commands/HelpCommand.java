// Команда для вывода справки по доступным командам

package commands;

import managers.CommandsManager;

public class HelpCommand implements Command{

    private final CommandsManager commandsManager;
    public HelpCommand(CommandsManager commandsManager){
        this.commandsManager = commandsManager;
    }

    @Override
    public String getName(){
        return "help";
    }

    @Override
    public String getDescription(){
        return "Вывести справку по доступным командам";
    }

    @Override
    public void execute(String[] args){
        for (Command command : commandsManager.getCommands()) { // Цикл проходит по всем командам и выводит их имя и описание
            System.out.println(command.getName() + ": " + command.getDescription()); // Выводит все команды с их именем и описанием
        }
    }
}
