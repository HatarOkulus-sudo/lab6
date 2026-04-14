package data;

import java.time.ZonedDateTime;

/**
 * Класс, представляющий учебную группу.
 */
public class StudyGroup {
    /**
     * Уникальный идентификатор группы. Должен быть больше 0.
     */
    private long id;

    /**
     * Название группы. Не может быть null или пустым.
     */
    private String name;

    /**
     * Координаты группы. Не могут быть null.
     */
    private Coordinates coordinates;

    /**
     * Дата создания группы. Генерируется автоматически, не может быть null.
     */
    private java.time.ZonedDateTime creationDate;

    /**
     * Количество студентов в группе. Должно быть больше 0.
     */
    private long studentsCount;

    /**
     * Количество студентов, которые должны быть исключены. Должно быть больше 0.
     */
    private int shouldBeExpelled;

    /**
     * Форма обучения. Не может быть null.
     */
    private FormOfEducation formOfEducation;

    /**
     * Семестр. Не может быть null.
     */
    private Semester semesterEnum;

    /**
     * Администратор группы. Не может быть null.
     */
    private Person groupAdmin;

    /**
     * Конструктор учебной группы.
     *
     * @param id Уникальный идентификатор группы.
     * @param name Название группы.
     * @param coordinates Координаты группы.
     * @param creationDate Дата создания группы.
     * @param studentsCount Количество студентов в группе.
     * @param shouldBeExpelled Количество студентов, которые должны быть исключены.
     * @param formOfEducation Форма обучения.
     * @param semesterEnum Семестр.
     * @param groupAdmin Администратор группы.
     */
    public StudyGroup(long id, String name, Coordinates coordinates, ZonedDateTime creationDate, long studentsCount, int shouldBeExpelled, FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin){

        // Валидация входных данных
        if (id <= 0){
            throw new IllegalArgumentException("id must be greater than 0");
        }
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException("name is null or empty");
        }
        if (coordinates == null){
            throw new IllegalArgumentException("coordinates is null");
        }
        if (creationDate == null){
            throw new IllegalArgumentException("creationDate is null");
        }
        if (studentsCount <= 0){
            throw new IllegalArgumentException("studentsCount must be greater than 0");
        }
        if (shouldBeExpelled <= 0){
            throw new IllegalArgumentException("shouldBeExpelled must be greater than 0");
        }
        if (formOfEducation == null){
            throw new IllegalArgumentException("formOfEducation is null");
        }
        if (semesterEnum == null){
            throw new IllegalArgumentException("semesterEnum mustn't be null");
        }
        if (groupAdmin == null){
            throw new IllegalArgumentException("groupAdmin mustn't be null");
        }
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.shouldBeExpelled = shouldBeExpelled;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public ZonedDateTime getCreationDate(){
        return creationDate;
    }

    public long getStudentsCount(){
        return studentsCount;
    }

    public int getShouldBeExpelled(){
        return shouldBeExpelled;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public final void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setStudentsCount(long studentsCount) {
        this.studentsCount = studentsCount;
    }

    public void setShouldBeExpelled(int shouldBeExpelled) {
        this.shouldBeExpelled = shouldBeExpelled;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    // Переопределение метода toString для удобного отображения информации о группе
    @Override
    public String toString(){
        return "StudyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", studentsCount=" + studentsCount +
                ", shouldBeExpelled=" + shouldBeExpelled +
                ", formOfEducation=" + formOfEducation +
                ", semesterEnum=" + semesterEnum +
                ", groupAdmin=" + groupAdmin +
                '}';
    }
}
