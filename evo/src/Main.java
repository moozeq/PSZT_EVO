import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Population evo = new Population();
        int mi = 20;
        int lambda = 80;
        int n = 100;
        int maxRounds = 3000;
        int maxGen = 10;
        double min = -20.0;
        double max = 20.0;
        double sigmaRange = 0.5;
        double c = 5.5;
        while (true) {
            evo.generate4MPL(mi, lambda, n, min, max, sigmaRange);

            long startTime = System.currentTimeMillis();
            evo.bestIndiv = Solver.solveMPL(evo.population, mi, lambda, c, maxRounds, maxGen);
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            System.out.println("Total time: " + totalTime +
                    "\nBest Indiv: " + evo.bestIndiv.getFit());
            //c += 1.0;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
