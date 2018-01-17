class Fitness {
    /*
    Function to get fitting value
     */
    public static double fitFun(Indiv indiv) {
        double fitCos = 1.0;
        double fitE = 0.0;
        for (int i = 0; i < indiv.getSize(); ++i) {
            double x = indiv.getX(i);
            fitCos *= Math.cos(x);
            fitE += x*x / (double)(i+1);
        }
        return fitCos - 0.01*fitE;
        //return (fitCos - 0.01*fitE + 10) * 2;
    }
}
