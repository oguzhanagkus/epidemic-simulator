package EpidemicSimulator;

import EpidemicSimulator.Controller.Controller;
import EpidemicSimulator.Model.Model;
import EpidemicSimulator.View.View;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class App extends Application {
    View view;
    Controller controller;

    @Override
    public void start(Stage stage) {
        this.view = new View();
        this.controller = new Controller(view);

        stage.setTitle("Epidemic Simulator");
        stage.setScene(new Scene(view.getWindow(), 1900, 1000));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}