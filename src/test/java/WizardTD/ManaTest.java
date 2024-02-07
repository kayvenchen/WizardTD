package WizardTD;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ManaTest {

    @Test
    public void testUseManaPoolSpell() {
        Mana mana = new Mana(100, 200, 5);
        mana.useManaPoolSpell(30, 2, 2);
        assertEquals(70, (int) mana.getCurrentMana());
        assertEquals(400, (int) mana.getManaCap());
        assertEquals(10, (int) mana.getManaGainPerSecond());
    }

    @Test
    public void testIncreaseManaOverTime() {
        Mana mana = new Mana(100, 200, 5);
        mana.increaseManaOverTime();
        assertEquals(105, (int) mana.getCurrentMana());
    }

    @Test
    public void testDecreaseMana() {
        Mana mana = new Mana(100, 200, 5);
        mana.decreaseMana(25);
        assertEquals(75, (int) mana.getCurrentMana());
    }

    @Test
    public void testGetCurrentMana() {
        Mana mana = new Mana(100, 200, 5);
        assertEquals(100, (int) mana.getCurrentMana());
    }

    @Test
    public void testGetManaCap() {
        Mana mana = new Mana(100, 200, 5);
        assertEquals(200, (int) mana.getManaCap());
    }

    @Test
    public void testGetManaGainPerSecond() {
        Mana mana = new Mana(100, 200, 5);
        assertEquals(5, (int) mana.getManaGainPerSecond());
    }
}
