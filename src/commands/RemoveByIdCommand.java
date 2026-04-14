// удаляет элемент из коллекции по его id

package commands;

import managers.CollectionManager;
import managers.InputManager;

/**
 * Команда удаления элемента коллекции по идентификатору.
 */
public class RemoveByIdCommand implements Command{

    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    /**
     * Создает команду remove_by_id.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер ввода (зарезервирован для единообразия зависимостей)
     */
    public RemoveByIdCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * @return имя команды
     */
    @Override
    public String getName() {
        return "remove_by_id";
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "Удалить элемент из коллекции по его id";
    }

    /**
     * Удаляет элемент по переданному id.
     *
     * @param args аргументы команды, где первый элемент - id
     */
    @Override
    public void execute(String[] args){
        if (args == null || args.length == 0 || args[0].isBlank()){ // проверка есть ли аргумент. Данная команда требует аргумент - id элемента, который нужно удалить
            System.out.println("Ошибка : Команда remove_by_id требует аргумент: id элемента.");
            System.out.println("Пример: remove_by_id 5");
            return;
        }

        long id;
        try{
            id = Long.parseLong(args[0].trim());
        } catch (NumberFormatException e){
            System.out.println("id должен быть целым числом."); // проверка, что id - это целое число
            return;
        }
        boolean delete = collectionManager.removeById(id); // создаем "флаг" для проверки удалился ли элемент или нет. При True -> удалился при False -> элемент не найден
        if (delete){
            System.out.println("Элемент с id = " + id + " успешно удалён.");
        } else {
            System.out.println("Элемент с id = " + id + " не найден. Удаление не выполнено.");
        }
    }
}
