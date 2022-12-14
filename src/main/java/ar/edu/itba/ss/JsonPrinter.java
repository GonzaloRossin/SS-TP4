package ar.edu.itba.ss;

import ar.edu.itba.ss.oscillator.SimulationHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class JsonPrinter {
    JSONArray dataArray;
    JSONArray errorArray;
    JSONArray cuadraticErrors;

    JSONArray dateDistanceArray;
    JSONArray velocityArray;
    JSONArray energyErrorArray;


    JSONArray minDistanceVelocity;

    public JsonPrinter() {
        this.dataArray = new JSONArray();
        this.errorArray = new JSONArray();
        this.dateDistanceArray = new JSONArray();
        this.velocityArray = new JSONArray();
        this.minDistanceVelocity = new JSONArray();
        this.cuadraticErrors = new JSONArray();
        this.energyErrorArray = new JSONArray();
    }

    public void addStepEnergyError(double energy, double step) {
        JSONObject toAdd = new JSONObject();
        toAdd.put("step", step);
        toAdd.put("energy", energy);
        energyErrorArray.add(toAdd);
    }

    public JSONArray getEnergyErrorArray() {
        return energyErrorArray;
    }

    public void addDateDistance(String date, double distance, double time) {
        JSONObject toAdd = new JSONObject();
        toAdd.put("date", date);
        toAdd.put("distance", distance);
        toAdd.put("time", time);
        dateDistanceArray.add(toAdd);
    }

    public JSONArray getDateDistanceArray() {
        return dateDistanceArray;
    }

    public void addCuadraticErrors(SimulationHandler simulationHandler, DataAcumulator dataAcumulator){
        Double errorVerlet = simulationHandler.getCuadraticError(dataAcumulator.getErrors().get(Algorithm.VERLET));
        Double errorBeeman = simulationHandler.getCuadraticError(dataAcumulator.getErrors().get(Algorithm.BEEMAN));
        Double errorGCP = simulationHandler.getCuadraticError(dataAcumulator.getErrors().get(Algorithm.GCP));
        JSONObject toAdd = new JSONObject();
        toAdd.put("errorVerlet", errorVerlet);
        toAdd.put("errorBeeman", errorBeeman);
        toAdd.put("errorGCP", errorGCP);
        cuadraticErrors.add(toAdd);
    }

    public JSONArray getCuadraticErrors() {
        return cuadraticErrors;
    }

    public void createDataArray(DataAcumulator dataAccumulator){
        for(int i = 0; i < dataAccumulator.getTlist().size();i++){
            addStep(dataAccumulator, i);
        }

    }
    public void createErrorArray(DataAcumulator dataAccumulator){
        for(int i=0;i < dataAccumulator.getDeltas().size();i++){
            addErrorStep(dataAccumulator,i);
        }
    }

    public void addStep(DataAcumulator dataAcumulator, int iteration) {
        JSONObject step = new JSONObject();
        step.put("time", dataAcumulator.getTlist().get(iteration));
        step.put("pAnalytical", dataAcumulator.getPositions().get(Algorithm.ANALITYCAL).get(iteration));
        step.put("pVerlet", dataAcumulator.getPositions().get(Algorithm.VERLET).get(iteration));
        step.put("pBeeman", dataAcumulator.getPositions().get(Algorithm.BEEMAN).get(iteration));
        step.put("pGCP", dataAcumulator.getPositions().get(Algorithm.GCP).get(iteration));
        dataArray.add(step);
    }
    public void addErrorStep(DataAcumulator dataAcumulator,int iteration){
        JSONObject step = new JSONObject();
        step.put("delta", dataAcumulator.getDeltas().get(iteration));
        step.put("errorVerlet",dataAcumulator.getMeanCuadrticErrors().get(Algorithm.VERLET).get(iteration));
        step.put("errorBeeman",dataAcumulator.getMeanCuadrticErrors().get(Algorithm.BEEMAN).get(iteration));
        step.put("errorGCP",dataAcumulator.getMeanCuadrticErrors().get(Algorithm.GCP).get(iteration));
        errorArray.add(step);
    }
    public void setVelocityArray(List<Double> v, List<Double> time){
        for(int i = 0; i< time.size();i++){
            JSONObject step = new JSONObject();
            step.put("time", time.get(i));
            step.put("v", v.get(i));
            velocityArray.add(step);
        }
    }
    public void addMinDistanceVelocity(Double velocity,Double elpasedDays){
        JSONObject step = new JSONObject();
        step.put("velocity", velocity);
        step.put("elapsed_time", elpasedDays);
        minDistanceVelocity.add(step);
    }

    public JSONArray getVelocityArray() {
        return velocityArray;
    }
    public JSONArray getMinDistanceVelocity(){return minDistanceVelocity;}

    public JSONArray getDataArray() {
        return dataArray;
    }
    public JSONArray getErrorArray(){
        return errorArray;
    }

    public void addAngleDistance(double angle, double distance, double time) {
        JSONObject toAdd = new JSONObject();
        toAdd.put("angle", angle);
        toAdd.put("distance", distance);
        toAdd.put("time", time);
        dateDistanceArray.add(toAdd);
    }
}
