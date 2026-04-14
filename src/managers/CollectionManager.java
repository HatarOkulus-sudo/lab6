// Класс для управления коллекцией. Содержит методы для работы с коллекцией, которые вызываются из команд. Также отвечает за генерацию id для новых элементов и хранение даты инициализации коллекции.

package managers;

import data.StudyGroup;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * Менеджер коллекции учебных групп.
 */
public class CollectionManager {
    private final LinkedList<StudyGroup> collection = new LinkedList<>();
    private final ZonedDateTime initializationDate = ZonedDateTime.now();
    private long currentId = 1;

    /**
     * @return текущая внутренняя коллекция
     */
    public LinkedList<StudyGroup> getCollection() {
        return collection;
    }

    /**
     * @return дата инициализации менеджера
     */
    public ZonedDateTime getInitializationDate() {
        return initializationDate;
    }

    /**
     * Генерирует следующий идентификатор.
     *
     * @return новый id
     */
    public long generateId() {
        return currentId++;
    }

    /**
     * Формирует строку с метаданными коллекции.
     *
     * @return информация о коллекции
     */
    public String getInfo() {
        return "Тип: " + collection.getClass().getName() +
                ", дата инициализации: " + initializationDate +
                ", количество элементов: " + collection.size();
    }

    /**
     * @return копия всех элементов коллекции
     */
    public List<StudyGroup> getAll() {
        return new LinkedList<>(collection);
    }

    /**
     * Добавляет новый элемент с автоматически сгенерированным id.
     *
     * @param groupWithoutId элемент без id
     * @return добавленный элемент
     */
    public StudyGroup add(StudyGroup groupWithoutId) {
        long id = generateId();
        groupWithoutId.setId(id);
        collection.add(groupWithoutId);
        return groupWithoutId;
    }

    /**
     * Добавляет элемент, считанный из файла, сохраняя его id.
     *
     * @param groupFromFile элемент из файла
     * @return добавленный элемент
     */
    public StudyGroup addFromFile(StudyGroup groupFromFile) {
        collection.add(groupFromFile);
        if (groupFromFile.getId() >= currentId) {
            currentId = groupFromFile.getId() + 1;
        }
        return groupFromFile;
    }

    /**
     * Обновляет элемент по id.
     *
     * @param id идентификатор изменяемого элемента
     * @param newValueWithoutId новые данные
     * @return true, если элемент найден и обновлен
     */
    public boolean updateById(long id, StudyGroup newValueWithoutId) {
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

    /**
     * Удаляет элемент по id.
     *
     * @param id идентификатор элемента
     * @return true, если элемент удален
     */
    public boolean removeById(long id) {
        return collection.removeIf(g -> g.getId() == id);
    }

    /**
     * Полностью очищает коллекцию.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Удаляет последний элемент.
     *
     * @return true, если удаление выполнено
     */
    public boolean removeLast() {
        if (collection.isEmpty()) {
            return false;
        }
        collection.removeLast();
        return true;
    }

    /**
     * Перемешивает порядок элементов коллекции.
     */
    public void shuffle() {
        Collections.shuffle(collection);
    }

    /**
     * Удаляет все элементы с заданным studentsCount.
     *
     * @param studentsCount значение studentsCount
     * @return количество удаленных элементов
     */
    public int removeAllByStudentsCount(long studentsCount) {
        int before = collection.size();
        collection.removeIf(g -> g.getStudentsCount() == studentsCount);
        return before - collection.size();
    }

    /**
     * Находит элемент с максимальным значением shouldBeExpelled.
     *
     * @return найденный элемент или null, если коллекция пуста
     */
    public StudyGroup getMaxByShouldBeExpelled() {
        if (collection.isEmpty()) {
            return null;
        }

        StudyGroup maxGroup = null;
        for (StudyGroup group : collection) {
            if (maxGroup == null ||
                    group.getShouldBeExpelled() > maxGroup.getShouldBeExpelled()) {
                maxGroup = group;
            }
        }
        return maxGroup;
    }
}
