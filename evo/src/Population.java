import java.util.ArrayList;

/**
 * Created by Ted on 06.01.2018.
 */
class Population {
    public ArrayList<Indiv> population;
    public Indiv bestIndiv;
    private double min;
    private double max;
    private int mi;
    private int lambda;
    private int n; //dimensions

    /*
    mi - population size
    n - dimensions
    min - min x value
    max - max x value
     */
    public void generate4MPL(int mi, int lambda, int n, double min, double max, double sigmaRange) {
        this.min = min;
        this.max = max;
        this.mi = mi;
        this.lambda = lambda;
        this.n = n;
        population = new ArrayList(mi);
        for (int i = 0; i < mi; ++i)
            population.add(new Indiv(n, min, max, sigmaRange));
    }
    /*
        mi - population size
        n - dimensions
        min - min x value
        max - max x value
         */
    public void generate4EP(int mi, int n, double min, double max, double sigmaRange) {
        this.min = min;
        this.max = max;
        this.mi = mi;
        this.n = n;
        population = new ArrayList(mi);
        for (int i = 0; i < mi; ++i)
            population.add(new Indiv(n, min, max, sigmaRange));
    }

}
