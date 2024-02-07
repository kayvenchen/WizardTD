package WizardTD;

import java.util.ArrayList;

/**
 * The Wave class represents a wave of monsters in a tower defense game.
 * Each wave has a pre-wave pause duration, a total duration for the wave,
 * a list of monster types to spawn, and the quantity of each monster type to spawn.
 */
public class Wave {
    private double preWavePause;
    private int duration;
    private ArrayList<Monster> monsters;
    private int quantity;

    /**
     * Constructs a new Wave object.
     *
     * @param preWavePause The duration of the pause before the wave starts.
     * @param duration The total duration of the wave.
     * @param monsters The list of Monster objects to spawn in the wave.
     * @param quantity The quantity of each monster type to spawn in the wave.
     */
    public Wave(double preWavePause, int duration, ArrayList<Monster> monsters, int quantity) {
        this.preWavePause = preWavePause;
        this.duration = duration;
        this.monsters = monsters;
        this.quantity = quantity;
    }

    /**
     * Get the duration of the pre-wave pause.
     *
     * @return The duration of the pre-wave pause.
     */
    public double getPreWavePause() {
        return preWavePause;
    }

    /**
     * Get the total duration of the wave.
     *
     * @return The total duration of the wave.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Get the list of Monster objects to spawn in the wave.
     *
     * @return The list of Monster objects to spawn.
     */
    public ArrayList<Monster> getMonstersToSpawn() {
        return monsters;
    }

    /**
     * Get the quantity of each monster type to spawn in the wave.
     *
     * @return The quantity of each monster type to spawn.
     */
    public int getQuantity() {
        return quantity;
    }
}