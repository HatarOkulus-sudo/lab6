// Команда для добавления нового элемента в коллекцию

package commands;

import data.StudyGroup;
import managers.CollectionManager;
import managers.InputManager;

public class AddCommand implements Command{

    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    public AddCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    @Override
    public String getName(){
        return "add";
    }

    @Override
    public String getDescription(){
        return "Добавить новый элемент в коллекцию";
    }

    @Override
    public void execute(String[] args){
        StudyGroup groupWithoutId = inputManager.readStudyGroup(); // считывать данные для нового элемента
        StudyGroup addedGroup = collectionManager.add(groupWithoutId); // добавлять элменет в коллекцию
        System.out.println("Элемент добавлен с id: " + addedGroup.getId()); // выводить id нового элемента
    }
}
