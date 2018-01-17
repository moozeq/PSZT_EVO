import java.util.ArrayList;
import java.util.Random;

class Solver {
    private static ArrayList<Individual> population;
    public static int generation = 0;
    public static Individual bestIndiv;
    private static Individual prevBestIndiv;
    /*
    Solve by (mi + lambda) algorithm, c = length between local max-s
     */
    public static Population solveMPL(Population pop, int lambda, double c, int maxRounds, double minFit) {
        Population sPopulation = new Popul().newInstance(pop);
        population = sPopulation.getPopulation();
        prevBestIndiv = bestIndiv = sPopulation.getBestIndiv();
        int mi = population.size();

        for (generation = 0; generation < maxRounds && bestIndiv.getFit() < minFit; ++generation) {
            ArrayList<Individual> tPopulation = new ArrayList(lambda);
            ArrayList<Individual> rPopulation = new ArrayList(lambda);
            Random random = new Random();
            for (int i = 0; i < lambda; ++i)
                tPopulation.add(population.get(random.nextInt(mi)));
            for (int i = 0; i < lambda; ++i) {
                Individual parentM = tPopulation.get(random.nextInt(lambda));
                Individual parentF = tPopulation.get(random.nextInt(lambda));
                if (Individual.chanceToReproduce(parentM, parentF, c) < random.nextDouble()) {
                    --i;
                    continue;
                }
                Individual child =  new Indiv().newInstance(parentM, parentF);
                rPopulation.add(child);
            }
            population = Choosing.choose(population, rPopulation);
            population.set(mi - 1, prevBestIndiv); //elitism
            prevBestIndiv = bestIndiv;
            sPopulation.getBestsInGen().add(bestIndiv);
        }
        sPopulation.setPopulation(population);
        sPopulation.setBestIndiv(bestIndiv);
        sPopulation.getBestsInGen().add(bestIndiv);
        return sPopulation;
    }
    /*
    Solve by evo programming algorithm
     */
    public static Population solveEP(Population pop, int maxRounds, double minFit) {
        Population sPopulation = new Popul().newInstance(pop);
        population = sPopulation.getPopulation();
        prevBestIndiv = bestIndiv = sPopulation.getBestIndiv();
        int mi = population.size();

        for (generation = 0; generation < maxRounds && bestIndiv.getFit() < minFit; ++generation) {
            ArrayList<Individual> mPopulation = new ArrayList(mi);
            population
                    .parallelStream()
                    .forEach(e-> mPopulation.add(new Indiv().newInstance(e, true)));
            population = Choosing.choose(population, mPopulation);
            population.set(mi - 1, prevBestIndiv); //elitism
            prevBestIndiv = bestIndiv;
            sPopulation.getBestsInGen().add(bestIndiv);
        }
        sPopulation.setPopulation(population);
        sPopulation.setBestIndiv(bestIndiv);
        sPopulation.getBestsInGen().add(bestIndiv);
        return sPopulation;
    }
}
