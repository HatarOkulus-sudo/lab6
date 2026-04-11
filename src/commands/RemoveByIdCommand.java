// удаляет элемент из коллекции по его id

package commands;

import managers.CollectionManager;
import managers.InputManager;

public class RemoveByIdCommand implements Command{

    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    public RemoveByIdCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }
    @Override
    public String getDescription() {
        return "Удалить элемент из коллекции по его id";
    }

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
