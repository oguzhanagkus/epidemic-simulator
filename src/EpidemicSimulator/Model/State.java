package EpidemicSimulator.Model;

import javafx.scene.paint.Color;

public enum State {
    HEALTHY (Color.GREEN),
    HOSPITALIZED (Color.BLUE),
    INFECTED (Color.RED),
    DEAD (Color.BLACK);

    public Color color;

    State(Color color) {
        this.color = color;
    }
}