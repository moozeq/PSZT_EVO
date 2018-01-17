import java.util.ArrayList;

public class Popul implements Population {
    private ArrayList<Individual> population;
    private ArrayList<Individual> bestsInGen;
    private Individual bestIndiv;

    @Override
    public Population newInstance(int mi, int n, double min, double max, double sigmaRange) {
        population = new ArrayList<>(mi);
        bestsInGen = new ArrayList<>();
        for (int i = 0; i < mi; ++i) {
            Individual individual = new Indiv().newInstance(n, min, max, sigmaRange);
            if (i == 0)
                bestIndiv = individual;
            if (individual.getFit() > bestIndiv.getFit())
                bestIndiv = individual;
            population.add(individual);
        }
        bestsInGen.add(bestIndiv);
        return this;
    }
    @Override
    public Population newInstance(Population population) {
        this.population = new ArrayList<>(population.getPopulation());
        this.bestIndiv = new Indiv().newInstance(population.getBestIndiv(), false);
        this.bestsInGen = new ArrayList<>();
        return this;
    }
    @Override
    public ArrayList<Individual> getPopulation() {return population;}
    @Override
    public ArrayList<Individual> getBestsInGen() {return bestsInGen;}
    @Override
    public Individual getBestIndiv() {return bestIndiv;}
    @Override
    public void setPopulation(ArrayList<Individual> population) {this.population = population;}
    @Override
    public void setBestsInGen(ArrayList<Individual> bestsInGen) {this.bestsInGen = bestsInGen;}
    @Override
    public void setBestIndiv(Individual bestIndiv) {this.bestIndiv = bestIndiv;}
}
