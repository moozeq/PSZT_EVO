import java.util.ArrayList;

/*
@bried Interface which must be provided to use algorithms
 */
public interface Individual {
    /*
    @brief creating new individual based on parameters
     */
    Individual newInstance(int n, double min, double max, double sigmaRange);

    /*
    @brief creating new individual from parents by crossing and mutating
     */
    Individual newInstance(Individual parentM, Individual parentF);

    /*
    @brief creating new individual from parent or not
    @param isParent - if true - create new individual by mutating parent, if false - just copy individual
     */
    Individual newInstance(Individual individual, boolean isParent);

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
    double getFit();
    int getSize();
    int getDim();
    double getX(int index);
    double getSigma(int index);
    ArrayList<Double> getCoords();
    ArrayList<Double> getSigmas();

}
