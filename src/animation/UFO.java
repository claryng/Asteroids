/**
 * 
 */
package animation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
//import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 * This class creates the Ship
 *
 */
public class UFO implements AnimatedObject {
    
    // Width of the screen
    private static final int WIDTH = 600;
    
    // Coordinates of the center of the Ship
    private double x;
    private double y;
    
    // Shape of the Ship
    private Polygon ufo;
    
    //The x when ufo die
    private static final double UFO_PARKING_SPACE = -100;
    
    // The animation that this object is part of
    private AbstractAnimation animation;
    
    // The number of pixels to move on each frame of the animation
    private int moveAmount = 1;
    
//    private Shot[] bullets = new Shot[];
    
    /**
     * Create the Saucer object
     * 
     * @param animation the animation that this object is part of
     */
    public UFO(AbstractAnimation animation) {
        this.animation = animation;
        ufo = new Polygon();
        ufo.addPoint(-15, 25);
        ufo.addPoint(0, -25);
        ufo.addPoint(15, 25);
        ufo.addPoint(10, 25);
        ufo.addPoint(0, 30);
        ufo.addPoint(-10, 25);
        
        x = UFO_PARKING_SPACE;
        y = 100;
    }
    
    /**
     * Updates the object's state as you want it to appear on 
     * the next frame of the animation.
     */
    public void nextFrame() {
        
        if (x >= -10) {
            x = x + moveAmount;
        }
        
        if (x >= WIDTH) {
            x = UFO_PARKING_SPACE;
        }
        
        getShape();
        
        //bullets.moveRandom?
    }
    
    /**
     * Draws the object
     * @param g the graphics context to draw on
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.draw(getShape());
    }
    
    /**
     * Returns the shape after applying the current translation
     * and rotation
     * @return the shape located as we want it to appear
     */
    public Shape getShape() {
        AffineTransform at = new AffineTransform();
        
        at.translate(x, y);
        
        return at.createTransformedShape(ufo);
    }
    
    /**
     * @param amount
     */
    public void setX(double amount) {
        x = amount;
    }
    
    public void die() {
        x = UFO_PARKING_SPACE;
    }
    
    public void appear() {
        x = -10;
    }

}
