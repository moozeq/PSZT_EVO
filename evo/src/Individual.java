import java.util.ArrayList;

public interface Individual {
    Individual newInstance(int n, double min, double max, double sigmaRange);
    Individual newInstance(Individual parentM, Individual parentF);
    Individual newInstance(Individual individual, boolean isParent);
    double getFit();
    int getSize();
    int getDim();
    double getX(int index);
    double getSigma(int index);
    ArrayList<Double> getCoords();
    ArrayList<Double> getSigmas();
    /*
    Chance to reproduce
     */
    static double chanceToReproduce(Individual pParentM, Individual pParentF, double c) {
        double chance = 0;
        int dim = pParentM.getDim();
        for (int i = 0; i < dim; ++i)
            chance += 1.0 / (1.0 + Math.pow(pParentM.getX(i) - pParentF.getX(i), 2) / c);
        chance /= dim;
        return chance;
    }
}
