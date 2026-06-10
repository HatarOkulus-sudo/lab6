package Server.commands;

import Common.data.Semester;
import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.managers.CollectionManager;

public class RemoveAllBySemesterEnumCommand implements ServerCommand {

    private final CollectionManager collectionManager;

    public RemoveAllBySemesterEnumCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "remove_all_by_semester_enum";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, значение поля semesterEnum которого эквивалентно заданному";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null) {
            return new CommandResponse(false, "Коллекция не инициализирована.");
        }

        String[] args = request.getArgs();
        if (args == null || args.length != 1 || args[0] == null || args[0].isBlank()) {
            return new CommandResponse(false, "Использование: remove_all_by_semester_enum <semesterEnum>");
        }

        Semester semester;
        try {
            semester = Semester.valueOf(args[0].trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return new CommandResponse(false, "Неизвестный semesterEnum: " + args[0] + ". Использование: remove_all_by_semester_enum <semesterEnum>");
        }

        int removed = collectionManager.removeAllBySemesterEnum(semester);
        return new CommandResponse(true, "Удалено элементов: " + removed);
    }
}