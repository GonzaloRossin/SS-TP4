package ar.edu.itba.ss.solarSystem;

import ar.edu.itba.ss.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlanetsHandler {
    private final List<Planet> planetList = new ArrayList<>();
    private Planet starship;
    private double step = 300, actualTime = 0;
    private double tf = 31536000.0 * 5;

    public PlanetsHandler() {
        Planet sun = new Planet(new Vector2(0,0), new Vector2(0,0), PlanetsInfo.SUN);
        planetList.add(sun);
    }

    public void addPlanet(Planet planet) {
        planetList.add(planet);
        if (Objects.equals(planet.getName(), "Earth")) {
            Vector2 starshipR = calculateStarshipR(planet);
            Vector2 starshipV = calculateStarshipV(planet);

            starship = new Planet(starshipR, starshipV, PlanetsInfo.STARSHIP);
        }
    }

    public Vector2 calculateStarshipR(Planet planet) {
        Vector2 earthPosition = planet.getActualR();
        Vector2 sunEarthVersor = earthPosition.normalize();
        double earthRadius = 6378137.0;
        double ISSAltitude = 400.0;

        double distanceToStarship = earthPosition.distanceTo(earthPosition) - earthRadius - ISSAltitude;
        return sunEarthVersor.scalarProduct(distanceToStarship);
    }

    public Vector2 calculateStarshipV(Planet planet) {
        Vector2 radialVersor = planet.getActualR().normalize();
        Vector2 orbitalVersor = radialVersor.getOrthogonal();
        double starshipInitialSpeed = 8000.0;

        Vector2 earthV = planet.getActualV();
        double earthOrbitalSpeedPro = orbitalVersor.innerProduct(earthV);
        double ISSOrbitalSpeed = 7660.0;

        double velocityModule = earthOrbitalSpeedPro - ISSOrbitalSpeed - starshipInitialSpeed;
        return orbitalVersor.scalarProduct(velocityModule);
    }

    public void initPlanets() {
        for(Planet planet : planetList) {
            planet.applyEulerModified(planetList, step);
        }
        starship.applyEulerModified(planetList, step);
        actualTime += step;
    }

    public void iterate() {
        for(Planet planet : planetList) {
            planet.applyBeeman(planetList, step);
        }
        starship.applyBeeman(planetList, step);
        actualTime += step;
    }

    public String printPlanets() {
        StringBuilder sb = new StringBuilder();
        for(Planet p : planetList) {
            sb.append(p.toXYZ());
        }
        sb.append(starship.toXYZ());
        return sb.toString();
    }

    public double getActualTime() {
        return actualTime;
    }

    public double getTf() {
        return tf;
    }
}
