package WizardTD;


/**
 * The `SpawnPoint` class represents a point on a game map where enemies can spawn.
 * It stores the x and y coordinates of the spawn point and the direction from which
 * enemies will appear.
 */
public class SpawnPoint {
    int x;
    int y;
    int direction;


    /**
     * Constructs a new SpawnPoint object with the specified coordinates and direction.
     *
     * @param x The x-coordinate of the spawn point.
     * @param y The y-coordinate of the spawn point.
     * @param direction The direction from which enemies will appear.
     */
    public SpawnPoint(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    /**
     * Get the x-coordinate of the spawn point.
     *
     * @return The x-coordinate of the spawn point.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Get the y-coordinate of the spawn point.
     *
     * @return The y-coordinate of the spawn point.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Get the direction from which enemies will appear at this spawn point.
     *
     * @return The direction of enemy spawn.
     */
    public int getDirection() {
        return direction;
    }
}
