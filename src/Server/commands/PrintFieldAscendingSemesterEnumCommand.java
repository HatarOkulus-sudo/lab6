package Server.commands;

import Common.data.Semester;
import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.managers.CollectionManager;

import java.util.List;
import java.util.stream.Collectors;

public class PrintFieldAscendingSemesterEnumCommand implements ServerCommand {

    private final CollectionManager collectionManager;

    public PrintFieldAscendingSemesterEnumCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "print_field_ascending_semester_enum";
    }

    @Override
    public String getDescription() {
        return "вывести значения поля semesterEnum всех элементов в порядке возрастания";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null) {
            return new CommandResponse(false, "Коллекция не инициализирована.");
        }

        List<Semester> list = collectionManager.getSemesterEnumsAscending();
        if (list == null || list.isEmpty()) {
            return new CommandResponse(true, "Значений semesterEnum нет (коллекция пуста).");
        }

        String msg = list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));

        return new CommandResponse(true, msg);
    }
}