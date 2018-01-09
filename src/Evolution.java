package sample;

import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Ted on 06.01.2018.
 */
public class Evolution {
    public ArrayList<Indiv> population;
    Indiv bestIndiv;
    double min;
    double max;
    int mi;
    int n; //dimensions

    /*
    mi - population size
    n - dimensions
    min - min x value
    max - max x value
     */
    public void generate(int mi, int n, double min, double max, double sigmaRange) {
        this.min = min;
        this.max = max;
        this.mi = mi;
        this.n = n;
        population = new ArrayList(mi);
        for (int i = 0; i < mi; ++i)
            population.add(new Indiv(n, min, max, sigmaRange));
    }
    /*
    Solve by (mi + lambda) algorithm, c = length between local max-s
     */
    public void solveMPL(int mi, int lambda, double c) throws Exception {
        if (this.mi != mi)
            throw new Exception();
        do {
            ArrayList<Indiv> tPopulation = new ArrayList(lambda);
            ArrayList<Indiv> rPopulation = new ArrayList(lambda);
            Random random = new Random(mi);
            for (int i = 0; i < lambda; ++i)
                tPopulation.add(population.get(random.nextInt(mi)));
            for (int i = 0; i < lambda; ++i) {
                Indiv parentM = tPopulation.get(random.nextInt(lambda));
                Indiv parentF = tPopulation.get(random.nextInt(lambda));
                Indiv child = new Indiv(parentM, parentF);
                rPopulation.add(child);
            }
            population = chooseBests(population, rPopulation);
        } while (bestIndiv.getFit() < 0.95);
    }
    /*
    Solve by evo programming algorithm
     */
    public void solveEP(int mi) throws Exception {
        if (this.mi != mi)
            throw new Exception();
    }
    /*
    Choose who should be in next population
    actually - mi firsts
     */
    public ArrayList<Indiv> chooseBests(ArrayList<Indiv> population, ArrayList<Indiv> rPopulation) {
        int lambda = rPopulation.size();
        population.addAll(rPopulation);
        population.sort(Comparator.comparing(Indiv::getFit));
        ArrayList<Indiv> newPopulation = new ArrayList(mi);
        for (int i = 0; i < mi; ++i)
            newPopulation.add(population.get(mi + lambda - i - 1));
        bestIndiv = newPopulation.get(0);
        return newPopulation;
    }
}
