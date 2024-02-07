package WizardTD;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class FireballTest {

    @Test
    public void testFireballCreation() {
        Fireball fireball = new Fireball(null);

        // Check if the Fireball is created correctly
        assertNotNull(fireball);
        assertFalse(fireball.isFiring());
    }

    @Test
    public void testSetStartLocation() {

        Fireball fireball = new Fireball(null);
        float x = 10.0f;
        float y = 20.0f;
        double direction = Math.PI / 4; // 45 degrees
        int damage = 10;
        int range = 100;

        fireball.setStartLocation(x, y, direction, damage, range);

        // Check if the parameters are correctly set
        assertTrue(fireball.isFiring());
        assertEquals(x, fireball.getX());
        assertEquals(y, fireball.getY());
        assertEquals(direction, fireball.getDirection(), 0.001); 
        assertEquals(damage, fireball.getDamage());
        assertEquals(range, fireball.getRange());
    }

    @Test
    public void testTick() {

        Fireball fireball = new Fireball(null);
        float x = 10.0f;
        float y = 20.0f;
        double direction = Math.PI / 4; // 45 degrees
        int damage = 10;
        int range = 100;

        fireball.setStartLocation(x, y, direction, damage, range);

        // Simulate the passage of time, update the fireball position
        fireball.tick();

        // Check if the fireball's position has been updated
        assertNotEquals(x, fireball.getX());
        assertNotEquals(y, fireball.getY());
    }

    @Test
    public void testResetFireball() {
        Fireball fireball = new Fireball(null);
        float x = 10.0f;
        float y = 20.0f;
        double direction = Math.PI / 4; // 45 degrees
        int damage = 10;
        int range = 100;

        fireball.setStartLocation(x, y, direction, damage, range);
        fireball.resetFireball();

        // Check if the fireball is no longer firing
        assertFalse(fireball.isFiring());
        assertEquals(0, fireball.getX(), 0.001);
        assertEquals(0, fireball.getY(), 0.001);
    }

}