package ar.edu.itba.ss.solarSystem;

public enum PlanetsInfo {
    EARTH(5.97219E24, 60, "Earth"),
    SUN(1.988500E30, 75, "Sun"),
    VENUS(4.8685E24, 0, "Venus"),
    STARSHIP(2.0E5, 30, "Starship");

    final double mass;
    final int color;
    final String name;

    PlanetsInfo(double mass, int color, String name) {
        this.mass = mass;
        this.color = color;
        this.name = name;
    }

    public double getMass() {
        return mass;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
