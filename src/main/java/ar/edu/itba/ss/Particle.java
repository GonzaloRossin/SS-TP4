package ar.edu.itba.ss;

import java.util.Objects;

public class Particle {
    private double rc;
    private Vector2 actualR, lastR = new Vector2(0,0);
    private Vector2 actualV, lastV = new Vector2(0,0);
    private Vector2 actualForce;
    private double mass;
    private int id;
    private int color;
    private double gamma;

    public Particle(int id, double mass, Vector2 r, double gamma) {
        this.actualR = r;
        this.actualV = new Vector2(-r.getX() * (gamma / (2 * mass)), 0);
        this.id = id;
        this.mass = mass;
        this.color = 0;
        this.gamma = gamma;
    }

    Particle(double x, double y) {
        this.actualR = new Vector2(x, y);
    }

    public void applyEulerModified(double step) {
        // Only for X, it is a linear problem
        lastV.setX(actualV.getX());
        actualV.setX(actualV.getX() + (step / mass) * actualForce.getX());

        lastR.setX(actualR.getX());
        actualR.setX(actualR.getX() + step * actualV.getX() + step * step * actualForce.getX() / (2 * mass));
    }

    public void updateR(double t) {
        actualR.setX(getActualR().getX() + getActualV().getX() * t);
        actualR.setY(getActualR().getY() + getActualV().getY() * t);
    }

    public void applyVerlet(double step) {
        Vector2 nextR = new Vector2(2 * actualR.getX() - lastR.getX() + (step * step / mass) * actualForce.getX(), 0);

        actualV.setX((nextR.getX() - lastR.getX()) / (2 * step));

        lastR = actualR;
        actualR = nextR;


    }

    public void setActualR(Vector2 actualR) {
        this.actualR = actualR;
    }

    public double getMass() {
        return mass;
    }

    @Override
    public String toString() {
        return String.format("Id %d R[%.2f, %.2f] V[%.2f, %.2f]", id, actualR.getX(), actualR.getY(), actualV.getX(), actualV.getY());
    }

    public String toXYZ() {
        return String.format("%f %f %d\n", actualR.getX(), actualR.getY(), color);
    }

    public Vector2 getActualR() {
        return actualR;
    }

    public Vector2 getActualV() {
        return actualV;
    }

    public double getRc() {
        return rc;
    }

    public void setRc(double rc) {
        this.rc = rc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setActualForce(Vector2 actualForce) {
        this.actualForce = actualForce;
    }
}
