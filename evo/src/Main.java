import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Population evoMPL = new Population();

        int mi = 20;
        int lambda = 80;
        int n = 100;
        int maxRounds = 3000;
        int maxGen = 10;
        double min = -20.0;
        double max = 20.0;
        double sigmaRange = 0.5;
        double c = 0.5;

        while (true) {
            evoMPL.generate4MPL(mi, lambda, n, min, max, sigmaRange);
            Population evoEP = new Population(evoMPL);

            long startTime = System.currentTimeMillis();
            evoMPL.bestIndiv = Solver.solveMPL(evoMPL.population, mi, lambda, c, maxRounds, maxGen);
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            System.out.println("Evo MPL\nTotal time: " + totalTime +
                    "\nBest Indiv: " + evoMPL.bestIndiv.getFit());


            startTime = System.currentTimeMillis();
            evoEP.bestIndiv = Solver.solveEP(evoEP.population, mi, maxRounds, maxGen);
            endTime = System.currentTimeMillis();
            totalTime = endTime - startTime;

            System.out.println("Evo EP\nTotal time: " + totalTime +
                    "\nBest Indiv: " + evoEP.bestIndiv.getFit());
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
