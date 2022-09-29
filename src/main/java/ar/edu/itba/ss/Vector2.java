package ar.edu.itba.ss;

public class Vector2 {
    private double x,y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double innerProduct(Vector2 v) {
        return this.x * v.getX() + this.y * v.getY();
    }

    public Vector2 scalarProduct(double k) {
        return new Vector2(x * k, y * k);
    }

    public Vector2 substract(Vector2 v) {
        return new Vector2(getX() - v.getX(), getY() - v.getY());
    }

    public Vector2 sum(Vector2 v) {
        return new Vector2(x + v.getX(), y + getY());
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
