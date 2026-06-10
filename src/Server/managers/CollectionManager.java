package Server.managers;

import Common.data.FormOfEducation;
import Common.data.Semester;
import Common.data.StudyGroup;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {
    private final LinkedList<StudyGroup> collection = new LinkedList<>();
    private final ZonedDateTime initializationDate = ZonedDateTime.now();
    private long currentId = 1;

    public LinkedList<StudyGroup> getCollection() {
        return collection;
    }

    public List<StudyGroup> getAll() {
        return new LinkedList<>(collection);
    }

    public int size() {
        return collection.size();
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public String getInfo() {
        return "Тип: " + collection.getClass().getName() +
                ", дата инициализации: " + initializationDate +
                ", количество элементов: " + collection.size();
    }

    public long generateId() {
        return currentId++;
    }

    private StudyGroup buildServerStudyGroup(long id, StudyGroup fromClient, ZonedDateTime creationDate) {
        return new StudyGroup(
                id,
                fromClient.getName(),
                fromClient.getCoordinates(),
                creationDate,
                fromClient.getStudentsCount(),
                fromClient.getShouldBeExpelled(),
                fromClient.getFormOfEducation(),
                fromClient.getSemesterEnum(),
                fromClient.getGroupAdmin()
        );
    }

    public StudyGroup add(StudyGroup fromClient) {
        Objects.requireNonNull(fromClient, "StudyGroup is null");
        long id = generateId();
        ZonedDateTime now = ZonedDateTime.now();
        StudyGroup serverGroup = buildServerStudyGroup(id, fromClient, now);
        collection.add(serverGroup);
        return serverGroup;
    }

    public StudyGroup addFromFile(StudyGroup groupFromFile) {
        Objects.requireNonNull(groupFromFile, "StudyGroup is null");
        collection.add(groupFromFile);
        if (groupFromFile.getId() >= currentId) {
            currentId = groupFromFile.getId() + 1;
        }
        return groupFromFile;
    }

    public boolean updateById(long id, StudyGroup newValueFromClient) {
        Objects.requireNonNull(newValueFromClient, "newValue is null");

        for (int i = 0; i < collection.size(); i++) {
            StudyGroup current = collection.get(i);
            if (current.getId() == id) {
                ZonedDateTime now = ZonedDateTime.now();
                StudyGroup serverGroup = buildServerStudyGroup(id, newValueFromClient, now);
                collection.set(i, serverGroup);
                return true;
            }
        }
        return false;
    }

    public boolean removeById(long id) {
        return collection.removeIf(g -> g.getId() == id);
    }

    public void clear() {
        collection.clear();
    }

    public void shuffle() {
        Collections.shuffle(collection);
    }

    public List<StudyGroup> getAllSortedByName() {
        return collection.stream()
                .sorted(Comparator.comparing(StudyGroup::getName, String.CASE_INSENSITIVE_ORDER))
                .sorted(Comparator.comparing(StudyGroup::getName, String.CASE_INSENSITIVE_ORDER))
                .sorted(Comparator.comparing(StudyGroup::getName, String.CASE_INSENSITIVE_ORDER))
                .sorted(Comparator.comparing(StudyGroup::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public boolean addIfMax(StudyGroup candidate) {
        Objects.requireNonNull(candidate, "candidate is null");

        Optional<StudyGroup> max = collection.stream().max(StudyGroup::compareTo);
        if (max.isEmpty() || candidate.compareTo(max.get()) > 0) {
            add(candidate);
            return true;
        }
        return false;
    }

    public int removeGreater(StudyGroup pivot) {
        Objects.requireNonNull(pivot, "pivot is null");

        int before = collection.size();

        List<StudyGroup> kept = collection.stream()
                .filter(el -> el.compareTo(pivot) <= 0)
                .collect(Collectors.toList());

        collection.clear();
        collection.addAll(kept);

        return before - collection.size();
    }

    public int removeAllBySemesterEnum(Semester semester) {
        Objects.requireNonNull(semester, "semester is null");

        int before = collection.size();

        List<StudyGroup> kept = collection.stream()
                .filter(el -> !semester.equals(el.getSemesterEnum()))
                .collect(Collectors.toList());

        collection.clear();
        collection.addAll(kept);

        return before - collection.size();
    }

    public Set<FormOfEducation> getUniqueFormOfEducation() {
        return collection.stream()
                .map(StudyGroup::getFormOfEducation)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public List<Semester> getSemesterEnumsAscending() {
        return collection.stream()
                .map(StudyGroup::getSemesterEnum)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<StudyGroup> snapshot() {
        return Collections.unmodifiableList(new ArrayList<>(collection));
    }
}