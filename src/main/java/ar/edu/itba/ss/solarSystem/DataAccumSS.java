package ar.edu.itba.ss.solarSystem;

import java.util.HashMap;
import java.util.Map;

public class DataAccumSS {
    private double minDistance = Double.MAX_VALUE;

    public DataAccumSS() {

    }

    public void setMinDistance(double distance) {
        if (distance < minDistance) {
            minDistance = distance;
        }
    }

    public double getMinDistance() {
        return minDistance;
    }
}
