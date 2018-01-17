import java.util.ArrayList;

/*
@brief Interface which must be provided to use algorithms
 */
public interface Population {
    /*
    @brief creating new population from parameters which are passed when creating individuals
     */
    Population newInstance(int mi, int n, double min, double max, double sigmaRange);

    /*
    @brief copying population
     */
    Population newInstance(Population population);

    ArrayList<Individual> getPopulation();
    ArrayList<Individual> getBestsInGen();
    Individual getBestIndiv();
    void setPopulation(ArrayList<Individual> population);
    void setBestIndiv(Individual bestIndiv);
}
