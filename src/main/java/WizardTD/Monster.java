package WizardTD;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;


/**
 * The Monster class represents an enemy unit in the Wizard Tower Defense game.
 * It has attributes such as health points (hp), speed, armor, and more. Monsters
 * move along waypoints towards a wizard's house and can be affected by fireballs.
 */
public class Monster {
    private PImage sprite;
    private int hp;
    private int currentHp;
    private double speed;
    private double armour;
    private int manaGainedOnKill;
    private double direction;

    
    private Tile wizardHouse;
    private ArrayList<Waypoint> waypoints;
    private ArrayList<Fireball> fireballs;

    private boolean isAlive;
    private boolean isBanished;

    private float x;
    private float y;
    

    /**
     * Constructs a new Monster instance with the specified parameters.
     *
     * @param sprite             The image representing the monster's appearance.
     * @param hp                 The initial health points of the monster.
     * @param speed              The movement speed of the monster.
     * @param armour             The armor value of the monster.
     * @param manaGainedOnKill   The amount of mana gained when the monster is killed.
     * @param wizardHouse        The wizard's house tile, which is the destination of the monster.
     */
    public Monster(PImage sprite, int hp, double speed, double armour, int manaGainedOnKill, Tile wizardHouse) {
        this.sprite = sprite;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.manaGainedOnKill = manaGainedOnKill;
        this.wizardHouse = wizardHouse;
    }

    /**
     * Constructs a new Monster instance by cloning an existing monster.
     *
     * @param original   The original monster to clone.
     * @param x          The x-coordinate of the cloned monster's position.
     * @param y          The y-coordinate of the cloned monster's position.
     * @param direction  The direction of movement for the cloned monster.
     * @param waypoints  The list of waypoints the monster follows.
     * @param fireballs  The list of fireballs in the game.
     */
    public Monster(Monster original, float x, float y, double direction, ArrayList<Waypoint> waypoints, ArrayList<Fireball> fireballs) {
        // Copy the properties from the original monster
        
        this.hp = original.hp;
        this.currentHp = original.hp;
        this.sprite = original.sprite;
        this.speed = original.speed;
        this.armour = original.armour;
        this.manaGainedOnKill = original.manaGainedOnKill;
        this.wizardHouse = original.wizardHouse;
        
        this.isAlive = true;
        this.isBanished = false;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.waypoints = waypoints;
        this.fireballs = fireballs;
    }

    /**
     * Gets the image sprite representing the monster's appearance.
     *
     * @return The PImage object containing the monster's sprite.
     */
    public PImage getType() {
        return sprite;
    }

    /**
     * Gets the initial health points (hp) of the monster.
     *
     * @return The initial health points of the monster.
     */
    public int getHp() {
        return hp;
    }
    
    /**
     * Gets the current x-coordinate of the monster's position.
     *
     * @return The x-coordinate of the monster.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the current y-coordinate of the monster's position.
     *
     * @return The y-coordinate of the monster.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the width of the monster's sprite image.
     *
     * @return The width of the sprite image.
     */
    public int getWidth() {
        return this.sprite.width;
    }

    /**
     * Gets the height of the monster's sprite image.
     *
     * @return The height of the sprite image.
     */
    public int getHeight() {
        return this.sprite.height;
    }

    /**
     * Gets the current health points of the monster.
     *
     * @return The current health points of the monster.
     */
    public int getCurrentHp() {
        return currentHp;
    }

    /**
     * Sets the current health points of the monster.
     *
     * @param currentHp The new current health points to set.
     */
    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    /**
     * Gets the movement speed of the monster.
     *
     * @return The movement speed of the monster.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Gets the armor value of the monster.
     *
     * @return The armor value of the monster.
     */
    public double getArmour() {
        return armour;
    }

    /**
     * Gets the amount of mana gained when the monster is killed.
     *
     * @return The mana gained on killing the monster.
     */
    public int getManaGainedOnKill() {
        return manaGainedOnKill;
    }

    /**
     * Sets the direction in which the monster is moving.
     *
     * @param direction The new direction of movement in degrees.
     */
    public void setDirection(double direction) {
        this.direction = direction;
    }
    
