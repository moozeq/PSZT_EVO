import java.util.ArrayList;
import java.util.Random;

/*
Class Indiv represents individual in population
 */
public class Indiv implements Individual {
    private ArrayList<Double> coords = new ArrayList<>();
    private ArrayList<Double> sigmas = new ArrayList<>();
    private double fit;

    @Override
    public Individual newInstance(int n, double min, double max, double sigmaRange) {
        Random random = new Random();
        random.doubles(n, min, max).forEach(s->coords.add(s));
        random.doubles(n, 0.0, sigmaRange).forEach(s->sigmas.add(s));
        fit = Fitness.fitFun(this);
        return this;
    }
    @Override
    public Individual newInstance(Individual parentM, Individual parentF) {
        Random random = new Random();
        int dim = parentM.getDim();
        double t = 1 / Math.sqrt(2*dim);
        double t1 = 1 / Math.sqrt(2*Math.sqrt(dim));
        double ksi = random.nextGaussian();
        for (int i = 0; i < dim; ++i) {
            double embryoX = (parentM.getX(i) + parentF.getX(i)) / 2.0;
            double embryoSig = (parentM.getSigma(i) + parentF.getSigma(i)) / 2.0;
            double sigma = embryoSig * ( 1 + t1 * ksi) * (1 + t * random.nextGaussian());
            double x = embryoX + sigma * random.nextGaussian();
            sigmas.add(sigma);
            coords.add(x);
        }
        fit = Fitness.fitFun(this);
        return this;
    }
    @Override
    public Individual newInstance(Individual individual, boolean isParent) {
        if (!isParent) { //simply copy individual
            coords = new ArrayList<>(individual.getCoords());
            sigmas = new ArrayList<>(individual.getSigmas());
            fit = individual.getFit();
        } else { //reproduce individual from parent
            Random random = new Random();
            int dim = individual.getDim();
            double t = 1 / Math.sqrt(2 * dim);
            double t1 = 1 / Math.sqrt(2 * Math.sqrt(dim));
            double ksi = random.nextGaussian();
            for (int i = 0; i < dim; ++i) {
                double sigma = individual.getSigma(i) * (1 + t1 * ksi) * (1 + t * random.nextGaussian());
                double x = individual.getX(i) + sigma * random.nextGaussian();
                sigmas.add(sigma);
                coords.add(x);
            }
            fit = Fitness.fitFun(this);
        }
        return this;
    }

    public double getSigma(int index) { return sigmas.get(index); }
    public ArrayList<Double> getCoords() { return coords; }
    public ArrayList<Double> getSigmas() { return sigmas; }
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Double x : coords)
            str.append(x).append(" ");
        return str.toString();
    }

    @Override
    public double getX(int index) { return coords.get(index); }
    @Override
    public int getSize() { return coords.size(); }
    @Override
    public double getFit() {
        return fit;
    }
    @Override
    public int getDim() {
        return coords.size();
    }
}
