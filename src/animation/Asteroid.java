package animation;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class Asteroid {
    
    // Width of the screen
    private static final int WIDTH = 600;
    
    // Number of children a large asteroid has
    private static final int NUM_OF_CHILDREN = 3;
    
    // Min and max
    private static final double MIN  = -30;
    private static final double MAX  = 630;
    
    // Coordinates of the asteroid
    private double locationX;
    private double locationY;
    
    // Shape of the asteroid
    private Polygon asteroid;
    
    // Size of the asteroid
    private int size;
    
    // Original direction 
    private double angle;
    
    // Initial speed of asteroid
    private double speed = 2.0;
    
    // Check if the asteroid is destroyed
    private boolean isHit = false;
    
    // Check if the asteroid is destroyed
    private boolean isDestroyed = false;
    
    // Frame counts
    private int frames = 1;
    
    // Random variable used to generate asteroids randomly
    private Random rand = new Random();
    
    // Animation that contains the object
    private AbstractAnimation animation;
    
    /**
     * Create medium and small asteroid
     */
    public Asteroid(AbstractAnimation animation, double speed, double parentX, double parentY, int size) {
        this.animation = animation;
        size = size;
        
        asteroid = asteroidShape(size);
        
        locationX = parentX;
        locationY = parentY;

        // 
    }
    
    /**
     * Create the original large asteroid object
     */
    public Asteroid(AbstractAnimation animation) {
        this.animation = animation;
        
        asteroid = asteroidShape(size);
        
        // Random starting coordinates
        // x-coordinate: min -30, max 660
        locationX =  Math.random() * (MAX - MIN) + MIN;
        
        locationY = (int) Math.random();
        if (locationY == 0) {
            locationY = Math.random() * 20 * (-1);
        }
        locationY = Math.random() * (MAX - MIN) + MIN;
        
        // Random starting angle
        angle = Math.random() * 360;
    }
    
    /**
     * Create the shape for the asteroid
     */
    private Polygon asteroidShape (int size) {
        Polygon asteroid = new Polygon();
        
        switch (size) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                asteroid.addPoint(-20, 15);
                asteroid.addPoint(0, 30);
                asteroid.addPoint(20, 15);
                asteroid.addPoint(-20, -10);
                asteroid.addPoint(0, -20);
                asteroid.addPoint(0, 40);
        }
            
        
        return asteroid;
    }
    
    /**
     * Moves the ball a small amount. If it reaches the left or right edge, it
     * bounces.
     */
    public void nextFrame() {
        
        // If hit by a bullet, the asteroid is destroyed
        if (isHit) {
            isDestroyed = true;
        }
        
        // The asteroid keeps moving if it is not destroyed
        if (!isDestroyed) {
            frames++;
            move();
        }       
    }
    
    /**
     * Draws an asteroid.
     * 
     * @param g the graphics context to draw on.
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
//        g.draw(getShape());
    }
    
    /**
     * Returns the shape after applying the current translation
     * and rotation
     * @return the shape located as we want it to appear
     */
    public Shape getShape() {
        // AffineTransform captures the movement and rotation we
        // want the shape to have
        AffineTransform at2 = new AffineTransform();
        
        // x, y are where the origin of the shape will be.  In this
        // case, this is the center of the triangle.  See the constructor
        // to see where the points are.
        at2.translate(locationX, locationY);
        
        // Rotate the shape 45 degrees to the left
//        at2.rotate(Math.PI/4);
        
       
        AffineTransform at = at2;
        
        // Create a shape that looks like our triangle, but centered
        // and rotated as specified by the AffineTransform object.
        return at.createTransformedShape(asteroid);
    }
    
    public void move() {
        
    }
}
