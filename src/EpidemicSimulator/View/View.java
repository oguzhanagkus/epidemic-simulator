package EpidemicSimulator.View;

import EpidemicSimulator.Controller.Controller;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class View {
    private Controller controller;
    private final Pane window;
    private VBox controlPane;
    private GridPane statisticsPane;
    private Pane map;

    private Slider populationSlider;
    private Slider maskSlider;
    private Slider spreadingFactorSlider;
    private Slider mortalityRateSlider;

    private Button startStopButton;
    private Button pauseContinueButton;

    private Label timeData;
    private Label healthyData;
    private Label infectedData;
    private Label hospitalizedData;
    private Label deadData;

    private boolean started;
    private boolean paused;

    public View() {
        window = new Pane();
        HBox container = new HBox();
        VBox leftPane = new VBox();

        initControlPane();
        initStatisticsPane();
        initMap();

        leftPane.getChildren().addAll(controlPane, statisticsPane);
        container.getChildren().addAll(leftPane, map);
        window.getChildren().add(container);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Pane getWindow() {
        return window;
    }

    public Pane getMap() {
        return map;
    }

    public void updateStatistics(int time, int healthy, int infected, int hospitalized, int dead) {
        timeData.setText(String.valueOf(time));
        healthyData.setText(String.valueOf(healthy));
        infectedData.setText(String.valueOf(infected));
        hospitalizedData.setText(String.valueOf(hospitalized));
        deadData.setText(String.valueOf(dead));
    }

    private void initControlPane() {
        Label title = new Label("Epidemic Simulator");

        controlPane = new VBox();
        controlPane.setMinWidth(300);
        controlPane.setSpacing(30);
        controlPane.setPadding(new Insets(30, 30, 30 ,30));
        controlPane.setAlignment(Pos.CENTER);
        controlPane.getChildren().addAll(title, new Separator());

        initSliders();
        initButtons();
    }

    private void initSliders() {
        VBox sliderContainer = new VBox();
        sliderContainer.setAlignment(Pos.CENTER);
        sliderContainer.setMinWidth(300);
        sliderContainer.setSpacing(10);

        populationSlider = new Slider(100, 1100, 600);
        populationSlider.setShowTickLabels(true);
        populationSlider.setShowTickMarks(true);
        populationSlider.setSnapToTicks(true);
        populationSlider.setMajorTickUnit(250);
        populationSlider.setMinorTickCount(4);

        maskSlider = new Slider(0, 100, 50);
        maskSlider.setShowTickLabels(true);
        maskSlider.setShowTickMarks(true);
        maskSlider.setSnapToTicks(true);
        maskSlider.setMajorTickUnit(25);
        maskSlider.setMinorTickCount(4);

        spreadingFactorSlider = new Slider(0.5, 1.0, 0.75);
        spreadingFactorSlider.setShowTickLabels(true);
        spreadingFactorSlider.setShowTickMarks(true);
        spreadingFactorSlider.setSnapToTicks(true);
        spreadingFactorSlider.setMajorTickUnit(0.1);
        spreadingFactorSlider.setMinorTickCount(9);

        mortalityRateSlider = new Slider(0.1, 0.9, 0.5);
        mortalityRateSlider.setShowTickLabels(true);
        mortalityRateSlider.setShowTickMarks(true);
        mortalityRateSlider.setSnapToTicks(true);
        mortalityRateSlider.setMajorTickUnit(0.4);
        mortalityRateSlider.setMinorTickCount(4);

        Label populationData = new Label("Population: " + (int) populationSlider.getValue());
        Label maskData = new Label("Mask: " + (int) maskSlider.getValue() + "%");
        Label spreadingFactorData = new Label("Spreading factor: " + spreadingFactorSlider.getValue());
        Label mortalityRateData = new Label("Mortality rate: " + mortalityRateSlider.getValue());

        populationSlider.valueProperty().addListener((observable, oldValue, newValue) -> populationData.setText("Population: " + newValue.intValue()));
        maskSlider.valueProperty().addListener((observable, oldValue, newValue) -> maskData.setText("Mask: " + newValue.intValue() + "%"));
        spreadingFactorSlider.valueProperty().addListener((observable, oldValue, newValue) -> spreadingFactorData.setText(String.format("Spreading factor: %.2f" , newValue.doubleValue())));
        mortalityRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> mortalityRateData.setText(String.format("Mortality rate: %.1f" , newValue.doubleValue())));

        sliderContainer.getChildren().addAll(populationData, populationSlider);
        sliderContainer.getChildren().addAll(maskData, maskSlider);
        sliderContainer.getChildren().addAll(spreadingFactorData, spreadingFactorSlider);
        sliderContainer.getChildren().addAll(mortalityRateData, mortalityRateSlider);

        controlPane.getChildren().addAll(sliderContainer, new Separator());
    }

    private void initButtons() {
        started = false;
        paused = false;

        startStopButton = new Button("Start");
        startStopButton.setMinWidth(100);
        startStopButton.setOnMousePressed(e -> startStopButtonPressed());

        pauseContinueButton = new Button("Pause");
        pauseContinueButton.setMinWidth(100);
        pauseContinueButton.setOnMousePressed(e -> pauseContinueButtonPressed());
        pauseContinueButton.setDisable(true);

        HBox buttonContainer = new HBox();
        buttonContainer.setPadding(new Insets(0, 40, 0, 40));
        buttonContainer.setMinWidth(300);
        buttonContainer.setSpacing(20);
        buttonContainer.getChildren().addAll(startStopButton, pauseContinueButton);

        controlPane.getChildren().addAll(buttonContainer, new Separator());
    }

    private void initMap() {
        map = new Pane();
        map.setPrefSize(1600, 1000);
    }

    private void initStatisticsPane() {
        statisticsPane = new GridPane();
        statisticsPane.setMinWidth(300);
        statisticsPane.setVgap(20);
        statisticsPane.setHgap(20);
        statisticsPane.setPadding(new Insets(0, 0, 0 ,40));

        timeData = new Label("0");
        healthyData = new Label("0");
        infectedData = new Label("0");
        hospitalizedData = new Label("0");
        deadData = new Label("0");

        statisticsPane.add(new Label("Time: "), 0, 0);
        statisticsPane.add(timeData, 1, 0);
        statisticsPane.add(new Label("Healthy:"), 0, 1);
        statisticsPane.add(healthyData, 1, 1);
        statisticsPane.add(new Label("Hospitalized:"), 0, 2);
        statisticsPane.add(hospitalizedData, 1, 2);
        statisticsPane.add(new Label("Infected:"), 0, 3);
        statisticsPane.add(infectedData, 1, 3);
        statisticsPane.add(new Label("Dead:"), 0, 4);
        statisticsPane.add(deadData, 1, 4);
    }

    private void startStopButtonPressed() {
        startStopButton.setDisable(true);

        if (started) { // Stop
            startStopButton.setText("Start");
            pauseContinueButton.setDisable(true);
            controller.stop_();
        }
        else { // Start
            startStopButton.setText("Stop");
            pauseContinueButton.setText("Pause");
            paused = false;
            pauseContinueButton.setDisable(false);
            controller.start_((int) populationSlider.getValue(), (int) maskSlider.getValue(),
                                    spreadingFactorSlider.getValue(), mortalityRateSlider.getValue());
        }

        started = !started;
        startStopButton.setDisable(false);
    }

    private void pauseContinueButtonPressed() {
        pauseContinueButton.setDisable(true);

        if (paused) { // Continue
            pauseContinueButton.setText("Pause");
            controller.continue_();
        }
        else { // Pause
            pauseContinueButton.setText("Continue");
            controller.pause_();
        }

        paused = !paused;
        pauseContinueButton.setDisable(false);
    }
}