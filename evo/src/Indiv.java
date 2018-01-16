import java.util.ArrayList;
import java.util.Random;

/*
Class Indiv represents individual in population
 */
public class Indiv {
    private ArrayList<Double> coords = new ArrayList<>();
    private ArrayList<Double> sigmas = new ArrayList<>();
    private final double fit;

    Indiv(int n, double min, double max, double sigmaRange) {
        Random random = new Random();
        random.doubles(n, min, max).forEach(s->coords.add(s));
        random.doubles(n, 0.0, sigmaRange).forEach(s->sigmas.add(s));
        fit = fitFun();
    }
    Indiv(Indiv parentM, Indiv parentF) {
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
        fit = fitFun();
    }

    Indiv(Indiv indiv, boolean isParent) {
        if (!isParent) { //simply copy individual
            coords = new ArrayList<>(indiv.coords);
            sigmas = new ArrayList<>(indiv.sigmas);
            fit = indiv.getFit();
        } else { //reproduce individual from parent
            Random random = new Random();
            int dim = indiv.getDim();
            double t = 1 / Math.sqrt(2 * dim);
            double t1 = 1 / Math.sqrt(2 * Math.sqrt(dim));
            double ksi = random.nextGaussian();
            for (int i = 0; i < dim; ++i) {
                double sigma = indiv.getSigma(i) * (1 + t1 * ksi) * (1 + t * random.nextGaussian());
                double x = indiv.getX(i) + sigma * random.nextGaussian();
                sigmas.add(sigma);
                coords.add(x);
            }
            fit = fitFun();
        }
    }
    public double getX(int index) { return coords.get(index); }
    public double getSigma(int index) {
        return sigmas.get(index);
    }
    public double getFit() {
        return fit;
    }
    public int getDim() {
        return coords.size();
    }
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Double x : coords)
            str.append(x).append(" ");
        return str.toString();
    }
    /*
    Chance to reproduce
     */
    public static double chanceToReproduce(Indiv pParentM, Indiv pParentF, double c) {
        double chance = 0;
        int dim = pParentM.getDim();
        for (int i = 0; i < dim; ++i)
            chance += 1.0 / (1.0 + Math.pow(pParentM.getX(i) - pParentF.getX(i), 2) / c);
        chance /= dim;
        return chance;
    }
    /*
    Function to get fitting value
     */
    private double fitFun() {
        double fitCos = 1.0;
        double fitE = 0.0;
        for (int i = 0; i < coords.size(); ++i) {
            double x = getX(i);
            fitCos *= Math.cos(x);
            fitE += x*x / (double)(i+1);
        }
        return fitCos - 0.01*fitE;
        //return (fitCos - 0.01*fitE + 10) * 2;
    }
}
