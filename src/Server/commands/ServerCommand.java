package Server.commands;

import Common.dto.*;


public interface ServerCommand {

    String getName();

    String getDescription();

    CommandResponse execute(CommandRequest request);
}
