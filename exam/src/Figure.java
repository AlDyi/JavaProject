public class Figure {
    private Integer x;
    private Integer y;
    private Integer height;
    private Integer width;

    public Figure (Integer x, Integer y, Integer height, Integer width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setHeight(Integer height) {
        height = height;
    }

    public void setWidth(Integer width) {
        width = width;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
