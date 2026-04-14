// Команда для отображения всех элементов коллекции в строковом представлении

package commands;

import managers.CollectionManager;
import data.StudyGroup;

/**
 * Команда вывода всех элементов коллекции.
 */
public class ShowCommand implements Command{

    private final CollectionManager collectionManager;

    /**
     * Создает команду show.
     *
     * @param collectionManager менеджер коллекции
     */
    public ShowCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    /**
     * @return имя команды
     */
    @Override
    public String getName(){
        return "show";
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription(){
        return "Вывести все элементы коллекции в строковом представлении";
    }

    /**
     * Выводит содержимое коллекции в консоль.
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args){
        var all = collectionManager.getAll();
        if (all.isEmpty()){
            System.out.println("Коллекция пуста.");
        } else {
            for (StudyGroup group : all) {
                System.out.println(group);
            }
        }
    }
}
