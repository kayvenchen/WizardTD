package WizardTD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WaveTest {
    private Wave wave;
    private ArrayList<Monster> monsters;

    @BeforeEach
    public void setUp() {
        monsters = new ArrayList<>();
        Monster monster1 = new Monster(null, 1, 1,1,1, null);
        Monster monster2 = new Monster(monster1, 1, 1, 1, null, null);
        monsters.add(monster2);

        wave = new Wave(2.0, 60, monsters, 10);
    }

    @Test
    public void testGetPreWavePause() {
        assertEquals(2.0, wave.getPreWavePause(), 0.01);
    }

    @Test
    public void testGetDuration() {
        assertEquals(60, wave.getDuration());
    }

    @Test
    public void testGetMonstersToSpawn() {
        ArrayList<Monster> monstersToSpawn = wave.getMonstersToSpawn();
        assertNotNull(monstersToSpawn);
        assertEquals(1, monstersToSpawn.size());
    }

    @Test
    public void testGetQuantity() {
        assertEquals(10, wave.getQuantity());
    }
}
