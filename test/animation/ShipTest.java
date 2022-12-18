package animation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Point2D.Double;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import animation.UI.GameGUI;

class ShipTest {
    // Width of the screen
    private static final int WIDTH = 600;

    // Create game animation
    private GameGUI game = new GameGUI();

    // Create the Ship
    private Ship ship = new Ship(game);

    @BeforeEach
    void setUp() throws Exception {
        ship.reset();
    }

    @Test
    void testNextFrame() {

        // Test what happens when the ship is NOT moving: The ship stays at the
        // center of the screen
        assertEquals(new Double(WIDTH / 2, WIDTH / 2), ship.getXY());
        assertEquals(false, ship.getMoving());

        // Test what happens when the ship is moving: pressing UP once
        ship.thrust();
        ship.move();
        assertEquals(true, ship.getMoving());
        assertEquals(3, ship.getSpeed());

        // Speed decreases by 10% after 3 frames
        ship.nextFrame();
        ship.nextFrame();
        ship.nextFrame();
        assertEquals(true, ship.getMoving());
        assertEquals((double) 3 * 90 / 100, ship.getSpeed());

        // Speed stays the same for the next 3 frames before decreasing again
        ship.nextFrame();
        ship.nextFrame();
        assertEquals(true, ship.getMoving());
        assertEquals((double) 3 * 90 / 100, ship.getSpeed());

        // Speed decreases by 10% again
        ship.nextFrame();
        assertEquals(true, ship.getMoving());
        assertEquals((double) 2.7 * 90 / 100, ship.getSpeed());

        // Reset speed and moving status
        ship.reset();

        // Test what happens when the ship is moving: pressing UP twice
        ship.thrust();
        ship.move();
        ship.thrust();
        ship.move();
        assertEquals(true, ship.getMoving());
        assertEquals(6, ship.getSpeed());

        // Speed decreases by 10% after 3 frames
        ship.nextFrame();
        ship.nextFrame();
        ship.nextFrame();
        assertEquals(true, ship.getMoving());
        assertEquals((double) 6 * 90 / 100, ship.getSpeed());

        // Speed stays the same for the next 3 frames before decreasing again
        ship.nextFrame();
        ship.nextFrame();
        assertEquals(true, ship.getMoving());
        assertEquals((double) 6 * 90 / 100, ship.getSpeed());

        // Speed decreases by 10% again
        ship.nextFrame();
        assertEquals(true, ship.getMoving());
        assertEquals((double) 5.4 * 90 / 100, ship.getSpeed());

        // Reset speed and moving status
        ship.reset();

        // Test what happens when pressing UP twice but 1 frame apart
        ship.thrust();
        ship.move();
        ship.nextFrame();
        ship.thrust();
        ship.move();
        assertEquals(true, ship.getMoving());
        assertEquals((double) 6, ship.getSpeed());

        // Reset speed and moving status
        ship.reset();

        // Test what happens when pressing UP twice but 3 frames apart
        ship.thrust();
        ship.move();
        ship.nextFrame();
        ship.nextFrame();
        ship.nextFrame();
        ship.thrust();
        ship.move();
        assertEquals(true, ship.getMoving());
        assertEquals((double) 2.7 + 3, ship.getSpeed());

        // Reset speed and moving status
        ship.reset();

        // Test ship stops moving
        ship.thrust();
        ship.move();
        while (ship.getSpeed() >= 0.5) {
            ship.nextFrame();
        }
        ship.nextFrame();
        assertTrue(!ship.getMoving());

        // Reset speed and moving status
        ship.reset();

        // Test max speed == 20
        ship.thrust();
        ship.move();
        ship.thrust();
        ship.move();
        ship.thrust();
        ship.move();
        ship.thrust();
        ship.move();
        ship.thrust();
        ship.move();
        ship.thrust();
        ship.move();
        ship.thrust();
        ship.move();
        assertEquals(20, ship.getSpeed());
        ship.thrust();
        ship.move();
        assertEquals(20, ship.getSpeed());

        // Max speed stays the same after 2 frames
        ship.nextFrame();
        ship.nextFrame();
        assertEquals(20, ship.getSpeed());

        // Max speed decreases by 10% after 3 frames
        ship.nextFrame();
        assertEquals((double) 20 * 90 / 100, ship.getSpeed());
    }

    @Test
    void testRotation() {
        // When not moving
        ship.rotateLeft();
        assertEquals(-0.2, ship.getRotatingAngle());
        assertEquals(-0.2, ship.getDirectionAngle());
        ship.rotateRight();
        assertEquals(0, ship.getRotatingAngle());
        assertEquals(0, ship.getDirectionAngle());

        // When moving
        ship.move();
        ship.rotateLeft();
        assertEquals(-0.2, ship.getRotatingAngle());
        assertEquals(0, ship.getDirectionAngle());
    }

    @Test
    void testFire() {

        // Before firing
        assertTrue(ship.getShots().isEmpty());

        // Fire
        ship.addShots();
        ship.fire();
        assertTrue(!ship.getShots().isEmpty());
        assertEquals(1, ship.getShots().size());
        ship.addShots();
        ship.fire();
        assertTrue(!ship.getShots().isEmpty());
        assertEquals(2, ship.getShots().size());
    }

    @Test
    void testDie() {

        // Before being hit
        ship.move();
        assertTrue(!ship.getXY().equals(new Double(WIDTH / 2, WIDTH / 2)));

        // Get hit
        ship.die();
        assertTrue(ship.getXY().equals(new Double(WIDTH / 2, WIDTH / 2)));
    }

    @Test
    void testSpace() {

        // Before hyperspace
        Double coordinates = ship.getXY();

        // Hyperspace
        ship.space();
        assertTrue(!ship.getXY().equals(coordinates));

        // Before hyperspace
        ship.thrust();
        ship.move();
        double speed = ship.getSpeed();

        // Hyperspace
        ship.space();
        assertTrue(ship.getSpeed() == speed);
    }
}
