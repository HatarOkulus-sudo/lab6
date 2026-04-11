// Команда для сохранения коллекции в файл.

package commands;

import data.StudyGroup;
import managers.CollectionManager;
import managers.FileManager;

import java.util.LinkedList;

public class SaveCommand implements Command{

    private final CollectionManager collectionManager;
    private final FileManager fileManager;

    public SaveCommand(CollectionManager collectionManager, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "Сохранить коллекцию в файл";
    }

    @Override
    public void execute(String[] args){
        if (args != null && args.length >0 && !args[0].isBlank()){
            System.out.println("Ошибка : Команда save не принимает аргументы.");
            System.out.println("Пример: save"); // Команда не должна принимать аргументы.
            return;
        }

        LinkedList<StudyGroup> collection = collectionManager.getCollection(); // Коллекция для сохранения
        try{
            fileManager.save(collectionManager.getCollection()); // Попытка сохранить коллекцию в файл
            System.out.println("Коллекция успешно сохранена в файл.");
        } catch (Exception e){ // Ловим любые исключения, которые могут возникнуть при сохранении
            System.out.println("Ошибка при сохранении коллекции: " + e.getMessage());  // Выводим сообщение об ошибке, если сохранение не удалось
        }
    }
}
