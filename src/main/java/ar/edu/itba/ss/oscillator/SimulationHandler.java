package ar.edu.itba.ss.oscillator;

import ar.edu.itba.ss.Particle;
import ar.edu.itba.ss.Vector2;

public class SimulationHandler {
    private Particle verletP, analyticP, beemanP, gearPredictorP;
    private double K;
    private double gamma;
    private double tf;
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

    public void iterate() {
        verletP.applyVerlet(step);
        beemanP.applyBeeman(step);
        gearPredictorP.applyGearPredictor5(step);

        analyticP.setActualR(calculateAnalyticR(analyticP));
        actualTime += step;
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
}
