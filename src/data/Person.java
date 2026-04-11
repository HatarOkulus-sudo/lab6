package data;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long weight; //Поле не может быть null, Значение поля должно быть больше 0
    private String passportID; //Поле не может быть null
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null

    public Person(
            String name,
            Long weight,
            String passportID,
            Color eyeColor,
            Color hairColor
    ) {
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

    public String getName(){
        return name;
    }

    public Long getWeight(){
        return weight;
    }

    public String getPassportID(){
        return passportID;
    }

    public Color getEyeColor(){
        return eyeColor;
    }

    public Color getHairColor(){
        return hairColor;
    }

    // Метод для получения строки с данными о человеке в формате "name: [name], weight: [weight], passportID: [passportID], eyeColor: [eyeColor], hairColor: [hairColor]"
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



