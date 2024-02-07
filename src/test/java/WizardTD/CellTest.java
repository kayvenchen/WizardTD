package WizardTD;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class CellTest {

    @Test
    public void testConstruction() {
        Cell cell = new Cell(1, 2);
        assertEquals(1, cell.getX());
        assertEquals(2, cell.getY());
        assertFalse(cell.getBuildable());
        assertNull(cell.getOccupant());
    }

    @Test
    public void testSetBuildable() {
        Cell cell = new Cell(3, 4);
        cell.setBuildable(true);
        assertTrue(cell.getBuildable());
    }

    @Test
    public void testBuildTower() {
        Cell cell = new Cell(5, 6);
        Tower towerOriginal = new Tower(null,100,100,100,100);
        Tower tower = new Tower(towerOriginal, 100, 100);
        ArrayList<Tower> towers = new ArrayList<>();
        
        // Ensure that the cell is buildable
        cell.setBuildable(true);

        // Build the tower on the cell
        cell.buildTower(tower, towers);

        // Check if the cell's occupant is the tower
        assertEquals(tower, cell.getOccupant());

        // Check if the tower is added to the list of towers
        assertTrue(towers.contains(tower));
    }
    
    @Test
    public void testBuildTowerNotBuildable() {
        Cell cell = new Cell(7, 8);
        Tower towerOriginal = new Tower(null,100,100,100,100);
        Tower tower = new Tower(towerOriginal, 100, 100);
        ArrayList<Tower> towers = new ArrayList<>();

        // Ensure that the cell is not buildable
        cell.setBuildable(false);

        // Attempt to build the tower on the cell
        cell.buildTower(tower, towers);

        // Check that the cell's occupant is still null
        assertNull(cell.getOccupant());

        // Check that the tower is not added to the list of towers
        assertFalse(towers.contains(tower));
    }
}
