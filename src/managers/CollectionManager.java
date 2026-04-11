// Класс для управления коллекцией. Содержит методы для работы с коллекцией, которые вызываются из команд. Также отвечает за генерацию id для новых элементов и хранение даты инициализации коллекции.

package managers;

import data.StudyGroup;

import java.time.ZonedDateTime;
import java.util.*;

public class CollectionManager {
    private final LinkedList<StudyGroup> collection = new LinkedList<>();
    private final ZonedDateTime initializationDate = ZonedDateTime.now();
    private long currentId = 1L;

    public LinkedList<StudyGroup> getCollection() { // метод для получения коллекции, который понадобиться для сохранения в файл
        return collection;
    }

    public ZonedDateTime getInitializationDate() { // метод для получения даты инициализации, который понадобиться для отображения информации о коллекции
        return initializationDate;
    }

    public long generateId() { // метод для генерации id для новых элементов
        return currentId++;
    }
    public String getInfo() { // метод для получения информации о коллекции, который понадобиться для отображения информации о коллекции
        return "Тип: " + collection.getClass().getName() +
                ", дата инициализации: " + initializationDate +
                ", количество элементов: " + collection.size();
    }

    public List<StudyGroup> getAll() { // метод для получения всех элементов коллекции, который понадобиться для отображения всех элементов коллекции
        return new LinkedList<>(collection);
    }

    public StudyGroup add(StudyGroup groupWithoutId) { // метод для добавления нового элемента в коллекцию, который понадобиться для команды add
        long id = generateId();
        groupWithoutId.setId(id);
        collection.add(groupWithoutId);
        return groupWithoutId;
    }


    public StudyGroup addFromFile(StudyGroup groupFromFile) { // метод для добавления элемента из файла в коллекцию, который понадобиться для загрузки коллекции из файла.
        collection.add(groupFromFile);
        if (groupFromFile.getId() >= currentId) {
            currentId = groupFromFile.getId() + 1;
        }
        return groupFromFile;
    }

    public boolean updateById(long id, StudyGroup newValueWithoutId) { // метод для обновления элемента по id, который понадобиться для команды update
        for (int i = 0; i < collection.size(); i++) {
            StudyGroup current = collection.get(i);
            if (current.getId() == id) {
                newValueWithoutId.setId(id);
                collection.set(i, newValueWithoutId);
                return true;
            }
        }
        return false;
    }

    public boolean removeById(long id) { // метод для удаления элемента по id, который понадобиться для команды remove_by_id
        return collection.removeIf(g -> g.getId() == id); // удаляет элемент, если id совпало, и возвращает true. Если элемент не найден, возвращает false.
    }

    public void clear() { // метод для очистки коллекции, который понадобиться для команды clear
        collection.clear();
    }

    public boolean removeLast() { // метод для удаления последнего элемента коллекции, который понадобиться для команды remove_last. Возвращает true, если элемент удалён, и false, если коллекция была пустой.
        if (collection.isEmpty()) {
            return false;
        }
        collection.removeLast();
        return true;
    }

    public void shuffle() { // метод для перемешивания элементов коллекции, который понадобиться для команды shuffle
        Collections.shuffle(collection);
    }

    public int removeAllByStudentsCount(long studentsCount) { // метод для удаления всех элементов коллекции, у которых studentsCount равно заданному, который понадобиться для команды remove_all_by_students_count. Возвращает количество удалённых элементов.
        int before = collection.size();
        collection.removeIf(g -> g.getStudentsCount() == studentsCount);
        return before - collection.size(); // сколько удалили
    }

    public Optional<StudyGroup> maxByShouldBeExpelled() { // метод для получения элемента с максимальным значением поля shouldBeExpelled, который понадобиться для команды max_by_should_be_expelled. Возвращает Optional, который может быть пустым, если коллекция пуста.
        return collection.stream().max(Comparator.comparingInt(StudyGroup::getShouldBeExpelled));
    }

    public List<StudyGroup> filterContainsName(String substring) { // метод для получения всех элементов коллекции, у которых поле name содержит заданную подстроку, который понадобиться для команды filter_contains_name. Возвращает список найденных элементов. Поиск регистронезависимый.
        String needle = substring.toLowerCase();
        List<StudyGroup> result = new ArrayList<>();
        for (StudyGroup g : collection) {
            if (g.getName().toLowerCase().contains(needle)) {
                result.add(g);
            }
        }
        return result; // возвращаем список найденных элементов. Если ничего не найдено, вернётся пустой список.
    }
}
