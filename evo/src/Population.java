import java.util.ArrayList;

public interface Population {
    Population newInstance(int mi, int n, double min, double max, double sigmaRange);
    Population newInstance(Population population);
    ArrayList<Individual> getPopulation();
    ArrayList<Individual> getBestsInGen();
    Individual getBestIndiv();
    void setPopulation(ArrayList<Individual> population);
    void setBestsInGen(ArrayList<Individual> bestsInGen);
    void setBestIndiv(Individual bestIndiv);
}
