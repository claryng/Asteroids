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

public class LargeAsteroids extends Asteroids {
    
    private Polygon asteroid; 
    private int size;
    private double angle;
//    private double locationX;
//    private double locationY;
//    private double targetedX;
//    private double targetedY;
    
    /**
     * Constructor to create a large asteroid
     */
    public LargeAsteroids(AbstractAnimation animation) {
        
        super(animation);
        
        asteroid = new Polygon();
        asteroid.addPoint(0, 30);
        asteroid.addPoint(20, 15);
        asteroid.addPoint(20, -15);
        asteroid.addPoint(0, -30);
        asteroid.addPoint(-20, -15);
        asteroid.addPoint(-20, 15);
        
        setRandom();
        this.angle = setRandomAngle();
        
    }
    
//    public double getAngle() {
//        return angle;
//    }
    
    /**
     * Returns the shape after applying the current translation and rotation
     * 
     * @return the shape located as we want it to appear
     */
    public Shape getShape() {

        // AffineTransform captures the movement and rotation we
        // want the asteroid to have
        AffineTransform affineTransform = new AffineTransform();

        // x, y are where the origin of the shape will be. In this
        // case, this is the center of the polygon. See the constructor
        // to see where the points are.
        affineTransform.translate(getLocationX(), getLocationY());

        // Rotate the ship
//        affineTransform.rotate(angle);

        AffineTransform at = affineTransform;

        // Create a shape that looks like our polygon, but centered
        // and rotated as specified by the AffineTransform object.
        return at.createTransformedShape(asteroid);
    }
    
    /**
     * Draws the object
     * 
     * @param g the graphics context to draw on
     */
    public void paint(Graphics2D g) {
        super.paint(g);
//        g.setColor(Color.WHITE);
        g.draw(getShape());
    }
    
    /**
     * Move the ship in its current direction
     */
    public void move() {
        super.move();
    }
    
    public void setTarget() {
        super.setTarget();
    }
    
    /**
     * Moves the ball a small amount. If it reaches the left or right edge, it
     * bounces.
     */
    public void nextFrame() {
        
//        // If hit by a bullet, the asteroid is destroyed
//        if (isHit) {
//            isDestroyed = true;
//        }
//        
//        // The asteroid keeps moving if it is not destroyed
//        if (!isDestroyed) {
//            frames++;
        move();     
    }
    /**
     * Get Angle
     */
    public double getAngle() {
        return angle;
    }
}
