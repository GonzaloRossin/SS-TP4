package ar.edu.itba.ss.solarSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAccumSS {
    private double minDistance = Double.MAX_VALUE;
    private double time = Double.MAX_VALUE;
    private List<Double> timeArray = new ArrayList<>();
    private List<Double> vArray = new ArrayList<>();

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

    public void addTime(Double time){timeArray.add(time);}

    public void addVelocity(Double V){vArray.add(V);}

    public List<Double> getTimeArray() {
        return timeArray;
    }

    public List<Double> getvArray() {
        return vArray;
    }
}
