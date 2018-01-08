package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Evolution evo = new Evolution();
        evo.generate(10, 2 , -20.0, 20.0);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
