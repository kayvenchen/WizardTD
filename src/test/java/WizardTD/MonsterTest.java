package WizardTD;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MonsterTest {

    @Test
    public void testMonsterConstruction() {
        Monster monster = new Monster(null, 1, 1,1,1, null);

        assertEquals(1, monster.getHp());
    }

    @Test
    public void testMonsterTakeDamage() {
        Monster monster = new Monster(null, 1, 1,1,1, null);

        int damage = 20;
        monster.takeDamage(damage);

        assertEquals(-19, monster.getCurrentHp());
    }

    @Test
    public void testMonsterSetDead() {
        Monster monster = new Monster(null, 1, 1,1,1, null);

        monster.setDead();

        assertFalse(monster.isAlive());
    }

    @Test
    public void testMonsterBanished() {
        Monster monster = new Monster(null, 1, 1, 1, 1, null);

        monster.setBanishedMonsterLocation(1,1,1);

        assertFalse(monster.getIsBanished());
    }
}

