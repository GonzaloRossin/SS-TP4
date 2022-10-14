package ar.edu.itba.ss.solarSystem;

public enum PlanetsInfo {
    EARTH(5.97219E24, 60, "Earth", 6371000),
    SUN(1.988500E30, 75, "Sun", 696340000),
    VENUS(4.8685E24, 85, "Venus", 6051800),
    STARSHIP(2.0E5, 30, "Starship",1000),
    MARS(6.4171E23, 100, "Mars", 3389000),;

    final double mass;
    final int color;
    final int radius;
    final String name;

    PlanetsInfo(double mass, int color, String name, int radius) {
        this.mass = mass;
        this.color = color;
        this.name = name;
        this.radius = radius;
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

    public int getRadius() {
        return radius;
    }
}
