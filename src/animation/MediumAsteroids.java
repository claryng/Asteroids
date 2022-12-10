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

public class MediumAsteroids extends Asteroids {
    
    private Polygon asteroid; 
//    private double locationX;
//    private double locationY;
//    private double targetedX;
//    private double targetedY;
    
    /**
     * Constructor to create a large asteroid
     */
    public MediumAsteroids(AbstractAnimation animation, double angle, int no) {
        
        super(animation);
        
        asteroid = new Polygon();
        asteroid.addPoint(0, 20);
        asteroid.addPoint(-10, 0);
        asteroid.addPoint(-10, 10);
        asteroid.addPoint(0, -10);
        asteroid.addPoint(10, 10);
        asteroid.addPoint(10, 0);
       
        setAngle(angle, no);
        
    }
    
    public void setAngle(double parentAngle, int no) {
        super.setAngle(parentAngle, no);
    }
    
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

//        setVectorTarget(speed);

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
        
        // Find coordinates using calculus: position vector
        setLocationX (this.getLocationX() + 1 * ((this.getTargetedX() - this.getLocationX())));
        setLocationY (this.getLocationY() + 1 * ((this.getTargetedY() - this.getLocationY())));

         //Wrap the ship around the screen
        setLocationX ((this.getLocationX() <= 0) ? WIDTH + this.getLocationX() : this.getLocationX() % WIDTH);
        setLocationY ((this.getLocationY() <= 0) ? WIDTH + this.getLocationY() : this.getLocationY() % WIDTH);

        // Change the vector target according to the new coordinates
        setTarget();

        // Set moving flag to true to continue moving in the next frames
//        moving = true;
    }
    
    public void setTarget() {
        setTargetedX(this.getLocationX() + SPEED * Math.sin(getAngle()));
        setTargetedY(this.getLocationY() - SPEED * Math.cos(getAngle()));
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
}
