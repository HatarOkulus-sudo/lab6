package Common.dto;

import Common.data.StudyGroup;

import java.io.Serializable;
import java.util.List;

public class CommandResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final boolean success;
    private final String message;
    private final List<StudyGroup> data;

    public CommandResponse(boolean success, String message) {
        this(success, message, null);
    }

    public CommandResponse(boolean success, String message, List<StudyGroup> data) {
        this.success = success;
        this.message = message == null ? "" : message.trim();
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<StudyGroup> getData() {
        return data;
    }
}