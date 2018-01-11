import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Ted on 09.01.2018.
 */
class Solver {
    private static Indiv bestIndiv;
    private static Indiv prevBestIndiv;
    /*
    Solve by (mi + lambda) algorithm, c = length between local max-s
     */
    public static Indiv solveMPL(Population pop, int mi, int lambda, double c, int maxRounds, double minFit) {
        prevBestIndiv = bestIndiv = pop.population.get(0);

        int generation;
        for (generation = 0; generation < maxRounds && bestIndiv.getFit() < minFit; ++generation) {
            ArrayList<Indiv> tPopulation = new ArrayList(lambda);
            ArrayList<Indiv> rPopulation = new ArrayList(lambda);
            Random random = new Random();
            for (int i = 0; i < lambda; ++i)
                tPopulation.add(pop.population.get(random.nextInt(mi)));
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
            pop.population = ranking(pop.population, rPopulation);
            pop.population.set(mi - 1, prevBestIndiv); //elitism
            prevBestIndiv = bestIndiv;
        }
        return bestIndiv;
    }
    /*
    Solve by evo programming algorithm
     */
    public static Indiv solveEP(ArrayList<Indiv> population, int mi, int maxRounds, double minFit) {
        prevBestIndiv = bestIndiv = population.get(0);

        int generation;
        for (generation = 0; generation < maxRounds && bestIndiv.getFit() < minFit; ++generation) {
            ArrayList<Indiv> mPopulation = new ArrayList(mi);
            population
                    .parallelStream()
                    .forEach(e-> mPopulation.add(new Indiv(e)));
            population = miBests(population, mPopulation);
            population.set(mi - 1, prevBestIndiv); //elitism
            prevBestIndiv = bestIndiv;
        }
        return bestIndiv;
    }

    private static ArrayList<Indiv> miBests(ArrayList<Indiv> population, ArrayList<Indiv> rPopulation) {
        int lambda = rPopulation.size();
        int mi = population.size();
        population.addAll(rPopulation);
        population.sort(Comparator.comparing(Indiv::getFit));

        ArrayList<Indiv> newPopulation = new ArrayList(mi);
        for (int i = 0; i < mi; ++i)
            newPopulation.add(population.get(mi + lambda - i - 1));

        bestIndiv = newPopulation.get(0);
        return newPopulation;
    }

    private static ArrayList<Indiv> ranking(ArrayList<Indiv> population, ArrayList<Indiv> rPopulation) {
        int lambda = rPopulation.size();
        int mi = population.size();
        double slice = 1.0 / (mi + lambda); //1 slice of circle
        population.addAll(rPopulation);
        population.sort(Comparator.comparing(Indiv::getFit));

        Random random = new Random();
        ArrayList<Indiv> newPopulation = new ArrayList(mi);
        do {
            for (int i = 0, slicesNum = 1; i < (mi + lambda) && newPopulation.size() != mi; ++i, ++slicesNum) {
                if (slice * slicesNum < random.nextDouble())
                    newPopulation.add(population.get(mi + lambda - i - 1));
            }
        } while (newPopulation.size() < mi);

        bestIndiv = newPopulation.get(0);
        return newPopulation;
    }
}
