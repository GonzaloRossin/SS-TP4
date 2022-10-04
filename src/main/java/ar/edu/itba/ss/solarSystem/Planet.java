package ar.edu.itba.ss.solarSystem;

import ar.edu.itba.ss.Vector2;

import java.util.List;

public class Planet {
    private Vector2 actualR, lastR = new Vector2(0,0), actualV, actualForce;
    private double mass;
    private int color;
    private String name;
    private double G = 0.00000000006693;

    public Planet(Vector2 actualR, Vector2 actualV, PlanetsInfo planetsInfo) {
        this.actualR = actualR;
        this.actualV = actualV;
        this.mass = planetsInfo.getMass();
        this.color = planetsInfo.getColor();
        this.name = planetsInfo.getName();
    }

    public void init(Planet p) {
        actualForce = calculateGravity(p);
    }

    private Vector2 calculateGravity(Planet p) {
        Vector2 aux = p.actualR.substract(actualR);
        double distanceSquared = aux.innerProduct(aux);

        double fModule = G * mass * p.mass / distanceSquared;
        Vector2 versor = aux.normalize();

        return versor.scalarProduct(fModule);
//        return new Vector2(Math.cos(ang), Math.sin(ang)).scalarProduct(fModule).scalarProduct(-1);
    }

    public Vector2 calculateForceSum(List<Planet> planets) {
        Vector2 newForce = new Vector2(0,0);
        for (Planet p: planets) {
            if (!p.equals(this)) {
                newForce = newForce.sum(calculateGravity(p));
            }
        }
        return newForce;
    }

    public void applyEulerModified(List<Planet> planets, double step) {
        Vector2 lastForce = actualForce;
        actualForce = calculateForceSum(planets);

        Vector2 a = actualForce.scalarProduct(1/mass);

        Vector2 lastV = actualV;
        Vector2 term = actualForce.scalarProduct(step / mass);
        actualV = actualV.sum(term);

        lastR = actualR;

        Vector2 term1 = actualV.scalarProduct(step);
        Vector2 term2 = actualForce.scalarProduct(step * step / (2 * mass));
        actualR = actualR.sum(term1).sum(term2);
    }

    public void simpleUpdate(List<Planet> planets, double step) {
        Vector2 force = calculateForceSum(planets);
        Vector2 accel = force.scalarProduct(1/mass);

        actualV = actualV.sum(accel.scalarProduct(step));
        actualR = actualR.sum(actualV.scalarProduct(step));
    }

    public Double applyBeeman(List<Planet> planets, double step) {
        Vector2 lastForce = actualForce;
        actualForce = calculateForceSum(planets);

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
        Vector2 nextForce = calculateForceSum(planets);
        Vector2 nextA = nextForce.scalarProduct(1.0/mass);

        // Calculate corrected velocity
        Vector2 term1 = nextA.scalarProduct((1.0/3.0) * step);
        Vector2 term2 = actualA.scalarProduct((5.0/6.0) * step);
        Vector2 term3 = lastA.scalarProduct((1.0/6.0) * step);

        actualV = actualV.sum(term1).sum(term2).substract(term3);
        return actualR.getX();
    }

    public String toXYZ() {
        return String.format("%f %f %d\n", actualR.getX(), actualR.getY(), color);
    }

    public String getName() {
        return name;
    }

    public Vector2 getActualR() {
        return actualR;
    }

    public Vector2 getActualV() {
        return actualV;
    }
}
