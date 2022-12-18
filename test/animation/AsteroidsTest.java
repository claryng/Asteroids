package animation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.Test;

import animation.UI.GameGUI;

class AsteroidsTest {

    // Create game animation
    private GameGUI game = new GameGUI();

    // Create an initial asteroid
    private Asteroids asteroid;

    // Create initial location points
    private double locationX;
    private double locationY;

    // Create targeted point
    private double targetedX;
    private double targetedY;

    // Create angle
    private double angle;

    // Create an array of broken up asteroids
    private CopyOnWriteArrayList<Asteroids> asteroids;

    @Test
    void testNextFrame() {

        // Large Asteroid Testing
        asteroid = new LargeAsteroids(game);

        // Test move() method
        locationX = asteroid.getLocationX();
        locationY = asteroid.getLocationY();
        targetedX = asteroid.getTargetedX();
        targetedY = asteroid.getTargetedY();

        // The asteroid moves in each next frame
        asteroid.nextFrame();

        // If asteroid is moving, its new x, y coordinates
        // are different from its original x, y coordinates
        assertTrue(locationX != asteroid.getLocationX());
        assertTrue(locationY != asteroid.getLocationY());
        assertTrue(targetedX != asteroid.getTargetedX());
        assertTrue(targetedY != asteroid.getTargetedY());
    }

    @Test
    void testIsHit() {
        // Test when asteroid is hit: asteroid should stop moving
        asteroid = new LargeAsteroids(game);

        locationX = asteroid.getLocationX();
        locationY = asteroid.getLocationY();

        asteroid.setIsHit();
        asteroid.move();

        // After asteroid is hit, its x, y coordinates should not change
        assertEquals(locationX, asteroid.getLocationX());
        assertEquals(locationY, asteroid.getLocationY());
    }

    @Test
    void testSplit() {
        // Test Large Asteroid: If a large asteroid is hit, it splits into two
        // medium asteroids
        asteroid = new LargeAsteroids(game);

        asteroid.move();

        asteroid.split(asteroid.getAngle(), asteroid.getLocationX(),
                asteroid.getLocationY());
        asteroids = asteroid.getAsteroids();

        // Two asteroids in asteroids array should be two medium asteroids
        assertEquals(asteroids.get(0).getClass(), MediumAsteroids.class);
        assertEquals(asteroids.get(1).getClass(), MediumAsteroids.class);
        // asteroids array size is 2
        assertEquals(asteroids.size(), 2);

        locationX = asteroid.getLocationX();
        locationY = asteroid.getLocationY();
        angle = asteroid.getAngle();

        // Test angles of medium asteroids
        assertEquals(angle - Math.PI / 4, asteroids.get(0).getAngle());
        assertEquals(angle + Math.PI / 4, asteroids.get(1).getAngle());

        // Test Medium Asteroid: If a medium asteroid is hit, it splits into two
        // small asteroids
        asteroid = new MediumAsteroids(game, angle, locationX, locationY);
        asteroid.move();

        asteroid.split(asteroid.getAngle(), asteroid.getLocationX(),
                asteroid.getLocationY());
        asteroids = asteroid.getAsteroids();

        // Two asteroids in asteroids array should be two small asteroids
        assertEquals(asteroids.get(0).getClass(), SmallAsteroids.class);
        assertEquals(asteroids.get(1).getClass(), SmallAsteroids.class);
        // asteroids array size is 2
        assertEquals(asteroids.size(), 2);

        angle = asteroid.getAngle();

        // Test angles of small asteroids
        assertEquals(angle - Math.PI / 4, asteroids.get(0).getAngle());
        assertEquals(angle + Math.PI / 4, asteroids.get(1).getAngle());
    }
}
