package ar.edu.itba.ss.solarSystem;

import ar.edu.itba.ss.Vector2;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Scanner;

import static ar.edu.itba.ss.Utils.openFile;
import static ar.edu.itba.ss.Utils.writeToFile;

public class App {

    public static void main(String[] args) {
        PlanetsHandler ph = new PlanetsHandler();

        Scanner earth = openInputFile("earth.txt");
        readTxt(earth, ph, PlanetsInfo.EARTH);

        Scanner venus = openInputFile("venus.txt");
        readTxt(venus, ph, PlanetsInfo.VENUS);

        PrintWriter pw = openFile("output/anim/solarSystem.xyz");
        String size = "6\n\n";
        double offset = 5.1E11;
        String borders = "" + offset + " -" + offset + " 100\n" + "-" + offset + " " + offset + " 100\n";

        writeToFile(pw, size + ph.printPlanets() + borders);

        double outerStep = 3600 * 24, lastTime = ph.getActualTime();
        ph.initPlanets();
        while (ph.getActualTime() < ph.getTf()) {
            ph.iterate();
            if (ph.getActualTime() - lastTime > outerStep ) {
                lastTime = ph.getActualTime();
                writeToFile(pw, size + ph.printPlanets() + borders);
            }
        }
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

    private static void readTxt(Scanner sc, PlanetsHandler ph, PlanetsInfo planetsInfo) {
        double rx, ry, vx, vy;
        while (!Objects.equals(sc.nextLine(), "$$SOE"));

        // Skip to the good part
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
    }
}
