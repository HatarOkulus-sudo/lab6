// Команда для добавления нового элемента в коллекцию

package commands;

import data.StudyGroup;
import managers.CollectionManager;
import managers.InputManager;

/**
 * Команда добавления нового элемента в коллекцию.
 */
public class AddCommand implements Command{

    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    /**
     * Создает команду add.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager менеджер пользовательского ввода
     */
    public AddCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * @return имя команды
     */
    @Override
    public String getName(){
        return "add";
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription(){
        return "Добавить новый элемент в коллекцию";
    }

    /**
     * Считывает поля нового объекта и добавляет его в коллекцию.
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args){
        StudyGroup groupWithoutId = inputManager.readStudyGroup();// считывать данные для нового элемента
        StudyGroup addedGroup = collectionManager.add(groupWithoutId);// добавлять элменет в коллекцию
        System.out.println("Элемент добавлен с id: " + addedGroup.getId()); // выводить id нового элемента
    }

}
