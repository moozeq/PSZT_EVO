import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;


public class Controller {
    @FXML
    private
    ScatterChart chart;
    @FXML
    private
    LineChart fitChart;

    @FXML
    private
    TextField miField;
    @FXML
    private
    TextField nField;
    @FXML
    private
    TextField sigmaField;
    @FXML
    private
    TextField lambdaField;
    @FXML
    private
    TextField cField;
    @FXML
    private
    TextField maxRoundsField;
    @FXML
    private
    TextField minFitnessField;
    @FXML
    private
    TextField x1Field;
    @FXML
    private
    TextField x2Field;
    @FXML
    private
    TextArea textArea;
    @FXML
    private
    Label dataGenerated;

    private Population population;
    private Population sPopulation;
    private final double min = -20.0;
    private final double max = 20.0;

    public void generateData() {
        int mi = Integer.parseInt(miField.getText());
        int n = Integer.parseInt(nField.getText());
        double sigmaRange = Double.parseDouble(sigmaField.getText());
        population = new Population(mi, n, min, max, sigmaRange);
        sPopulation = new Population(population);
        x1Field.setText("0");
        x2Field.setText("1");
        showChart();
        if (fitChart.getData().size() > 0)
            fitChart.getData().clear();

        dataGenerated.setVisible(true);
        PauseTransition visiblePause = new PauseTransition(Duration.seconds(1));
        visiblePause.setOnFinished(event -> dataGenerated.setVisible(false));
        visiblePause.play();
    }

    public void solveMPL() {
        int lambda = Integer.parseInt(lambdaField.getText());
        double c = Double.parseDouble(cField.getText());
        int maxRounds = Integer.parseInt(maxRoundsField.getText());
        double minFitness = Double.parseDouble(minFitnessField.getText());
        long startTime = System.currentTimeMillis();
        sPopulation = Solver.solveMPL(population, lambda, c, maxRounds, minFitness);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        showText(totalTime, 0);
        showChart();
        showFitChart();
    }

    public void solveEP() {
        int maxRounds = Integer.parseInt(maxRoundsField.getText());
        double minFitness = Double.parseDouble(minFitnessField.getText());
        long startTime = System.currentTimeMillis();
        sPopulation = Solver.solveEP(population, maxRounds, minFitness);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        showText(totalTime, 1);
        showChart();
        showFitChart();
    }

    public void showText(long totalTime, int type) {
        if (type == 0)
            textArea.setText("MPL time: " +  totalTime + "\nGenerations: " + Solver.generation +"\nFitness: " + sPopulation.bestIndiv.getFit());
        else
            textArea.setText("EP time: " +  totalTime + "\nGenerations: " + Solver.generation + "\nFitness: " + sPopulation.bestIndiv.getFit());
    }

    public void showChart() {
        if (chart.getData().size() > 0)
            chart.getData().clear();
        int x1 = Integer.parseInt(x1Field.getText());
        int x2 = Integer.parseInt(x2Field.getText());
        XYChart.Series series = new XYChart.Series();
        for (Indiv aPopulation : sPopulation.population)
            series.getData().add(new XYChart.Data(aPopulation.getX(x1), aPopulation.getX(x2)));
        chart.getData().add(series);
    }

    public void showFitChart() {
        if (fitChart.getData().size() > 0)
            fitChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < sPopulation.bestInGen.size(); ++i)
            series.getData().add(new XYChart.Data(i, sPopulation.bestInGen.get(i).getFit()));
        fitChart.getData().add(series);
    }
}
