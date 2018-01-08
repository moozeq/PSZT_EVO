package sample;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Ted on 06.01.2018.
 */
public class Indiv {
    double sigmaRange = 5.0;
    ArrayList<Double> coords = new ArrayList<Double>();
    ArrayList<Double> sigmas = new ArrayList<Double>();
    double fit;

    Indiv(int n, double min, double max) {
        Random random = new Random();
        random.doubles(n, min, max).forEach(s->coords.add(s));
        random.doubles(n, 0.0, sigmaRange).forEach(s->sigmas.add(s));
        fit = fitFun();
    }
    double getX(int index) {
        return coords.get(index);
    }
    double getSigma(int index) {
        return sigmas.get(index);
    }
    double getFit() {
        return fit;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Double x : coords)
            str.append(x + " ");
        return str.toString();
    }
    /*
    Function to get fitting value
     */
    public double fitFun() {
        double fitCos = 1.0;
        double fitE = 0.0;
        for (int i = 0; i < coords.size() - 1; ++i) {
            double x = getX(i);
            fitCos *= Math.cos(x);
            fitE += x*x / (double)(i+1);
        }
        return fitCos - 0.01*fitE;
    }
}
