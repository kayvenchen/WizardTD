package WizardTD;

import java.util.ArrayList;

import processing.core.PApplet;


/**
 * The `Cell` class represents a cell in a tower defense game grid. Each cell has a position,
 * buildable status, and may contain a tower.
 *
 */
public class Cell {
    
    private int x;
    private int y;
    private boolean buildable;

    private Tower occupant = null;

    /**
     * Constructs a new Cell at the specified coordinates.
     *
     * @param x The X-coordinate of the cell.
     * @param y The Y-coordinate of the cell.
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.buildable = false;
    }

    /**
     * Get the X-coordinate of the cell.
     *
     * @return The X-coordinate of the cell.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Y-coordinate of the cell.
     *
     * @return The Y-coordinate of the cell.
     */
    public int getY() {
        return y;
    }

    /**
     * Check if a tower can be built on this cell.
     *
     * @return true if a tower can be built on this cell, false otherwise.
     */
    public boolean getBuildable() {
        return buildable;
    }

    /**
     * Get the tower currently occupying this cell.
     *
     * @return The tower occupying this cell, or null if there is none.
     */
    public Tower getOccupant() {
        return occupant;
    }

    /**
     * Set the buildable status of this cell.
     *
     * @param buildable true if a tower can be built on this cell, false otherwise.
     */
    public void setBuildable(boolean buildable) {
        this.buildable = buildable;
    }

    /**
     * Draw the cell as a rectangle on the game screen.
     *
     * @param app The PApplet object used for rendering.
     */
    public void draw(PApplet app) {
        app.noFill();
        app.stroke(0);
        app.rect(this.x * App.CELLSIZE, y * App.CELLSIZE + App.TOPBAR, App.CELLSIZE, App.CELLSIZE);
    }

    /**
     * Build a tower on this cell and add it to the list of towers.
     *
     * @param t The tower to be built on this cell.
     * @param towers The list of towers in the game.
     */
    public void buildTower(Tower t, ArrayList<Tower> towers) {
        if (buildable) {
            occupant = t;
            towers.add(occupant);
        }
        setBuildable(false);
    }
}