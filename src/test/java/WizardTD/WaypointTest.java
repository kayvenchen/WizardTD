package WizardTD;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class WaypointTest {

    @Test
    public void testConstructor() {
        Waypoint waypoint = new Waypoint('U', 1, 2);
        assertEquals('U', waypoint.getDirection());
        assertEquals(1, waypoint.getX());
        assertEquals(2, waypoint.getY());
    }

    @Test
    public void testGetDirectionAsAngle() {
        Waypoint upWaypoint = new Waypoint('U', 0, 0);
        Waypoint downWaypoint = new Waypoint('D', 0, 0);
        Waypoint leftWaypoint = new Waypoint('L', 0, 0);
        Waypoint rightWaypoint = new Waypoint('R', 0, 0);


        assertEquals(270, upWaypoint.getDirectionAsAngle());
        assertEquals(90, downWaypoint.getDirectionAsAngle());
        assertEquals(180, leftWaypoint.getDirectionAsAngle());
        assertEquals(0, rightWaypoint.getDirectionAsAngle());

    }

    @Test
    public void testGetX() {
        Waypoint waypoint = new Waypoint('R', 5, 6);
        assertEquals(5, waypoint.getX());
    }

    @Test
    public void testGetY() {
        Waypoint waypoint = new Waypoint('U', 2, 3);
        assertEquals(3, waypoint.getY());
    }
    
    @Test
    public void testGetDirection() {
        Waypoint waypoint = new Waypoint('R', 5, 6);
        assertEquals('R', waypoint.getX());
    }
}
