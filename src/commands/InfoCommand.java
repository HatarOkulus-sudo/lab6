// Команда для вывода информации о коллекции

package commands;

import managers.CollectionManager;

/**
 * Команда вывода информации о коллекции.
 */
public class InfoCommand implements Command {

    private final CollectionManager collectionManager;

    /**
     * Создает команду info.
     *
     * @param collectionManager менеджер коллекции
     */
    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * @return имя команды
     */
    @Override
    public String getName() {
        return "info";
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "Вывести информацию о коллекции";
    }

    /**
     * Выводит служебную информацию о текущей коллекции.
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args){
            System.out.println(collectionManager.getInfo());
    }
}