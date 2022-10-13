package ar.edu.itba.ss.oscillator;

import ar.edu.itba.ss.Algorithm;
import ar.edu.itba.ss.DataAcumulator;
import ar.edu.itba.ss.Particle;
import ar.edu.itba.ss.Vector2;

import java.util.List;

public class SimulationHandler {
    private Particle verletP, analyticP, beemanP, gearPredictorP;
    private double K;
    private double gamma;
    private double tf;
    private double meanCuadraticErrorVerlet = 0, meanCuadraticErrorBeeman = 0, meanCuadraticErrorGCP = 0;
    private double actualTime = 0, step;


    public SimulationHandler(double pMass, Vector2 r, Vector2 v, double K, double gamma, double tf, double step) {
        this.K = K;
        this.gamma = gamma;
        this.tf = tf;
        this.verletP = new Particle(0 , pMass, r, gamma, K, 0);
        this.analyticP = new Particle(1, pMass, new Vector2(r.getX(), 0.3), gamma, K, 20);
        this.beemanP = new Particle(1, pMass, new Vector2(r.getX(), 0), gamma, K, 200);
        this.gearPredictorP = new Particle(1, pMass, new Vector2(r.getX(), 0), gamma, K, 100);
        this.step = step;
    }

    public void initParticles() {
        verletP.applyEulerModified(step);
        beemanP.applyEulerModified(step);
        actualTime += step;
    }

    public void iterate(DataAcumulator dataAcumulator) {

        dataAcumulator.addT(actualTime);
        Double pVerlet = verletP.applyVerlet(step);
        dataAcumulator.addP(pVerlet, Algorithm.VERLET);
        Double pBeeman = beemanP.applyBeeman(step);
        dataAcumulator.addP(pBeeman, Algorithm.BEEMAN);
        Double pGCP = gearPredictorP.applyGearPredictor5(step);
        dataAcumulator.addP(pGCP, Algorithm.GCP);

        analyticP.setActualR(calculateAnalyticR(analyticP));
        Double pAnalytical = analyticP.getActualR().getX();
        dataAcumulator.addP(pAnalytical, Algorithm.ANALITYCAL);
        calculateErrors(dataAcumulator, pVerlet, pBeeman, pGCP, pAnalytical);
        actualTime += step;
    }

    public void calculateErrors(DataAcumulator dataAcumulator,Double pVerlet, Double pBeeman, Double pGCP, Double analytical){
        dataAcumulator.addError(analytical-pVerlet, Algorithm.VERLET);
        dataAcumulator.addError(analytical-pBeeman, Algorithm.BEEMAN);
        dataAcumulator.addError(analytical-pGCP, Algorithm.GCP);
    }

    public void calculateCuadraticErrors(DataAcumulator dataAcumulator, Double delta){
        Double verletCuadraticSum = 0.0, beemanCuadraticSum = 0.0, GCPcuadraticSum = 0.0;
        int iterations = dataAcumulator.getTlist().size();
        for(int i=0; i < iterations;i++){
            verletCuadraticSum += Math.pow(dataAcumulator.getErrors().get(Algorithm.VERLET).get(i), 2);
            beemanCuadraticSum += Math.pow(dataAcumulator.getErrors().get(Algorithm.BEEMAN).get(i), 2);
            GCPcuadraticSum += Math.pow(dataAcumulator.getErrors().get(Algorithm.GCP).get(i), 2);
        }
        dataAcumulator.addMeanCuadraticError(verletCuadraticSum/iterations, Algorithm.VERLET);
        dataAcumulator.addMeanCuadraticError(beemanCuadraticSum/iterations, Algorithm.BEEMAN);
        dataAcumulator.addMeanCuadraticError(GCPcuadraticSum/iterations, Algorithm.GCP);
        dataAcumulator.addDelta(delta);
    }

    public Double getCuadraticError(List<Double> errorList){
        Double cuadraticSum = 0.0;
        for (Double aDouble : errorList) {
            cuadraticSum += Math.pow(aDouble, 2);
        }
        return cuadraticSum/errorList.size();
    }

    public Vector2 calculateAnalyticR(Particle p) {
        double exp = Math.exp(-(gamma/(2 * p.getMass())) * actualTime);

        double kDivM = K / p.getMass();
        double ggDiv4mm = (gamma * gamma) / (4 * p.getMass() * p.getMass());
        double pow = Math.pow(kDivM - ggDiv4mm, 0.5);
        double cos = Math.cos(pow * actualTime);

        return new Vector2(exp * cos, p.getActualR().getY());
    }

    public String printParticles() {
        return String.format("%s%s%s%s", analyticP.toXYZ(), verletP.toXYZ(), beemanP.toXYZ(), gearPredictorP.toXYZ());
    }
    public String printGear() {
        return String.format("%s%s", analyticP.toXYZ(), gearPredictorP.toXYZ());
    }


    public Particle getVerletP() {
        return verletP;
    }

    public Particle getAnalyticP() {
        return analyticP;
    }

    public double getK() {
        return K;
    }

    public double getGamma() {
        return gamma;
    }

    public double getTf() {
        return tf;
    }

    public double getActualTime() {
        return actualTime;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }
}
