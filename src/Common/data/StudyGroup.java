package Common.data;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

public class StudyGroup implements Comparable<StudyGroup>, Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;

    private Coordinates coordinates;

    private java.time.ZonedDateTime creationDate;
    private long studentsCount;

    private int shouldBeExpelled;

    private FormOfEducation formOfEducation;

    private Semester semesterEnum;

    private Person groupAdmin;
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

//    public void setName(String name){
//        this.name = name;
//    }
//
//    public void setCoordinates(Coordinates coordinates) {
//        this.coordinates = coordinates;
//    }
//
//    public void setStudentsCount(long studentsCount) {
//        this.studentsCount = studentsCount;
//    }
//
//    public void setShouldBeExpelled(int shouldBeExpelled) {
//        this.shouldBeExpelled = shouldBeExpelled;
//    }

    @Override
    public int compareTo(StudyGroup other) {
        Objects.requireNonNull(other, "other StudyGroup is null");
        return Long.compare(this.id, other.id);
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
