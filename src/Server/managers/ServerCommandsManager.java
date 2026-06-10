package Server.managers;

import Server.commands.ServerCommand;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ServerCommandsManager {

    private final Map<String, ServerCommand> commands = new HashMap<>();

    public void registerCommand(ServerCommand command) {
        if (command == null || command.getName() == null) return;
        commands.put(command.getName().trim().toLowerCase(), command);
    }

    public ServerCommand getCommandByName(String name) {
        if (name == null) return null;
        return commands.get(name.trim().toLowerCase());
    }

    public Collection<ServerCommand> getCommands() {
        return commands.values();
    }
}
