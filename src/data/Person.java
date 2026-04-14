package data;

/**
 * Класс, описывающий администратора учебной группы.
 */
public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long weight; //Поле не может быть null, Значение поля должно быть больше 0
    private String passportID; //Поле не может быть null
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null

    /**
     * Создает объект администратора группы.
     *
     * @param name имя
     * @param weight вес
     * @param passportID номер паспорта
     * @param eyeColor цвет глаз
     * @param hairColor цвет волос
     */
    public Person(String name, Long weight, String passportID, Color eyeColor, Color hairColor) {
        // Валидация входных данных
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name must not be empty");
        }
        if (weight == null || weight <= 0) {
            throw new IllegalArgumentException("weight must be > 0");
        }
        if (passportID == null){
            throw new IllegalArgumentException("passportID must not be null");
        }
        if (eyeColor == null){
            throw new IllegalArgumentException("eyeColor must not be null");
        }


        this.name = name;
        this.weight = weight;
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }

    /**
     * @return имя
     */
    public String getName(){
        return name;
    }

    /**
     * @return вес
     */
    public Long getWeight(){
        return weight;
    }

    /**
     * @return паспортный идентификатор
     */
    public String getPassportID(){
        return passportID;
    }

    /**
     * @return цвет глаз
     */
    public Color getEyeColor(){
        return eyeColor;
    }

    /**
     * @return цвет волос
     */
    public Color getHairColor(){
        return hairColor;
    }

    /**
     * Возвращает строковое представление администратора.
     *
     * @return строка с полями объекта
     */
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", passportID='" + passportID + '\'' +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                '}';
    }
}
