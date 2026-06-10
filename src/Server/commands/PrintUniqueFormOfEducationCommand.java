package Server.commands;

import Common.data.FormOfEducation;
import Common.dto.CommandRequest;
import Common.dto.CommandResponse;
import Server.managers.CollectionManager;

import java.util.Set;
import java.util.stream.Collectors;

public class PrintUniqueFormOfEducationCommand implements ServerCommand {

    private final CollectionManager collectionManager;

    public PrintUniqueFormOfEducationCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "print_unique_form_of_education";
    }

    @Override
    public String getDescription() {
        return "вывести уникальные значения поля formOfEducation всех элементов в коллекции";
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (collectionManager == null) {
            return new CommandResponse(false, "Коллекция не инициализирована.");
        }

        Set<FormOfEducation> set = collectionManager.getUniqueFormOfEducation();
        if (set == null || set.isEmpty()) {
            return new CommandResponse(true, "Уникальных значений formOfEducation нет (коллекция пуста или поле отсутствует).");
        }

        String msg = set.stream()
                .map(String::valueOf)
                .sorted()
                .collect(Collectors.joining("\n"));

        return new CommandResponse(true, msg);
    }
}