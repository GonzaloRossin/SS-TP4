package ar.edu.itba.ss.solarSystem;

import ar.edu.itba.ss.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlanetsHandler {
    private final List<Planet> planetList = new ArrayList<>();
    private Planet starship;
    private double step = 300, actualTime = 0;
    private double tf = 7776000.0;
    private String departureDate;

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

        double distanceToStarship = earthPosition.module() - earthRadius - ISSAltitude;
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

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }
    public String getDepartureDate() {
        return departureDate;
    }

    public double getStarshipToVenus() {
        Planet venus = planetList.stream().filter(p -> Objects.equals(p.getName(), "Venus")).findFirst().get();

         return venus.getActualR().distanceTo(starship.getActualR());
    }
}
