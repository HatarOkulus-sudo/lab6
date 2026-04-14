// Команда для очистки коллекции

package commands;

import managers.CollectionManager;

/**
 * Команда очистки коллекции.
 */
public class ClearCommand implements Command{

    private final CollectionManager collectionManager;

    /**
     * Создает команду clear.
     *
     * @param collectionManager менеджер коллекции
     */
    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * @return имя команды
     */
    @Override
    public String getName() {
        return "clear";
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "Очистить коллекцию";
    }

    /**
     * Очищает коллекцию после проверки отсутствия аргументов.
     *
     * @param args аргументы команды
     */
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
