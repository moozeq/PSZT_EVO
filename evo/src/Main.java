import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {

    private Stage pStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        pStage = primaryStage;
        testMPL();
        //testEP();
    }

    private void testMPL() {
        Population evoMPL = new Population();
        int mi = 1000;
        int lambda = 7000;
        int n = 30;
        int maxRounds = 3000;
        double minFit = 0.99;
        double min = -20.0;
        double max = 20.0;
        double sigmaRange = 3.0;
        double c = 4.2;

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
            int j = 1;
            double fit = 0.0;

            for (int i = 0; i < j; ++i) {
                evoMPL.generate4MPL(mi, lambda, n, min, max, sigmaRange);
                startTime = System.currentTimeMillis();
                evoMPL.bestIndiv = Solver.solveMPL(evoMPL, mi, lambda, c, maxRounds, minFit);
                endTime = System.currentTimeMillis();
                sum += endTime - startTime;
                fit += evoMPL.bestIndiv.getFit();

                System.out.println("mpl time: " + sum / (i + 1) + " fit: " + evoMPL.bestIndiv.getFit() + " c: " + c);
                log("mpl time: " + sum / (i + 1) + " fit: " + evoMPL.bestIndiv.getFit() + " c: " + c);

                //showPopulation(evoMPL.population, 0, 1);
            }

            sum /= j;
            fit /= j;
            log("loops: " + j + " average time: " + sum + " avarage fit: " + fit);
        }
    }

    public void testEP() {
        Population evoEP = new Population();
        int mi = 1000;
        int n = 10;
        int maxRounds = 10000;
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
        //while (true) {
            long sum = 0;
            long startTime;
            long endTime;
            int j = 1;
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

                //showPopulation(evoEP.population, 0, 1);
            }

            sum /= j;
            fit /= j;
            log("loops: " + j + " average time: " + sum + " average fit: " + fit);
        //}
    }

    private void log(String string) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data.txt", true)));
            out.println(string);
            out.close();
        } catch (IOException e) {}
    }

    private void showPopulation(ArrayList<Indiv> population, int x1, int x2) {
        pStage.setTitle("Population chart");
        final NumberAxis xAxis = new NumberAxis(-20, 20, 1);
        final NumberAxis yAxis = new NumberAxis(-20, 20, 1);
        final ScatterChart<Number,Number> sc = new ScatterChart<>(xAxis,yAxis);
        xAxis.setLabel("x" + x1);
        yAxis.setLabel("x" + x2);
        sc.setTitle("Population");
        XYChart.Series series = new XYChart.Series();
        series.setName("Individual");
        for (Indiv aPopulation : population)
            series.getData().add(new XYChart.Data(aPopulation.getX(x1), aPopulation.getX(x2)));
        sc.getData().add(series);

        pStage.setScene(new Scene (sc, 500, 500));
        pStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
