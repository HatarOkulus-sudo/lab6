// Команда для вывода информации о коллекции

package commands;

import managers.CollectionManager;

public class InfoCommand implements Command {

    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Вывести информацию о коллекции";
    }

    @Override
    public void execute(String[] args){
            System.out.println(collectionManager.getInfo());
    } // Метод execute вызывает метод getInfo() у collectionManager, который возвращает строку с информацией о коллекции, и выводит эту информацию на консоль.
}