import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //testMPL();
        testEP();
    }

    public void testMPL() {
        Population evoMPL = new Population();
        int mi = 100;
        int lambda = 700;
        int n = 100;
        int maxRounds = 3000;
        double minFit = 0.95;
        double min = -20.0;
        double max = 20.0;
        double sigmaRange = 0.5;
        double c = 0.2;

        System.out.println("Start testing MPL algorithm...");
        log("Start testing MPL algorithm...\n" +
                "mi = " + mi +
                " lambda = " + lambda +
                " n = " + n +
                " maxRounds = " + maxRounds +
                " minFit = " + minFit +
                " sigmaRange = " + sigmaRange +
                " c = " + c);
        while (true) {
            long sum = 0;
            long startTime;
            long endTime;
            int j = 10;
            double fit = 0.0;

            for (int i = 0; i < j; ++i) {
                evoMPL.generate4MPL(mi, lambda, n, min, max, sigmaRange);
                startTime = System.currentTimeMillis();
                evoMPL.bestIndiv = Solver.solveMPL(evoMPL.population, mi, lambda, c, maxRounds, minFit);
                endTime = System.currentTimeMillis();
                sum += endTime - startTime;
                fit += evoMPL.bestIndiv.getFit();
                System.out.println("mpl time: " + sum / (i + 1) + " fit: " + evoMPL.bestIndiv.getFit() + " c: " + c);
                log("mpl time: " + sum / (i + 1) + " fit: " + evoMPL.bestIndiv.getFit() + " c: " + c);
            }

            sum /= j;
            fit /= j;
            log("loops: " + j + " average time: " + sum + " avarage fit: " + fit);
        }
    }

    public void testEP() {
        Population evoEP = new Population();
        int mi = 100000;
        int n = 10;
        int maxRounds = 50;
        double minFit = 0.95;
        double min = -20.0;
        double max = 20.0;
        double sigmaRange = 3.0;


        System.out.println("Start testing EP algorithm...\n");
        log("Start testing EP algorithm..." +
                " mi = " + mi +
                " n = " + n +
                " maxRounds = " + maxRounds +
                " minFit = " + minFit +
                " sigmaRange = " + sigmaRange);
        while (true) {
            long sum = 0;
            long startTime;
            long endTime;
            int j = 10;
            double fit = 0.0;

            for (int i = 0; i < j; ++i) {
                evoEP.generate4EP(mi, n, min, max, sigmaRange);
                startTime = System.currentTimeMillis();
                evoEP.bestIndiv = Solver.solveEP(evoEP.population, mi, maxRounds, minFit);
                endTime = System.currentTimeMillis();
                sum += endTime - startTime;
                fit += evoEP.bestIndiv.getFit();

                System.out.println("ep time: " + sum / (i + 1) + " fit: " + evoEP.bestIndiv.getFit());
                log("ep time: " + sum / (i + 1) + " fit: " + evoEP.bestIndiv.getFit());
            }

            sum /= j;
            fit /= j;
            log("loops: " + j + " average time: " + sum + " average fit: " + fit);
        }
    }

    public void log (String string) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data.txt", true)));
            out.println(string);
            out.close();
        } catch (IOException e) {}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
