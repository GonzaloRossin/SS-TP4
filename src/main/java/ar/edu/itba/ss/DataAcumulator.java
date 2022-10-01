package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAcumulator {
    private List<Double> Tlist;
    private Map<Algorithm, List<Double>> positions;
    private Map<Algorithm, List<Double>> errors;
    private Map<Algorithm, Double> meanCuadrticErrors;

    public DataAcumulator() {
        Tlist = new ArrayList<>();
        positions = new HashMap<>();
        positions.put(Algorithm.ANALITYCAL, new ArrayList<Double>());
        positions.put(Algorithm.VERLET, new ArrayList<Double>());
        positions.put(Algorithm.BEEMAN, new ArrayList<Double>());
        positions.put(Algorithm.GCP, new ArrayList<Double>());
        errors = new HashMap<>();
        errors.put(Algorithm.VERLET, new ArrayList<Double>());
        errors.put(Algorithm.BEEMAN, new ArrayList<Double>());
        errors.put(Algorithm.GCP, new ArrayList<Double>());
        meanCuadrticErrors = new HashMap<>();
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
        meanCuadrticErrors.put(algorithm, error);
    }

    public Map<Algorithm, Double> getMeanCuadrticErrors() {
        return meanCuadrticErrors;
    }
}
