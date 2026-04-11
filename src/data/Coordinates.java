package data;

public class Coordinates {
    private Long x; //Поле не может быть null
    private long y;

    public Coordinates(
            Long x,
            long y
    ){
        if (x == null){
            throw new IllegalArgumentException("x cannot be null");
        }
        this.x = x;
        this.y = y;
    }

    public Long getX(){
        return x;
    }

    public long getY(){
        return y;
    }


    // Метод для получения строки с координатами в формате "x: [x], y: [y]"
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

