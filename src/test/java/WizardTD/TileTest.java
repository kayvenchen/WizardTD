package WizardTD;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;
import processing.core.PImage;
import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    @Test
    public void testConstructor() {
        Tile tile = new Tile(5, 10, null);

        assertEquals(5, tile.getX());
        assertEquals(10, tile.getY());
        assertEquals(null, tile.getTile());
    }

    @Test
    public void testGetters() {
        PApplet applet = new PApplet();
        PImage sprite = applet.createImage(32, 32, PApplet.ARGB);
        Tile tile = new Tile(3, 7, sprite);

        assertEquals(3, tile.getX());
        assertEquals(7, tile.getY());
        assertEquals(sprite, tile.getTile());
        assertEquals(32, tile.getWidth());
        assertEquals(32, tile.getHeight());
    }
}
