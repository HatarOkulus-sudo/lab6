// Команда для сохранения коллекции в файл.

package commands;

import data.StudyGroup;
import managers.CollectionManager;
import managers.FileManager;

/**
 * Команда сохранения коллекции в файл.
 */
public class SaveCommand implements Command{

    private final CollectionManager collectionManager;
    private final FileManager fileManager;

    /**
     * Создает команду save.
     *
     * @param collectionManager менеджер коллекции
     * @param fileManager файловый менеджер
     */
    public SaveCommand(CollectionManager collectionManager, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    /**
     * @return имя команды
     */
    @Override
    public String getName() {
        return "save";
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "Сохранить коллекцию в файл";
    }

    /**
     * Сохраняет текущую коллекцию в файл.
     *
     * @param args аргументы команды
     */
    @Override
    public void execute(String[] args){
        if (args != null && args.length >0 && !args[0].isBlank()){
            System.out.println("Ошибка : Команда save не принимает аргументы.");
            System.out.println("Пример: save"); // Команда не должна принимать аргументы.
            return;
        }

        try{
            fileManager.save(collectionManager.getCollection()); // Попытка сохранить коллекцию в файл
            System.out.println("Коллекция успешно сохранена в файл.");
        } catch (Exception e){ // Ловим любые исключения, которые могут возникнуть при сохранении
            System.out.println("Ошибка при сохранении коллекции: " + e.getMessage());  // Выводим сообщение об ошибке, если сохранение не удалось
        }
    }
}
