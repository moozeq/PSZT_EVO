import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/*
@brief Class which represents algorithms which choose individuals from population P+R
    choose - default method
    chooseRand - choose % bests and rest randomly
    miBests - choose mi bests
    ranking - choose bests but w/ possibility of not
 */
public class Choosing {
    /*
    @brief default choosing method, called in algorithms
     */
    public static ArrayList<Individual> choose(ArrayList<Individual> bPopulation, ArrayList<Individual> rPopulation) {
        return ranking(bPopulation, rPopulation);
    }

    /*
    @brief choose percentage of bests and rest randomly
     */
    public static ArrayList<Individual> chooseRand(ArrayList<Individual> bPopulation, ArrayList<Individual> rPopulation, int percentage) {
        int lambda = rPopulation.size();
        int mi = bPopulation.size();
        rPopulation.addAll(bPopulation);
        rPopulation.sort(Comparator.comparing(Individual::getFit));

        Random random = new Random();
        ArrayList<Individual> newPopulation = new ArrayList(mi);
        int bestsNum = percentage * mi / 100;
        int i = 0;
        while (i++ < bestsNum)
            newPopulation.add(rPopulation.get(mi + lambda - i - 1));
        while (i++ < mi)
            newPopulation.add(rPopulation.get(random.nextInt(mi + lambda)));

        Solver.bestIndiv = newPopulation.get(0);
        return newPopulation;
    }

    /*
    @brief choose whole mi-population of bests
     */
    private static ArrayList<Individual> miBests(ArrayList<Individual> bPopulation, ArrayList<Individual> rPopulation) {
        int lambda = rPopulation.size();
        int mi = bPopulation.size();
        rPopulation.addAll(bPopulation);
        rPopulation.sort(Comparator.comparing(Individual::getFit));

        ArrayList<Individual> newPopulation = new ArrayList(mi);
        for (int i = 0; i < mi; ++i)
            newPopulation.add(rPopulation.get(mi + lambda - i - 1));

        Solver.bestIndiv = newPopulation.get(0);
        return newPopulation;
    }

    /*
    @brief choose mi-bests but w/ possibility (inversely proportional to place in ranking) of not choosing
     */
    private static ArrayList<Individual> ranking(ArrayList<Individual> bPopulation, ArrayList<Individual> rPopulation) {
        int lambda = rPopulation.size();
        int mi = bPopulation.size();
        double slice = 1.0 / (mi + lambda); //1 slice of circle
        rPopulation.addAll(bPopulation);
        rPopulation.sort(Comparator.comparing(Individual::getFit));

        Random random = new Random();
        ArrayList<Individual> newPopulation = new ArrayList(mi);
        do {
            for (int i = 0, slicesNum = 1; i < (mi + lambda) && newPopulation.size() != mi; ++i, ++slicesNum) {
                if (slice * slicesNum < random.nextDouble())
                    newPopulation.add(rPopulation.get(mi + lambda - i - 1));
            }
        } while (newPopulation.size() < mi);

        Solver.bestIndiv = newPopulation.get(0);
        return newPopulation;
    }
}
