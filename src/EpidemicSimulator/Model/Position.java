package EpidemicSimulator.Model;

import javafx.scene.layout.Pane;
import java.util.Random;

public class Position {
    private int x;
    private int y;
    private static final Random random = new Random();

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Position getRandomPosition(Pane map, int iconSize) {
        int mapWidth = (int) map.getPrefWidth();
        int mapHeight = (int) map.getPrefHeight();

        int x = random.nextInt(mapWidth - iconSize + 1);
        int y = random.nextInt(mapHeight - iconSize + 1);

        return new Position(x, y);
    }
}
