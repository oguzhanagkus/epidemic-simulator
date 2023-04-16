package EpidemicSimulator.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum Direction {
    RIGHT, LEFT, DOWN, UP;

    private static final Random random = new Random();
    private static final List<Direction> directions = List.of(values());
    private static final int size = directions.size();

    public static Direction getRandomDirection()  {
        return directions.get(random.nextInt(size));
    }

    public static Direction getRandomDirection(Direction direction) {
        ArrayList<Direction> temp = new java.util.ArrayList<>(List.of(values()));
        temp.remove(direction);

        return temp.get(random.nextInt(temp.size()));
    }

    public static Direction getReverseDirection(Direction direction) {
        Direction temp = direction;
        switch (direction) {
            case RIGHT: temp = LEFT; break;
            case LEFT:  temp = RIGHT; break;
            case DOWN:  temp = UP; break;
            case UP:    temp = DOWN; break;
        }
        return temp;
    }
}