// Команда для отображения всех элементов коллекции в строковом представлении

package commands;

import managers.CollectionManager;
import data.StudyGroup;

public class ShowCommand implements Command{

    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName(){
        return "show";
    }

    @Override
    public String getDescription(){
        return "Вывести все элементы коллекции в строковом представлении";
    }

    @Override
    public void execute(String[] args){
        var all = collectionManager.getAll();
        if (all.isEmpty()){
            System.out.println("Коллекция пуста."); //  Если коллекция пуста, выводится сообщение "Коллекция пуста."
        } else {
            for (StudyGroup group : all) {
                System.out.println(group); // Для каждого элемента коллекции вызывается его метод toString(). Он должен переопределяться в классе StudyGroup, чтобы возвращать строковое представление объекта. Результат выводится на консоль.
            }
        }
    }
}
