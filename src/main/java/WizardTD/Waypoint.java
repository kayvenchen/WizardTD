package WizardTD;


/**
 * The Waypoint class represents a point with a direction in a 2D space.
 * It is used to define movement directions.
 */
public class Waypoint {
    
    private char direction;
    private float x;
    private float y;

    /**
     * Constructs a Waypoint object with the specified direction, x, and y coordinates.
     *
     * @param direction The direction of movement ('U' for up, 'D' for down, 'L' for left, 'R' for right).
     * @param x The X-coordinate of the waypoint in 2D space.
     * @param y The Y-coordinate of the waypoint in 2D space.
     */
    public Waypoint(char direction, float x, float y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }
    

    /**
     * Gets the direction as a letter.
     *
     * @return The direction angle in degrees (R for right, D for down, L for left, U for up).
     */
    public char getDirection() {
        return direction;
    }

    /**
     * Gets the direction as an angle in degrees.
     *
     * @return The direction angle in degrees (0 for right, 90 for down, 180 for left, 270 for up).
     */
    public int getDirectionAsAngle() {
        if (direction == 'U') {
            return 270;
        }
        else if (direction == 'D') {
            return 90;
        }
        if (direction == 'L') {
            return 180;
        }
        else if (direction == 'R') {
            return 0;
        }
        return 0;
    }
    
    /**
     * Gets the X-coordinate of the waypoint in 2D space.
     *
     * @return The X-coordinate of the waypoint.
     */
    public float getX() {
        return x;
    }
    
    /**
     * Gets the Y-coordinate of the waypoint in 2D space.
     *
     * @return The Y-coordinate of the waypoint.
     */
    public float getY() {
        return y;
    }
}
