import java.util.ArrayList;
import java.util.Random;

class Solver {
    public static int generation = 0;
    private static ArrayList<Indiv> population;
    public static Indiv bestIndiv;
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
            population = Choosing.choose(population, rPopulation);
            population.set(mi - 1, prevBestIndiv); //elitism
            prevBestIndiv = bestIndiv;
            sPopulation.bestInGen.add(bestIndiv);
        }
        sPopulation.population = population;
        sPopulation.bestIndiv = bestIndiv;
        sPopulation.bestInGen.add(bestIndiv);
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
            population = Choosing.choose(population, mPopulation);
            population.set(mi - 1, prevBestIndiv); //elitism
            prevBestIndiv = bestIndiv;
            sPopulation.bestInGen.add(bestIndiv);
        }
        sPopulation.population = population;
        sPopulation.bestIndiv = bestIndiv;
        sPopulation.bestInGen.add(bestIndiv);
        return sPopulation;
    }
}
