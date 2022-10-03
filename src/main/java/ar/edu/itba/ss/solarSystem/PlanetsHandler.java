package ar.edu.itba.ss.solarSystem;

import ar.edu.itba.ss.Vector2;

import java.util.ArrayList;
import java.util.List;

public class PlanetsHandler {
    private final List<Planet> planetList = new ArrayList<>();
    private double step = 300, actualTime = 0;
    private double tf = 31536000.0 * 5;

    public PlanetsHandler() {
        Planet sun = new Planet(new Vector2(0,0), new Vector2(0,0), PlanetsInfo.SUN.getMass(), PlanetsInfo.SUN.getColor());
        planetList.add(sun);
    }

    public void addPlanet(Planet planet) {
        planetList.add(planet);
    }

    public void initPlanets() {
        for(Planet planet : planetList) {
            planet.applyEulerModified(planetList, step);
        }
        actualTime += step;
    }

    public void iterate() {
        for(Planet planet : planetList) {
            planet.applyBeeman(planetList, step);
        }
        actualTime += step;
    }

    public String printPlanets() {
        StringBuilder sb = new StringBuilder();
        for(Planet p : planetList) {
            sb.append(p.toXYZ());
        }
        return sb.toString();
    }

    public double getActualTime() {
        return actualTime;
    }

    public double getTf() {
        return tf;
    }
}
