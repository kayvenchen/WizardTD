package WizardTD;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpawnpointTest {
    @Test
    public void testConstructor() {
        SpawnPoint spawnPoint = new SpawnPoint(5, 10, 1);
        
        assertEquals(5, spawnPoint.getX());
        assertEquals(10, spawnPoint.getY());
        assertEquals(1, spawnPoint.getDirection());
    }

    @Test
    public void testGetters() {
        SpawnPoint spawnPoint = new SpawnPoint(3, 7, 2);
        
        assertEquals(3, spawnPoint.getX());
        assertEquals(7, spawnPoint.getY());
        assertEquals(2, spawnPoint.getDirection());
    }
}
