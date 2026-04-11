package data;

import java.time.ZonedDateTime;

public class StudyGroup {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount; //Значение поля должно быть больше 0
    private int shouldBeExpelled; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле не может быть null

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
        this.creationDate = ZonedDateTime.now();
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
