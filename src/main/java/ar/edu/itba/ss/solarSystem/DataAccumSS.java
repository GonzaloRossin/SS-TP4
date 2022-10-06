package ar.edu.itba.ss.solarSystem;

import java.util.HashMap;
import java.util.Map;

public class DataAccumSS {
    private double minDistance = Double.MAX_VALUE;
    private double time = Double.MAX_VALUE;

    public DataAccumSS() {

    }

    public void setMinDistance(double distance, double time) {
        if (distance < minDistance) {
            minDistance = distance;
            this.time = time;
        }
    }

    public double getMinDistance() {
        return minDistance;
    }

    public double getTime() {
        return time;
    }
}
