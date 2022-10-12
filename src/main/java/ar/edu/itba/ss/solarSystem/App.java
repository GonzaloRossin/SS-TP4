package ar.edu.itba.ss.solarSystem;

import ar.edu.itba.ss.JsonPrinter;
import ar.edu.itba.ss.Vector2;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

import static ar.edu.itba.ss.Utils.openFile;
import static ar.edu.itba.ss.Utils.writeToFile;

public class App {
    private static final int SECONDS_IN_DAY = 86400;

    public static void main(String[] args) {
        simulationMain();
//        tryMultipleDates();
//        tryDifferentVelocities();
        tryDifferentDepartureAngles();
    }

    public static void tryMultipleDates() {
        Scanner earth = openInputFile("earth_1minStep_24_05_2023_0200.txt");
        initTxt(earth);
        Scanner venus = openInputFile("venus_1minStep_24_05_2023_0200.txt");
        initTxt(venus);
        JsonPrinter jp = new JsonPrinter();

        for (PlanetsHandler ph = new PlanetsHandler(); ; ph = new PlanetsHandler()) {
            if (!readTxt(earth, ph, PlanetsInfo.EARTH)) {
                System.out.println("No more earth data");
                break;
            }
            if (!readTxt(venus, ph, PlanetsInfo.VENUS)) {
                System.out.println("No more Venus data");
                break;
            }
            DataAccumSS dataAccumSS = new DataAccumSS();

            double outerStep = 3600 * 24, lastTime = ph.getActualTime();
            ph.initPlanets();
            while (ph.getActualTime() < ph.getTf()) {
                ph.iterate();
                if (ph.getActualTime() - lastTime > outerStep ) {
                    lastTime = ph.getActualTime();
                }
                dataAccumSS.setMinDistance(ph.getStarshipToVenus(), ph.getActualTime());
            }
            jp.addDateDistance(ph.getDepartureDate(), dataAccumSS.getMinDistance(), dataAccumSS.getTime());
        }
        PrintWriter pw = openFile("plots/dateDistance.json");
        writeToFile(pw, jp.getDateDistanceArray().toJSONString());
    }

    public static void simulationMain() {
        PlanetsHandler ph = new PlanetsHandler();
        Scanner earth = openInputFile("earth10daysStep3years.txt");
        initTxt(earth);
        readTxt(earth, ph, PlanetsInfo.EARTH);

        Scanner venus = openInputFile("venus10daysStep3years.txt");
        initTxt(venus);
        readTxt(venus, ph, PlanetsInfo.VENUS);

        Scanner mars = openInputFile("mars10daysStep3years.txt");
        initTxt(mars);
        readTxt(mars, ph, PlanetsInfo.MARS);

        JsonPrinter jp = new JsonPrinter();

        PrintWriter pw = openFile("output/anim/solarSystem.xyz");
        String size = "7\n\n";
        double offset = 5.1E11;
        String borders = "" + offset + " -" + offset + " 100\n" + "-" + offset + " " + offset + " 100\n";

        writeToFile(pw, size + ph.printPlanets() + borders);

        DataAccumSS dataAccumSS = new DataAccumSS();

        double outerStep = 3600 * 24, lastTime = ph.getActualTime();
        int days = 0;
        ph.initPlanets();
        while (ph.getActualTime() < ph.getTf() && ph.getStarshipToVenus() > PlanetsInfo.VENUS.getRadius()) {
            ph.iterate();
            if (ph.getActualTime() - lastTime > outerStep ) {
                lastTime = ph.getActualTime();
                writeToFile(pw, size + ph.printPlanets() + borders);
            }
            if(ph.getActualTime() % SECONDS_IN_DAY == 0){
                dataAccumSS.addTime((double) ++days);
                dataAccumSS.addVelocity(ph.getStarship().getActualV().module());
            }
        }
        jp.setVelocityArray(dataAccumSS.getvArray(), dataAccumSS.getTimeArray());
        pw = openFile("plots/VmoduleTime.json");
        writeToFile(pw,jp.getVelocityArray().toJSONString());
    }

