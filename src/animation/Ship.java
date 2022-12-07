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
public class Ship implements AnimatedObject {
    
    // Width of the screen
    private static final int WIDTH = 600;
    
    // Initial position of the ship when the game starts
    private static final int X = 300;
    private static final int Y = 300;
    
    // Coordinates of the center of the Ship
    private double x;
    private double y;
    
    // Shape of the Ship
    private Polygon ship;
    
    // The animation that this object is part of
    private AbstractAnimation animation;
    
    // The number of pixels to move on each frame of the animation
    private int moveAmount = 1;
    
    // Used to check if the ship needs to rotate
    private boolean rotate = false;
    
    // Used to check orientation of rotation
    private String orientation = "";
    
    // Used to check if direction changes
    private boolean direction_changed = false;
    
    // Original Angle of direction
    private double angle = 0;
    
    // Direction of movement
    private Double vector_target = new Double(X,Y - 20);
    
    /**
     * Create the Ship object
     * 
     * @param animation the animation that this object is part of
     */
    public Ship(AbstractAnimation animation) {
        this.animation = animation;
        ship = new Polygon();
        ship.addPoint(-10, 20);
        ship.addPoint(0, -20);
        ship.addPoint(10, 20);
        
        x = X;
        y = Y;
    }
    
    /**
     * Updates the object's state as you want it to appear on 
     * the next frame of the animation.
     */
    public void nextFrame() {
//        vector_target.setLocation(vector_target.getX() - x_direction, vector_target.getY() - y_direction);
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
        
        // AffineTransform captures the movement and rotation we
        // want the ship to have
        AffineTransform affineTransform = new AffineTransform();
        
        // x, y are where the origin of the shape will be.  In this
        // case, this is the center of the triangle.  See the constructor
        // to see where the points are.
        affineTransform.translate(x, y);
        
        if(rotate) {
            if(orientation.equals("left")) {
                angle -= 0.2;
            }else {
                angle += 0.2;
            }
            
            setVectorTarget();
            rotate = false;
        }
        
        // Rotate the ship
        affineTransform.rotate(angle);
        
        AffineTransform at = affineTransform;
        
        // Create a shape that looks like our triangle, but centered
        // and rotated as specified by the AffineTransform object.
        return at.createTransformedShape(ship);
    }
    
    /**
     * Move the ship in its current direction
     */
    public void move() {
        if (direction_changed) {
            x = Math.abs(x + 1 * (vector_target.getX() - x)) % WIDTH;
            y = Math.abs(y + 1 * (vector_target.getY() - y)) % WIDTH;
            setVectorTarget();
        }else {
            y -= moveAmount;
        }
    }
    
    /**
     * Indicate that rotation happens and direction changes
     */
    public void rotate() {
        rotate = true;
        direction_changed = true;
    }
    
    /**
     * Set the orientation of the rotation
     * @param orientation the orientation of the rotation
     */
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
    
    /**
     * Reset vector_target when direction of movement changes
     */
    private void setVectorTarget() {
        // -> positive x-axis
        // positive y-axis is reversed
        double x_direction_point = x + 20 * Math.sin(angle);
        double y_direction_point = y - 20 * Math.cos(angle);
        vector_target.setLocation(x_direction_point, y_direction_point);
    }
    
    /**
     * Send the ship into hyperspace
     */
    public void hyperspace() {
        double min = 20;
        double max = WIDTH - 20;
        double range = max - min + 1;
        x = (Math.random() * range) + min;
        y = (Math.random() * range) + min;
        setVectorTarget();
    }
}
