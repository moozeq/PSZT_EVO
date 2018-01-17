import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.util.Duration;

/*
@brief Class which controls graphic interface of application
 */
public class Controller {
    @FXML private ScatterChart chart;
    @FXML private LineChart fitChart;

    @FXML private TextField miField;
    @FXML private TextField nField;
    @FXML private TextField sigmaField;
    @FXML private TextField lambdaField;
    @FXML private TextField cField;
    @FXML private TextField maxRoundsField;
    @FXML private TextField minFitnessField;
    @FXML private TextField x1Field;
    @FXML private TextField x2Field;
    @FXML private TextArea textArea;
    @FXML private Label dataGenerated;
    @FXML private CheckBox showChart;

    private Population population;
    private Population sPopulation;
    private final double min = -20.0;
    private final double max = 20.0;

    /*
    @brief called when wrong input
     */
    private void showAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.showAndWait();
    }

    /*
    @brief called when button "Generate new data" clicked
    generate base population from parameters passed by user
    */
    public void generateData() {
        try {
            int mi = Integer.parseInt(miField.getText());
            int n = Integer.parseInt(nField.getText());
            double sigmaRange = Double.parseDouble(sigmaField.getText());
            population = new Popul().newInstance(mi, n, min, max, sigmaRange);
            sPopulation = new Popul().newInstance(population);
            x1Field.setText("0");
            x2Field.setText("1");

            if (showChart.isSelected())
                showChart();
            if (fitChart.getData().size() > 0)
                fitChart.getData().clear();

            dataGenerated.setVisible(true);
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(1));
            visiblePause.setOnFinished(event -> dataGenerated.setVisible(false));
            visiblePause.play();
        } catch (NumberFormatException ne) {
            showAlert("Wrong parameters");
        }
    }

    /*
    @brief called when button "Solve MPL" clicked
    solve previously generated population by (mi + lambda) algorithm and show proper data on charts
     */
    public void solveMPL() {
        try {
            int lambda = Integer.parseInt(lambdaField.getText());
            double c = Double.parseDouble(cField.getText());
            int maxRounds = Integer.parseInt(maxRoundsField.getText());
            double minFitness = Double.parseDouble(minFitnessField.getText());
            long startTime = System.currentTimeMillis();
            sPopulation = Solver.solveMPL(population, lambda, c, maxRounds, minFitness);
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            showText(totalTime, 0);
            if (showChart.isSelected())
                showChart();
            showFitChart();
        } catch (NumberFormatException ne) {
            showAlert("Wrong parameters");
        }
    }

    /*
    @brief called when button "Solve EP" clicked
    solve previously generated population by evolutionary programing and show proper data on charts
     */
    public void solveEP() {
        try {
            int maxRounds = Integer.parseInt(maxRoundsField.getText());
            double minFitness = Double.parseDouble(minFitnessField.getText());
            long startTime = System.currentTimeMillis();
            sPopulation = Solver.solveEP(population, maxRounds, minFitness);
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            showText(totalTime, 1);
            if (showChart.isSelected())
                showChart();
            showFitChart();
        } catch (NumberFormatException ne) {
            showAlert("Wrong parameters");
        }
    }

    /*
    @brief shows brief info about time, generations and best individual in area text
     */
    public void showText(long totalTime, int type) {
        if (type == 0)
            textArea.setText("MPL time: " +  totalTime + "\nGenerations: " + Solver.generation +"\nFitness: " + sPopulation.getBestIndiv().getFit());
        else
            textArea.setText("EP time: " +  totalTime + "\nGenerations: " + Solver.generation + "\nFitness: " + sPopulation.getBestIndiv().getFit());
    }

    /*
    @brief shows population on chart in 2 dimensions - chosen by user
     */
    public void showChart() {
        if (chart.getData().size() > 0)
            chart.getData().clear();
        int x1 = Integer.parseInt(x1Field.getText());
        int x2 = Integer.parseInt(x2Field.getText());
        XYChart.Series series = new XYChart.Series();
        for (Individual aPopulation : sPopulation.getPopulation())
            series.getData().add(new XYChart.Data(aPopulation.getX(x1), aPopulation.getX(x2)));
        chart.getData().add(series);
    }

    /*
    @brief shows linear chart - best individual's fitness in every generation
     */
    public void showFitChart() {
        if (fitChart.getData().size() > 0)
            fitChart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        int i = 0;
        for (Individual aPopulation : sPopulation.getBestsInGen())
            series.getData().add(new XYChart.Data(i++, aPopulation.getFit()));
        fitChart.getData().add(series);
    }
}
