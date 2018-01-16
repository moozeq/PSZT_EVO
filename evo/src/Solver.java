import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Ted on 09.01.2018.
 */
class Solver {
    public static int generation = 0;
    private static ArrayList<Indiv> population;
    private static Indiv bestIndiv;
    private static Indiv prevBestIndiv;
    /*
    Solve by (mi + lambda) algorithm, c = length between local max-s
     */
    public static Population solveMPL(Population pop, int lambda, double c, int maxRounds, double minFit) {
        Population sPopulation = new Population(pop);
        population = sPopulation.population;
        prevBestIndiv = bestIndiv = sPopulation.bestIndiv;
        int mi = population.size();

        for (generation = 0; generation < maxRounds && bestIndiv.getFit() < minFit; ++generation) {
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
            population = ranking(population, rPopulation);
            population.set(mi - 1, prevBestIndiv); //elitism
            prevBestIndiv = bestIndiv;
        }
        sPopulation.population = population;
        sPopulation.bestIndiv = bestIndiv;
        return sPopulation;
    }
    /*
    Solve by evo programming algorithm
     */
    public static Population solveEP(Population pop, int maxRounds, double minFit) {
        Population sPopulation = new Population(pop);
        population = sPopulation.population;
        prevBestIndiv = bestIndiv = sPopulation.bestIndiv;
        int mi = population.size();

        for (generation = 0; generation < maxRounds && bestIndiv.getFit() < minFit; ++generation) {
            ArrayList<Indiv> mPopulation = new ArrayList(mi);
            population
                    .parallelStream()
                    .forEach(e-> mPopulation.add(new Indiv(e, true)));
            population = ranking(population, mPopulation);
            population.set(mi - 1, prevBestIndiv); //elitism
            prevBestIndiv = bestIndiv;
        }
        sPopulation.population = population;
        sPopulation.bestIndiv = bestIndiv;
        return sPopulation;
    }

    private static ArrayList<Indiv> miBests(ArrayList<Indiv> bPopulation, ArrayList<Indiv> rPopulation) {
        int lambda = rPopulation.size();
        int mi = bPopulation.size();
        rPopulation.addAll(bPopulation);
        rPopulation.sort(Comparator.comparing(Indiv::getFit));

        ArrayList<Indiv> newPopulation = new ArrayList(mi);
        for (int i = 0; i < mi; ++i)
            newPopulation.add(bPopulation.get(mi + lambda - i - 1));

        bestIndiv = newPopulation.get(0);
        return newPopulation;
    }

    private static ArrayList<Indiv> ranking(ArrayList<Indiv> bPopulation, ArrayList<Indiv> rPopulation) {
        int lambda = rPopulation.size();
        int mi = bPopulation.size();
        double slice = 1.0 / (mi + lambda); //1 slice of circle
        rPopulation.addAll(bPopulation);
        rPopulation.sort(Comparator.comparing(Indiv::getFit));

        Random random = new Random();
        ArrayList<Indiv> newPopulation = new ArrayList(mi);
        do {
            for (int i = 0, slicesNum = 1; i < (mi + lambda) && newPopulation.size() != mi; ++i, ++slicesNum) {
                if (slice * slicesNum < random.nextDouble())
                    newPopulation.add(rPopulation.get(mi + lambda - i - 1));
            }
        } while (newPopulation.size() < mi);

        bestIndiv = newPopulation.get(0);
        return newPopulation;
    }
}
