/*
Class which represents fitness function
    fitFun - calculate fitness of passed Individual
 */
final class Fitness {
    /*
    Function to get fitting value
     */
    public static double fitFun(Individual individual) {
        double fitCos = 1.0;
        double fitE = 0.0;
        for (int i = 0; i < individual.getSize(); ++i) {
            double x = individual.getX(i);
            fitCos *= Math.cos(x);
            fitE += x*x / (double)(i+1);
        }
        return fitCos - 0.01*fitE; // cos(x1)*cos(x2)*cos(x3)*... - 0.01 * (x1^2 + x2^2/2 + x3^2/3 + ...)
    }
}
