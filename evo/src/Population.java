import java.util.ArrayList;

/**
 * Created by Ted on 06.01.2018.
 */
class Population {
    public ArrayList<Indiv> population;
    public ArrayList<Indiv> bestInGen;
    public Indiv bestIndiv;

    Population(int mi, int n, double min, double max, double sigmaRange) {
        population = new ArrayList<>(mi);
        bestInGen = new ArrayList<>();
        for (int i = 0; i < mi; ++i) {
            Indiv indiv = new Indiv(n, min, max, sigmaRange);
            if (i == 0)
                bestIndiv = indiv;
            if (indiv.getFit() > bestIndiv.getFit())
                bestIndiv = indiv;
            population.add(indiv);
        }
        bestInGen.add(bestIndiv);
    }

    Population(Population population) {
        this.population = new ArrayList<>(population.population);
        this.bestIndiv = new Indiv(population.bestIndiv, false);
        this.bestInGen = new ArrayList<>();
    }
}
