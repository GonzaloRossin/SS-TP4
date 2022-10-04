package ar.edu.itba.ss.solarSystem;

public enum PlanetsInfo {
    EARTH(5.97219E24, 60),
    SUN(1.988500E30, 75),
    VENUS(4.8685E24, 0);

    final double mass;
    final int color;
    PlanetsInfo(double mass, int color) {
        this.mass = mass;
        this.color = color;
    }

    public double getMass() {
        return mass;
    }

    public int getColor() {
        return color;
    }
}
