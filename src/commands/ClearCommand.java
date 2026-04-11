// Команда для очистки коллекции

package commands;

import managers.CollectionManager;

public class ClearCommand implements Command{

    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Очистить коллекцию";
    }

    @Override
    public void execute(String[] args) {

        if (args != null && args.length > 0 && !args[0].isBlank()) { // проверка есть ли аргумент. Данная команда не должна принимать аргументы
            System.out.println("Ошибка : Команда clear не принимает аргументы.");
            System.out.println("Пример: clear");
        }
        collectionManager.getCollection().clear(); // отчистка коллекции
        System.out.println("Коллекция успешно очищена.");
    }
}
