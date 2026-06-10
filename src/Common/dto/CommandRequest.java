package Common.dto;

import java.io.Serializable;

public class CommandRequest implements Serializable{
    private static final long serialVersionUID = 1L;
    private String commandName;
    private String[] args;
    private Object payload;

    public CommandRequest(String commandName, String[] args, Object payload) {
        this.commandName = commandName == null ? "" : commandName.trim().toLowerCase();
        this.args = args == null ? new String[0] : args;
        this.payload = payload;
    }

    public String getCommandName(){
        return commandName;
    }

    public String[] getArgs(){
        return args;
    }

    public Object getPayload(){
        return payload;
    }
}
