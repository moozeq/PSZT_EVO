import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Choosing {
    public static ArrayList<Indiv> choose(ArrayList<Indiv> bPopulation, ArrayList<Indiv> rPopulation) {
        return ranking(bPopulation, rPopulation);
    }

    public static ArrayList<Indiv> chooseRand(ArrayList<Indiv> bPopulation, ArrayList<Indiv> rPopulation, int percentage) {
        int lambda = rPopulation.size();
        int mi = bPopulation.size();
        rPopulation.addAll(bPopulation);
        rPopulation.sort(Comparator.comparing(Indiv::getFit));

        Random random = new Random();
        ArrayList<Indiv> newPopulation = new ArrayList(mi);
        int bestsNum = percentage * mi / 100;
        int i = 0;
        while (i++ < bestsNum)
            newPopulation.add(rPopulation.get(mi + lambda - i - 1));
        while (i++ < mi)
            newPopulation.add(rPopulation.get(random.nextInt(mi + lambda)));

        Solver.bestIndiv = newPopulation.get(0);
        return newPopulation;
    }

    private static ArrayList<Indiv> miBests(ArrayList<Indiv> bPopulation, ArrayList<Indiv> rPopulation) {
        int lambda = rPopulation.size();
        int mi = bPopulation.size();
        rPopulation.addAll(bPopulation);
        rPopulation.sort(Comparator.comparing(Indiv::getFit));

        ArrayList<Indiv> newPopulation = new ArrayList(mi);
        for (int i = 0; i < mi; ++i)
            newPopulation.add(rPopulation.get(mi + lambda - i - 1));

        Solver.bestIndiv = newPopulation.get(0);
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

        Solver.bestIndiv = newPopulation.get(0);
        return newPopulation;
    }
}
