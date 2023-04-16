package EpidemicSimulator.Model;

import EpidemicSimulator.View.View;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.Random;

public class Simulation {
    private final ArrayList<Person> people;
    private final double spreadingFactor;
    private final double mortalityRate;
    private final int deathTime;

    public Simulation(Pane map, int population, int mask, double spreadingFactor, double mortalityRate) {
        this.spreadingFactor = spreadingFactor;
        this.mortalityRate = mortalityRate;
        this.deathTime = (int) (100 * (1.0 - mortalityRate));

        int maskedPeople = population * mask / 100;
        int unmaskedPeople = population - maskedPeople;

        people = new ArrayList<>();

        for (int i = 0; i < maskedPeople; i++) {
            people.add(new Person(true, map));
        }

        for (int i = 0; i < unmaskedPeople; i++) {
            people.add(new Person(false, map));
        }

        Random random = new Random();
        people.get(random.nextInt(people.size())).setState(State.INFECTED);
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void move() {
        for (Person p: people) {
            p.move();
        }
    }

    public void draw() {
        for (Person p: people) {
            p.draw();
        }
    }

    public void checkCollisions() {
        for (Person p : people) {
            for (Person q : people) {
                if (p != q) {
                    p.collisionCheck(q);
                }
            }
        }
    }

    public void feelBetter() {
        for (Person p : people) {
            p.feelBetter(deathTime);
        }
    }

    public void updateStatistics(View view, int t) {
        int healthy = 0;
        int infected = 0;
        int hospitalized = 0;
        int dead = 0;

        State temp;

        for (Person p : people) {
            temp = p.getState();

            switch (temp) {
                case HEALTHY: healthy++; break;
                case INFECTED: infected++; break;
                case HOSPITALIZED: hospitalized++; break;
                case DEAD: dead++; break;
            }
        }

        view.updateStatistics(t, healthy, infected, hospitalized, dead);
    }
}