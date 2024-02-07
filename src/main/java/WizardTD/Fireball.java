package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * The Fireball class represents a projectile fired by a tower in a tower defense game. 
 * It can move in a specified direction, deal damage upon impact, and has a limited range.
 * 
 */
public class Fireball {
    
    private float x;
    private float y;
    private float towerX;
    private float towerY;

    private boolean isFiring;

    private PImage sprite;
    private double direction;

    private double speed;
    private double currentSpeed;
    private int damage;
    private int range;
    
    /**
     * Constructs a new Fireball object with the specified sprite.
     * @param sprite The image representing the fireball.
     */
    public Fireball(PImage sprite) {
        this.sprite = sprite;
        this.isFiring = false;
    }
    
    /**
     * Sets the initial parameters for the fireball's flight.
     * @param x The starting X-coordinate of the fireball.
     * @param y The starting Y-coordinate of the fireball.
     * @param direction The direction (angle) at which the fireball is launched.
     * @param damage The damage the fireball inflicts on targets.
     * @param range The maximum range of the fireball's flight.
     */
    public void setStartLocation(float x, float y, double direction, int damage, int range) {
        if (!isFiring) {
            this.x = x;
            this.y = y;
            this.towerX = x;
            this.towerY = y;
            this.speed = 5;
            this.damage = damage;
            this.range = range;
            this.direction = direction;
            isFiring = true;
        }
    }
    
    /**
     * Updates the position of the fireball during its flight.
     */
    public void tick() {
        if (isFiring) {
            this.currentSpeed = speed;
            if (this.isInsideTowerRange(towerX, towerY)) {
                x += currentSpeed * Math.cos(direction);
                y += currentSpeed * Math.sin(direction);
            } else {
                this.resetFireball();
            }
        }
    }
    
    /**
     * Draws the fireball on the screen if it is currently in flight.
     * @param app The PApplet object for rendering.
     */
    public void draw(PApplet app) {
        if (isFiring) {
            app.image(this.sprite, this.x - this.sprite.width/2, this.y - this.sprite.height/2);
        }
    }
    
    /**
     * Gets the X-coordinate of the fireball.
     * @return The X-coordinate of the fireball.
     */
    public float getX() {
        return x;
    }
    
    /**
     * Gets the Y-coordinate of the fireball.
     * @return The Y-coordinate of the fireball.
     */
    public float getY() {
        return y;
    }
    
    /**
     * Gets the direction (angle) at which the fireball is launched.
     * @return The direction of the fireball's flight in radians.
     */
    public double getDirection() {
        return direction;
    }
    
    /**
     * Gets the maximum range of the fireball's flight.
     * @return The maximum range of the fireball's flight in pixels.
     */
    public int getRange() {
        return range;
    }
    
    /**
     * Gets the width of the fireball's sprite.
     * @return The width of the fireball's sprite.
     */
    public int getWidth() {
        return this.sprite.width;
    }
    
    /**
     * Gets the height of the fireball's sprite.
     * @return The height of the fireball's sprite.
     */
    public int getHeight() {
        return this.sprite.height;
    }
    
    /**
     * Gets the damage inflicted by the fireball.
     * @return The damage inflicted by the fireball.
     */
    public int getDamage() {
        return this.damage;
    }
    
    /**
     * Checks if the fireball is currently in flight.
     * @return True if the fireball is in flight, otherwise false.
     */
    public boolean isFiring() {
        return this.isFiring;
    }
    
    /**
     * Checks if the fireball is still within the range of its tower.
     * @param towerX The X-coordinate of the tower.
     * @param towerY The Y-coordinate of the tower.
     * @return True if the fireball is within range, otherwise false.
     */
    public boolean isInsideTowerRange(float towerX, float towerY) {
        if (isFiring) {
            float distance = PApplet.dist(x, y, towerX, towerY);
            return distance <= range;
        }
        return false;
    }
    
    /**
     * Resets the fireball's state to indicate that it is no longer in flight.
     */
    public void resetFireball() {
        currentSpeed = 0;
        isFiring = false;
    }
}
