package data;

/**
 * Координаты учебной группы.
 */
public class Coordinates {
    private Long x; //Поле не может быть null
    private long y;

    /**
     * Создает координаты.
     *
     * @param x координата X, не может быть null
     * @param y координата Y
     */
    public Coordinates(Long x, long y){
        if (x == null){
            throw new IllegalArgumentException("x cannot be null");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * @return координата X
     */
    public Long getX(){
        return x;
    }

    /**
     * @return координата Y
     */
    public long getY(){
        return y;
    }

    /**
     * Возвращает строковое представление координат.
     *
     * @return строка с полями объекта
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
