package EpidemicSimulator.Controller;

import EpidemicSimulator.Model.Simulation;
import EpidemicSimulator.View.View;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class Controller {
    private final View view;
    private Pane map;
    private Simulation simulation;
    private Movement clock;
    private int time = 0;

    private class Movement extends AnimationTimer {
        private long INTERVAL = 1000000000L;

        private long last = 0;

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL) {
                step();
                last = now;
                time++;
            }
        }
    }

    public Controller(View view) {
        this.view = view;
        this.view.setController(this);
        this.map = view.getMap();
        this.clock = new Movement();
    }

    public void step() {
        simulation.move();
        simulation.feelBetter();
        simulation.checkCollisions();
        simulation.draw();
        simulation.updateStatistics(view, time);
    }

    public void start_(int population, int mask, double spreadingFactor, double mortalityRate) {
        map.getChildren().clear();
        simulation = new Simulation(map, population, mask, spreadingFactor, mortalityRate);
        clock.start();
    }

    public void stop_() {
        time = 0;
        clock.stop();
    }


    public void continue_() {
        clock.start();
    }

    public void pause_() {
        clock.stop();
    }
}