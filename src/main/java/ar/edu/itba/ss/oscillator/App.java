package ar.edu.itba.ss.oscillator;

import ar.edu.itba.ss.DataAcumulator;
import ar.edu.itba.ss.JsonPrinter;
import ar.edu.itba.ss.Utils;
import ar.edu.itba.ss.Vector2;

import java.io.*;
import java.util.Scanner;

import static ar.edu.itba.ss.Utils.openFile;
import static ar.edu.itba.ss.Utils.writeToFile;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        // Locating inputs.txt in resources
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("inputs.txt");
        if (is == null) {
            System.out.println("File not found");
            System.exit(1);
        }

        // Initiate setup
        Scanner scanner = new Scanner(is);
        SimulationHandler handler = readTxt(scanner);


        PrintWriter pw = openFile("output/anim/verlet.xyz");
        String size = "6\n\n";
        String limits = "1 0 255\n-1 0 255\n";
        DataAcumulator dataAccumulator = new DataAcumulator();

        writeToFile(pw, size + limits + handler.printParticles());

        double outerStep = 0.01, lastTime = handler.getActualTime();
        handler.initParticles();
        while (handler.getActualTime() < handler.getTf()) {
            handler.iterate(dataAccumulator);
            if (handler.getActualTime() - lastTime > outerStep ) {
                lastTime = handler.getActualTime();
                writeToFile(pw, size + limits + handler.printParticles());
            }
        }
        handler.calculateCuadraticErrors(dataAccumulator);
        JsonPrinter jsonPrinter = new JsonPrinter();
        jsonPrinter.createArray(dataAccumulator);
        String str2 = String.format("plots/positionOverTime.json");
        PrintWriter positionsVsT = openFile(str2);
        writeToFile(positionsVsT, jsonPrinter.getDataArray().toJSONString());
    }



    public static SimulationHandler readTxt(Scanner scanner) {
        double mass = 0, K = 0, gamma = 0, tf = 0, r0 = 0, step = 0;

        // Read mass value
        if (scanner.hasNextLine()) {
            String in = scanner.next();
            mass = Double.parseDouble(scanner.next());
            System.out.println(in + " " + mass);
        }
        // Read K value
        if (scanner.hasNextLine()) {
            String in = scanner.next();
            K = Double.parseDouble(scanner.next());
            System.out.println(in + " " + K);
        }
        // Read gamma value
        if (scanner.hasNextLine()) {
            String in = scanner.next();
            gamma = Double.parseDouble(scanner.next());
            System.out.println(in + " " + gamma);
        }
        // Read step value
        if (scanner.hasNextLine()) {
            String in = scanner.next();
            step = Double.parseDouble(scanner.next());
            System.out.println(in + " " + step);
        }
        // Read tf value
        if (scanner.hasNextLine()) {
            String in = scanner.next();
            tf = Double.parseDouble(scanner.next());
            System.out.println(in + " " + tf);
        }
        // Read r0 value
        if (scanner.hasNextLine()) {
            String in = scanner.next();
            r0 = Double.parseDouble(scanner.next());
            System.out.println(in + " " + r0);
        }
        return new SimulationHandler(mass, new Vector2(r0, 0), new Vector2(0,0), K, gamma, tf, step);
    }
}
