// класс для управления командами, позволяет регистрировать команды и получать их по имени

package managers;

import commands.Command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandsManager {
    private final Map<String, Command> commands = new HashMap<>(); // хранит команды по их именам

    public void registerCommand(Command command) { // регистрирует команду, добавляя ее в коллекцию
        this.commands.put(command.getName(), command);
    }

    public Command getCommandByName(String name) { // получает команду по ее имени, возвращая null, если команда не найдена
        return this.commands.get(name);
    }

    public Collection<Command> getCommands() { // возвращает коллекцию всех зарегистрированных команд
        return this.commands.values();
    }
}
