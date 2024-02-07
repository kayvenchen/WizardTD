package WizardTD;

import processing.core.PImage;


/**
 * The `Tile` class represents a tile on a game map, it is used for creating
 * the visual layout of the game. It stores the x and y coordinates of the tile
 * and the associated sprite image.
 */
public class Tile {

    private int x;
    private int y;
    private PImage sprite;

    /**
     * Constructs a new Tile object with the specified coordinates and sprite image.
     *
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @param sprite The sprite image associated with the tile.
     */
    public Tile(int x, int y, PImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    /**
     * Get the sprite image associated with the tile.
     *
     * @return The sprite image of the tile.
     */
    public PImage getTile() {
        return this.sprite;
    }

    /**
     * Get the x-coordinate of the tile.
     *
     * @return The x-coordinate of the tile.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the y-coordinate of the tile.
     *
     * @return The y-coordinate of the tile.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Get the height of the tile's sprite image.
     *
     * @return The height of the sprite image.
     */
    public int getHeight() {
        return this.sprite.height;
    }

    /**
     * Get the width of the tile's sprite image.
     *
     * @return The width of the sprite image.
     */
    public int getWidth() {
        return this.sprite.width;
    }
}