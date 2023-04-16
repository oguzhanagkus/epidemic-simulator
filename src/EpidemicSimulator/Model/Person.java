package EpidemicSimulator.Model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import java.util.Random;

public class Person {
    private final int size = 5;

    private final Random random = new Random();
    private final Rectangle icon;
    private final Pane map;
    private State state;
    private Position position;
    private Direction direction;
    private final int speed;
    private final int distance;
    private final int duration;
    private final double mask;
    private boolean inCollision;
    private int collisionTime = 0;
    private int infectedTime = 0;

    public Person(boolean mask, Pane map) {
        this.state = State.HEALTHY;
        this.position = Position.getRandomPosition(map, size);
        this.direction = Direction.getRandomDirection();
        this.speed = getRandomSpeed();
        this.distance = getRandomDistance();
        this.duration = getRandomDuration();
        this.mask = mask ? 0.2 : 1.0;
        this.inCollision = false;
        this.icon = new Rectangle(size, size, state.color);
        this.map = map;
        this.map.getChildren().add(icon);
    }

    private int getRandomDuration() {
        return random.nextInt(5) + 1;
    }

    private int getRandomDistance() {
        return random.nextInt(10);
    }

    private int getRandomSpeed() {
        return random.nextInt(500) + 1;
    }

    private boolean isInCollision() {
        return inCollision;
    }

    private boolean edgeCheck() {
        boolean temp = false;
        switch (direction) {
            case RIGHT:
                if (position.getX() + speed + size > map.getPrefWidth()) {
                    temp = true;
                }
                break;
            case LEFT:
                if (position.getX() - speed < 0) {
                    temp = true;
                }
                break;
            case DOWN:
                if (position.getY() + speed + size > map.getPrefHeight()) {
                    temp = true;
                }
                break;
            case UP:
                if (position.getY() - speed < 0) {
                    temp = true;
                }
                break;
        }
        return temp;
    }

    private void edgeControl() {
        if (edgeCheck()) {
            direction = Direction.getRandomDirection(direction);
            if (edgeCheck()) {
                direction = Direction.getReverseDirection(direction);
            }
        }
    }

    public void setState(State state) {
        this.state = state;
        icon.setFill(state.color);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setCollision(int time) {
        inCollision = true;
        collisionTime = time;
    }

    public State getState() {
        return state;
    }

    public Position getPosition() {
        return position;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public double getMask() {
        return mask;
    }

    public void move() {
        if (inCollision) {
            collisionTime--;
            if (collisionTime == 0) {
                inCollision = false;
                move();
            }
        }
        else {
            edgeControl();
            switch (direction) {
                case RIGHT: position.setX(position.getX() + speed); break;
                case LEFT:  position.setX(position.getX() - speed); break;
                case DOWN:  position.setY(position.getY() + speed); break;
                case UP:    position.setY(position.getY() - speed); break;
            }
        }
    }

    public void draw() {
        icon.setTranslateX(position.getX());
        icon.setTranslateY(position.getY());
    }

    public void collisionCheck(Person other) {
        if (other.isInCollision()) {
            return;
        }
        else {
            int socialDistance = Math.min(distance, other.getDistance());

            if ((Math.abs(position.getX() - other.getPosition().getX()) - size < socialDistance) &&
                    (Math.abs(position.getY() - other.getPosition().getY()) - size < socialDistance)) {

                int time = Math.max(duration, other.getDuration());

                this.setCollision(time);
                other.setCollision(time);

                if (other.getState() == State.INFECTED && state == State.HEALTHY) {
                    setState(State.INFECTED);
                }
                else if (other.getState() == State.HEALTHY && state == State.INFECTED) {
                    other.setState(State.INFECTED);
                }
            }
        }
    }

    public void feelBetter(int deathTime) {
        if (state == State.INFECTED) {
            infectedTime++;
            if (infectedTime > 25) {
                setState(state.HOSPITALIZED);
            }
            else if (infectedTime > deathTime) {
                setState(state.DEAD);
                icon.setVisible(false);
            }
        }
    }
}