    public static void tryDifferentVelocities() {
        PlanetsHandler initialPh = new PlanetsHandler();
        Scanner earth = openInputFile("earth_24_05_2023_0312.txt");
        initTxt(earth);
        readTxt(earth, initialPh, PlanetsInfo.EARTH);

        Scanner venus = openInputFile("venus_24_05_2023_0312.txt");
        initTxt(venus);
        readTxt(venus, initialPh, PlanetsInfo.VENUS);
        double minTime = Double.MAX_VALUE, minVModule = 0;
        for (double i = -10; i < 10; i += 0.1) {
            PlanetsHandler ph = initialPh.clonePh(initialPh.getStarshipInitialSpeed() + i, Math.PI / 2);

            double outerStep = 300, lastTime = ph.getActualTime();
            ph.initPlanets();
            while (ph.getActualTime() < ph.getTf() && ph.getStarshipToVenus() > PlanetsInfo.VENUS.getRadius()) {
                ph.iterate();
                if (ph.getActualTime() - lastTime > outerStep ) {
                    lastTime = ph.getActualTime();
                }
            }
            if (ph.getStarshipToVenus() <= PlanetsInfo.VENUS.getRadius()) {
                if (minTime > ph.getActualTime()) {
                    minTime = ph.getActualTime();
                    minVModule = ph.getStarshipInitialSpeed();
                }
            }
        }
        System.out.println(minTime + " " + minVModule);
    }

    public static void tryDifferentDepartureAngles() {
        PlanetsHandler initialPh = new PlanetsHandler();
        Scanner earth = openInputFile("earth10daysStep3years.txt");
        initTxt(earth);
        readTxt(earth, initialPh, PlanetsInfo.EARTH);

        Scanner venus = openInputFile("venus10daysStep3years.txt");
        initTxt(venus);
        readTxt(venus, initialPh, PlanetsInfo.VENUS);

        Scanner mars = openInputFile("mars10daysStep3years.txt");
        initTxt(mars);
        readTxt(mars, initialPh, PlanetsInfo.MARS);
        JsonPrinter jp = new JsonPrinter();
//        for (double i = 0; i < Math.PI / 2; i += Math.PI / (2 * 16)) {
//        for (double i = 1.08 - 0.1; i < 1.08 + 0.1; i += 0.001) {
        for (double i = 1.114 - 0.001; i < 1.114 + 0.001; i += 0.0001) {
            PlanetsHandler ph = initialPh.clonePh(initialPh.getStarshipInitialSpeed(), initialPh.getDepartureAngle() + i);

            DataAccumSS dataAccumSS = new DataAccumSS();
            double outerStep = 300, lastTime = ph.getActualTime();
            ph.initPlanets();
            while (ph.getActualTime() < ph.getTf() && ph.getStarshipToMars() > PlanetsInfo.MARS.getRadius()) {
                ph.iterate();
                if (ph.getActualTime() - lastTime > outerStep ) {
                    lastTime = ph.getActualTime();
                }
                dataAccumSS.setMinDistance(ph.getStarshipToMars(), ph.getActualTime());
            }
            if (ph.getStarshipToMars() <= PlanetsInfo.MARS.getRadius()) {
                System.out.println("Llego");
            }
            jp.addAngleDistance(ph.getDepartureAngle(), dataAccumSS.getMinDistance(), dataAccumSS.getTime());
        }
        PrintWriter pw = openFile("plots/angleDistance.json");
        writeToFile(pw, jp.getDateDistanceArray().toJSONString());
    }


    public static Scanner openInputFile(String filepath) {
        // Locating inputs.txt in resources
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filepath);

        if (is == null) {
            System.out.println("File not found");
            System.exit(1);
        }

        // Initiate setup
        return new Scanner(is);
    }

    private static void initTxt(Scanner sc) {
        while (!Objects.equals(sc.nextLine(), "$$SOE"));
    }

    private static boolean readTxt(Scanner sc, PlanetsHandler ph, PlanetsInfo planetsInfo) {
        double rx, ry, vx, vy;
        // Skip to the good par
        if (Objects.equals(sc.next(), "$$EOE")) {
            return false;
        }
        sc.next(); sc.next();
        ph.setDepartureDate(sc.next() + " " + sc.next());
        sc.nextLine();
        sc.next();
        sc.next();
        rx = Double.parseDouble(sc.next());
        ry = Double.parseDouble(sc.next());

        // skip rz
        sc.next();
        vx = Double.parseDouble(sc.next());
        vy = Double.parseDouble(sc.next());

        Planet planet = new Planet(new Vector2(rx * 1000, ry * 1000), new Vector2(vx * 1000, vy * 1000), planetsInfo);
        ph.addPlanet(planet);

        sc.nextLine();
        sc.nextLine();
        return true;
    }
}