    /**
     * Sets the sprite image representing the monster's appearance.
     *
     * @param sprite The new sprite image for the monster.
     */
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }

    /**
     * Handles the logic for the monster, including movement, collision checks, and health updates.
     */
    public void tick() {
        // handles logic
        if (this.isAlive) {
            move();
            checkCollision();
            checkHP();
        }
    }

    /**
     * Moves the monster based on its current direction and speed.
     */
    public void move() {
        x += speed/30 * Math.cos(Math.toRadians(direction));
        y += speed/30 * Math.sin(Math.toRadians(direction));
    }
    

    /**
     * Draws the monster's image and health bar on the game canvas.
     *
     * @param app  The PApplet object used for drawing.
     */
    public void draw(PApplet app) {
        // handle graphics
        if (this.isAlive) {

            float xOffset = this.x*App.CELLSIZE + 5;
            float yOffset = this.y*App.CELLSIZE + App.TOPBAR + 5;
            app.image(this.sprite, xOffset, yOffset);

            // draw health bar above head
            float healthBarWidth = this.sprite.width;
            float currentHealthWidth = ((float)currentHp/(float)hp) * healthBarWidth;

            app.noStroke();
            app.fill(255, 0, 0);
            app.rect(xOffset, yOffset - 10, healthBarWidth, 2);
            app.fill(0, 255, 0); 
            app.rect(xOffset, yOffset - 10, currentHealthWidth, 2);
            
            
        }
    }
    
    /**
     * Reduces the monster's current health points by the specified amount.
     *
     * @param damage The amount of damage to inflict on the monster.
     */
    public void takeDamage(int damage) {
        currentHp -= damage;
    }

    /**
     * Sets the monster's state as not alive, indicating it has been defeated.
     */
    public void setDead() {
        this.isAlive = false;
    }
    
    /**
     * Checks if the monster is alive.
     *
     * @return True if the monster is alive, false if it is defeated.
     */
    public boolean isAlive() {
        return this.isAlive;
    }

    /**
     * Checks if the monster's current health points have fallen to zero or below
     * and sets the monster as dead if it is defeated.
     */
    public void checkHP() {
        if (currentHp <= 0) {
            setDead();
        }
    }

    /**
     * Checks if the monster has been banished.
     *
     * @return True if the monster has been banished, false otherwise.
     */
    public boolean getIsBanished() {
        return isBanished;
    }

    /**
     * return the monster to the spawn location after being banished so it can run the course again.
     *
     * @param x          The x-coordinate of the spawn location.
     * @param y          The y-coordinate of the spawn location.
     * @param direction  The direction the monster should start moving in at that spawn location.
     */
    public void setBanishedMonsterLocation(int x, int y, double direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.isBanished = false;
    }

    /**
     * Checks if the monster is banished and sets its state accordingly.
     * Checks if the monster is inside a waypoint and changes direction accordingly.
     * Checks if the monster collides with a fireball and takes damage.
     */
    public void checkCollision() {
            
        // monster sprite location and dimensions
        float monsterLeft = this.x * App.CELLSIZE  + 5;
        float monsterTop = this.y * App.CELLSIZE  + 45;
        float monsterRight = monsterLeft + this.sprite.width;
        float monsterBottom = monsterTop + this.sprite.height;

        // wizard house
        int wizardHouseLeft = wizardHouse.getX() * App.CELLSIZE - 8;
        int wizardHouseRight = wizardHouseLeft + wizardHouse.getWidth();
        int wizardHouseTop = wizardHouse.getY()* App.CELLSIZE + App.TOPBAR - 8;
        int wizardHouseBottom = wizardHouseTop + wizardHouse.getHeight();

        if (monsterRight > wizardHouseLeft && 
            monsterLeft < wizardHouseRight &&
            monsterBottom > wizardHouseTop && 
            monsterTop < wizardHouseBottom) {
                this.isBanished = true;
                return;
        }
            
            // waypoints
        for (Waypoint waypoint : this.waypoints) {
            float waypointLeft = waypoint.getX() * App.CELLSIZE + 5;
            float waypointRight = waypointLeft + 22;
            float waypointTop = waypoint.getY() * App.CELLSIZE + 45;
            float waypointBottom = waypointTop + 22;

            if (monsterLeft >= waypointLeft && 
            monsterRight <= waypointRight && 
            monsterTop >= waypointTop && 
            monsterBottom <= waypointBottom) {
                this.setDirection(waypoint.getDirectionAsAngle());
            }
        }

        for (Fireball fireball : this.fireballs) {
            float fireballLeft = fireball.getX();
            float fireballRight = fireballLeft + fireball.getWidth();
            float fireballTop = fireball.getY();
            float fireballBottom = fireballTop + fireball.getHeight();
        
            if (monsterRight > fireballLeft && 
            monsterLeft < fireballRight &&
            monsterBottom > fireballTop && 
            monsterTop < fireballBottom) {
                if (fireball.isFiring()){
                    this.takeDamage(fireball.getDamage());
                }
                fireball.resetFireball();
            }
        }
    }
}
