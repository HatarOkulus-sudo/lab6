// Команда для обновления элемента коллекции по его id.

package commands;

import data.StudyGroup;
import managers.CollectionManager;
import managers.InputManager;

/**
 * Команда обновления элемента коллекции по идентификатору.
 */
public class UpdateIdCommand implements Command {

    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    /**
     * Создает команду update.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер ввода
     */
    public UpdateIdCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * @return имя команды
     */
    @Override
    public String getName() {
        return "update id";
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "Обновить элемент по id";
    }

    /**
     * Обновляет элемент по id на значения, считанные из консоли.
     *
     * @param args аргументы команды, где первый элемент - id
     */
    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0 || args[0].isBlank()) {
            System.out.println("Ошибка : Команда update требует аргумент: id элемента.");
            System.out.println("Пример: update 5");
            return;
        }

        long id;
        try {
            id = Long.parseLong(args[0].trim());
        } catch (NumberFormatException e) {
            System.out.println("id должен быть целым числом.");
            return;
        }

        System.out.println("Введите новые значения для элемента с id = " + id + ":");
        StudyGroup newValueWithoutId = inputManager.readStudyGroup();


        boolean updated = collectionManager.updateById(id, newValueWithoutId);

        if (updated) {
            System.out.println("Элемент с id = " + id + " успешно обновлён.");
        } else {
            System.out.println("Элемент с id = " + id + " не найден. Обновление не выполнено.");
        }
    }
}

//логика одинаковая с командой RemoveByIdCommand. Только операция removeById заменена на updateById, а также добавлено считывание новых данных для элемента. В остальном же проверка аргумента и его парсинг идентичны.
