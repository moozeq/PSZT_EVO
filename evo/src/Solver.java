import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Ted on 09.01.2018.
 */
class Solver {
    private static Indiv bestIndiv;
    private static double bestFit; //best fitting
    private static int bestDuringGen; //how many generation bestIndiv was best
    private static int maxGenSameBestIndiv; //max generation w/ same best individual
    private static boolean stop; //=true when bestIndiv exceed maxGenSameBestIndiv
    /*
    Solve by (mi + lambda) algorithm, c = length between local max-s
     */
    public static Indiv solveMPL(ArrayList<Indiv> population, int mi, int lambda, double c, int maxRounds, int maxGenSameBest) {
        bestIndiv = population.get(0);
        bestFit = bestIndiv.getFit();
        bestDuringGen = 0;
        maxGenSameBestIndiv = maxGenSameBest;

        int generation;
        for (generation = 0, stop = false; generation < maxRounds && !stop; ++generation) { //stop when max rounds or max during max gen best's same indiv
            ArrayList<Indiv> tPopulation = new ArrayList(lambda);
            ArrayList<Indiv> rPopulation = new ArrayList(lambda);
            Random random = new Random();
            for (int i = 0; i < lambda; ++i)
                tPopulation.add(population.get(random.nextInt(mi)));
            for (int i = 0; i < lambda; ++i) {
                Indiv parentM = tPopulation.get(random.nextInt(lambda));
                Indiv parentF = tPopulation.get(random.nextInt(lambda));
                if (Indiv.chanceToReproduce(parentM, parentF, c) < random.nextDouble()) {
                    --i;
                    continue;
                }
                Indiv child = new Indiv(parentM, parentF);
                rPopulation.add(child);
            }
            population = miBests(population, rPopulation);
        }
        return bestIndiv;
    }
    /*
    Solve by evo programming algorithm
     */
    public static Indiv solveEP(ArrayList<Indiv> population, int mi, int maxRounds, int maxGenSameBest) {
        bestIndiv = population.get(0);
        bestFit = bestIndiv.getFit();
        bestDuringGen = 0;
        maxGenSameBestIndiv = maxGenSameBest;

        int generation;
        for (generation = 0, stop = false; generation < maxRounds && !stop; ++generation) { //stop when max rounds or max during max gen best's same indiv
            ArrayList<Indiv> mPopulation = new ArrayList(mi);
            for (int i = 0; i < mi; ++i) {
                Indiv child = new Indiv(population.get(i));
                mPopulation.add(child);
            }
            population = miBests(population, mPopulation);
        }
        return bestIndiv;
    }
    /*
    Choose who should be in next population
    actually - mi firsts
     */
    private static ArrayList<Indiv> miBests(ArrayList<Indiv> population, ArrayList<Indiv> rPopulation) {
        int lambda = rPopulation.size();
        int mi = population.size();
        population.addAll(rPopulation);
        population.sort(Comparator.comparing(Indiv::getFit));
        ArrayList<Indiv> newPopulation = new ArrayList(mi);
        for (int i = 0; i < mi; ++i)
            newPopulation.add(population.get(mi + lambda - i - 1));
        if (newPopulation.get(0).getFit() > bestFit) {
            bestDuringGen = 0;
            bestIndiv = newPopulation.get(0);
        }
        else
            ++bestDuringGen;

        if (bestDuringGen > maxGenSameBestIndiv) //same best individual for max generations
            stop = true;
        return newPopulation;
    }
}
