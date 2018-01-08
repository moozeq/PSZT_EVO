package sample;

import java.util.ArrayList;

/**
 * Created by Ted on 06.01.2018.
 */
public class Evolution {
    ArrayList<Indiv> population;
    Indiv bestIndiv;
    double min;
    double max;
    double mi;

    /*
    mi - population size
    n - dimensions
    min - min x value
    max - max x value
     */
    public void generate(int mi, int n, double min, double max) {
        this.min = min;
        this.max = max;
        this.mi = mi;
        population = new ArrayList(mi);
        for (int i = 0; i < mi; ++i)
            population.add(new Indiv(n, min, max));
    }
    /*
    Solve by (mi + lambda) algorithm, c = length between local max-s
     */
    public void solveMPL(double mi, double lambda, double c) throws Exception {
        if (this.mi != mi)
            throw new Exception();
    }
    /*
    Solve by evo programming algorithm
     */
    public void solveEP(double mi) throws Exception {
        if (this.mi != mi)
            throw new Exception();
    }
    /*
    Choose who should be in next population
     */
    public void chooseBests() {

    }
}
