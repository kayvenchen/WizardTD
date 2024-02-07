package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;


/**
 * The Tower class represents a tower. Towers are used to defend against 
 * monsters by shooting fireballs. Towers can be upgraded to improve 
 * their range, firing speed, and damage.
 */
public class Tower {
    private int towerCost;
    private int initialTowerRange;
    private double initialTowerFiringSpeed;
    private int initialTowerDamage;

    private int rangeLevel;
    private int speedLevel;
    private int damageLevel;

    private PImage[] towerImages;
    private int towerImageIndex;

    ArrayList<Monster> monsters;
    ArrayList<Fireball> fireballs;

    private long lastFireTime; 
    
    private int x;
    private int y;

    /**
     * Creates a new Tower instance with the specified attributes.
     *
     * @param towerImages           An array of tower images for different upgrade levels.
     * @param towerCost             The initial cost of the tower.
     * @param TowerRange            The initial range of the tower.
     * @param TowerFiringSpeed      The initial firing speed of the tower.
     * @param initialTowerDamage    The initial damage of the tower.
     */
    public Tower(PImage[] towerImages, int towerCost, int TowerRange, double TowerFiringSpeed, int initialTowerDamage) {
        this.towerCost = towerCost;
        this.towerImages = towerImages;
        this.initialTowerRange = TowerRange;
        this.initialTowerFiringSpeed = TowerFiringSpeed;
        this.initialTowerDamage = initialTowerDamage;
    }

    /**
     * Creates a new Tower instance by copying an existing tower and placing it
     * at a specific position.
     *
     * @param original The original Tower instance to be copied.
     * @param x        The X-coordinate of the tower's position.
     * @param y        The Y-coordinate of the tower's position.
     */
    public Tower(Tower original, int x, int y) {
        this.x = x;
        this.y = y;

        this.towerImageIndex = 0;
        this.towerImages = original.towerImages;
        this.towerCost = original.towerCost;
        this.initialTowerRange = original.initialTowerRange;
        this.initialTowerFiringSpeed = original.initialTowerFiringSpeed;
        this.initialTowerDamage = original.initialTowerDamage;

        this.rangeLevel = 0;
        this.speedLevel = 0;
        this.damageLevel = 0;
    }

    /**
     * Gets the X-coordinate of the tower's position on the game grid.
     *
     * @return The X-coordinate of the tower.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y-coordinate of the tower's position on the game grid.
     *
     * @return The Y-coordinate of the tower.
     */
    public int getY() {
        return y;
    }

    /**
     * Upgrades the tower's range. This method increases the tower's effective range.
     */
    public void upgradeRange() {
        rangeLevel++;
    }

    /**
     * Upgrades the tower's firing speed. This method increases the tower's firing rate.
     */
    public void upgradeSpeed() {
        speedLevel++;
    }

    /**
     * Upgrades the tower's damage. This method increases the tower's damage output.
     */
    public void upgradeDamage() {
        damageLevel++;
    }

    /**
     * Gets the current upgrade level of the tower's range.
     *
     * @return The range upgrade level.
     */
    public int getRangeLevel() {
        return rangeLevel;
    }

    /**
     * Gets the current upgrade level of the tower's firing speed.
     *
     * @return The firing speed upgrade level.
     */
    public int getSpeedLevel() {
        return speedLevel;
    }

    /**
     * Gets the current upgrade level of the tower's damage.
     *
     * @return The damage upgrade level.
     */
    public int getDamageLevel() {
        return damageLevel;
    }

    /**
     * Calculates the cost of upgrading a particular attribute (range, speed, or damage)
     * to a specified level.
     *
     * @param level The desired upgrade level.
     * @return The cost of upgrading to the specified level.
     */
    public int getUpgradeCost(int level) {
        return 20 + (10 * level);
    }

    /**
     * Gets the initial cost of the tower.
     *
     * @return The initial cost of the tower.
     */
    public int getTowerCost() {
        return towerCost;
    }

    /**
     * Gets the effective range of the tower, taking into account range upgrades.
     *
     * @return The effective range of the tower.
     */
    public int getRange() {
        return initialTowerRange + (32 * (rangeLevel));
    }

    /**
     * Gets the firing speed of the tower, taking into account speed upgrades.
     *
     * @return The effective firing speed of the tower.
     */
    public double getFiringSpeed() {
        return initialTowerFiringSpeed + (0.5 * (speedLevel));
    }

    /**
     * Gets the tower's damage output, taking into account damage upgrades.
     *
     * @return The effective damage output of the tower.
     */
    public int getDamage() {
        return initialTowerDamage + (initialTowerDamage / 2) * (damageLevel);
    }

    /**
     * Calculates the time interval between two consecutive fireball shots by the tower.
     *
     * @return The time interval in milliseconds.
     */
    public double getFireInterval() {
        return 1000 / this.getFiringSpeed();
    }

    /**
     * Gets the timestamp of the last time the tower fired a fireball.
     *
     * @return The timestamp of the last fire.
     */
    public long getLastFireTime() {
        return lastFireTime;
    }

    /**
     * Sets the timestamp of the last time the tower fired a fireball.
     *
     * @param lastFireTime The timestamp of the last fire.
     */
    public void setLastFireTime(long lastFireTime) {
        this.lastFireTime = lastFireTime;
    }

    /**
     * Sets the tower's current image index to control its appearance based on upgrades.
     *
     * @param towerImageIndex The image index to set.
     */
    public void setTowerImageIndex(int towerImageIndex) {
        this.towerImageIndex = towerImageIndex;
    }

    /**
     * Draws the tower on the game screen with its current appearance and upgrades.
     *
     * @param app The PApplet instance for rendering.
     */
    public void draw(PApplet app) {
        int xOffset = this.x * App.CELLSIZE;
        int yOffset = this.y * App.CELLSIZE + App.TOPBAR;
        
        // set tower image
        if (rangeLevel >= 2 && speedLevel >= 2 && damageLevel >= 2) {
            this.towerImageIndex = 2;
        } else if (rangeLevel >= 1 && speedLevel >= 1 && damageLevel >= 1) {
            this.towerImageIndex = 1;
        } else {
            this.towerImageIndex = 0;
        }


        app.image(this.towerImages[towerImageIndex], xOffset, yOffset);

        if (speedLevel-towerImageIndex > 0) {
            app.stroke(136, 179, 249);  // Set fill color to blue
            app.noFill();
            // Calculate the border thickness based on speedLevel
            int borderThickness = 1 + speedLevel;  // Adjust the thickness as needed
            app.strokeWeight(borderThickness);
            // Draw the border
            app.rect(xOffset+6, yOffset+6, this.towerImages[towerImageIndex].height-12, this.towerImages[towerImageIndex].width-12);
        }
        
        for (int i = 0; i < rangeLevel-towerImageIndex; i++) {
            int xPosition = xOffset + i * 5;  // Alternate x position
            app.fill(227, 77, 239);  // Fill color (black)
            app.textSize(10); // Text size
            app.text("o", xPosition, yOffset + 5);
        }

        for (int i = 0; i < damageLevel-towerImageIndex; i++) {
            int xPosition = xOffset + i * 5;  // Alternate x position
            app.fill(227, 77, 239);  // Fill color (black)
            app.textSize(10); // Text size
            app.text("x", xPosition, yOffset + this.towerImages[towerImageIndex].height);
        }
    }  
}