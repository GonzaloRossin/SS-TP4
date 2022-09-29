package ar.edu.itba.ss;

import java.util.Objects;

public class Particle {
    private double rc;
    private Vector2 actualR, lastR = new Vector2(0,0);
    private Vector2 actualV, lastV = new Vector2(0,0);
    private Vector2 actualForce, lastForce, nextForce;
    private double mass;
    private int id;
    private int color;
    private double gamma;
    private double K;

    public Particle(int id, double mass, Vector2 r, double gamma, double K, int color) {
        this.actualR = r;
        this.actualV = new Vector2(-r.getX() * (gamma / (2 * mass)), 0);
        this.id = id;
        this.mass = mass;
        this.color = color;
        this.gamma = gamma;
        this.K = K;
    }

    Particle(double x, double y) {
        this.actualR = new Vector2(x, y);
    }

    public Vector2 calculateForce(Vector2 R, Vector2 V) {
        return new Vector2(-K * R.getX() - gamma * V.getX(), 0);
    }

    public void applyEulerModified(double step) {
        lastForce = actualForce;
        actualForce = calculateForce(actualR, actualV);
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
        lastForce = actualForce;
        actualForce = calculateForce(actualR, actualV);

        Vector2 nextR = new Vector2(2 * actualR.getX() - lastR.getX() + (step * step / mass) * actualForce.getX(), 0);
        actualV.setX((nextR.getX() - lastR.getX()) / (2 * step));

        lastR = actualR;
        actualR = nextR;
    }

    public void applyBeeman(double step) {
        lastForce = actualForce;
        actualForce = calculateForce(actualR, actualV);

        Vector2 actualA = actualForce.scalarProduct(1.0/mass);
        Vector2 lastA = lastForce.scalarProduct(1.0/mass);

        Vector2 rTerm1 = actualV.scalarProduct(step);
        Vector2 rTerm2 = actualA.scalarProduct((2.0/3.0) * step * step);
        Vector2 rTerm3 = lastA.scalarProduct((1.0/6.0) * step * step);

        // Stores in actualR the position r(t + step)
        actualR = actualR.sum(rTerm1).sum(rTerm2).substract(rTerm3);

        // Calculates predicted velocity
        Vector2 predictV = actualV.sum(actualA.scalarProduct((3.0/2.0) * step)).substract(lastA.scalarProduct((1.0/2.0) * step));

        // Calculate force in t + step
        nextForce = calculateForce(actualR, predictV);
        Vector2 nextA = nextForce.scalarProduct(1.0/mass);

        // Calculate corrected velocity
        Vector2 term1 = nextA.scalarProduct((1.0/3.0) * step);
        Vector2 term2 = actualA.scalarProduct((5.0/6.0) * step);
        Vector2 term3 = lastA.scalarProduct((1.0/6.0) * step);

        actualV = actualV.sum(term1).sum(term2).substract(term3);
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
