package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAcumulator {
    private final List<Double> Tlist;
    private final List<Double> deltas;
    private final Map<Algorithm, List<Double>> positions;
    private final Map<Algorithm, List<Double>> errors;
    private final Map<Algorithm, List<Double>> meanCuadrticErrors;

    public DataAcumulator() {
        Tlist = new ArrayList<>();
        positions = new HashMap<>();
        positions.put(Algorithm.ANALITYCAL, new ArrayList<Double>());
        positions.put(Algorithm.VERLET, new ArrayList<Double>());
        positions.put(Algorithm.BEEMAN, new ArrayList<Double>());
        positions.put(Algorithm.GCP, new ArrayList<Double>());
        errors = new HashMap<>();
        deltas  = new ArrayList<>();
        errors.put(Algorithm.VERLET, new ArrayList<Double>());
        errors.put(Algorithm.BEEMAN, new ArrayList<Double>());
        errors.put(Algorithm.GCP, new ArrayList<Double>());
        meanCuadrticErrors = new HashMap<>();
        meanCuadrticErrors.put(Algorithm.VERLET, new ArrayList<Double>());
        meanCuadrticErrors.put(Algorithm.BEEMAN, new ArrayList<Double>());
        meanCuadrticErrors.put(Algorithm.GCP, new ArrayList<Double>());
    }

    public void addP(Double position, Algorithm algorithm) {
        positions.get(algorithm).add(position);
    }

    public void addT(Double i) {
        this.Tlist.add(i);
    }

    public void addError(Double i,Algorithm algorithm) {
        this.errors.get(algorithm).add(i);
    }

    public List<Double> getTlist() {
        return Tlist;
    }

    public Map<Algorithm, List<Double>> getPositions() {
        return positions;
    }

    public Map<Algorithm, List<Double>> getErrors() {
        return errors;
    }

    public void addMeanCuadraticError(Double error, Algorithm algorithm){
        meanCuadrticErrors.get(algorithm).add(error);
    }

    public void addDelta(Double delta){
        deltas.add(delta);
    }

    public List<Double> getDeltas(){ return deltas;}

    public Map<Algorithm, List<Double>> getMeanCuadrticErrors() {
        return meanCuadrticErrors;
    }
}
