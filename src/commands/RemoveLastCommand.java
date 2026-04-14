package commands;

import managers.CollectionManager;

/**
 * Команда удаления последнего элемента коллекции.
 */
public class RemoveLastCommand implements Command {

    private final CollectionManager collectionManager;

    /**
     * Создает команду remove_last.
     *
     * @param collectionManager менеджер коллекции
     */
    public RemoveLastCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * @return имя команды
     */
    @Override
    public String getName() {
        return "remove_last";
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "Удалить последний элемент из коллекции";
    }

    /**
     * Удаляет последний элемент, если коллекция не пуста.
     *
     * @param args аргументы команды
     */
    @Override
    public void execute(String[] args) {
        if (args != null && args.length > 0 && !args[0].isBlank()) {
            System.out.println("Ошибка : Команда remove_last не принимает аргументы.");
            System.out.println("Пример: remove_last");
            return;
        }
        if (collectionManager.getCollection().isEmpty()) {
            System.out.println("Коллекция пуста. Нечего удалять.");
            return;
        }
        boolean removed = collectionManager.removeLast();
        if (removed){
            System.out.println("Последний элемент коллекции успешно удалён.");
        } else {
            System.out.println("Ошибка при удалении последнего элемента коллекции.");
        }
    }
}