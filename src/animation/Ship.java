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
    
    // Used to check if direction changes
    private boolean direction_changed = false;
    
    // Original Angle of direction
    private double angle = 0;
    
    // Direction of movement
    private Double vector_target = new Double(X,Y - 3);
    
    // Used to check if the ship is moving
    private boolean moving = false;
    
    // Used to keep track of current speed
    private double speed = 3;
    
    // Used to keep track of number of frames
    private int frames = 1;
    
    // Used to keep track of number of thrusts
    private int thrusts = 0;
    
    // Testing
    private int i = 1;
    
    /**
     * Create the Ship object
     * 
     * @param animation the animation that this object is part of
     */
    public Ship(AbstractAnimation animation) {
        this.animation = animation;
        ship = new Polygon();
        
        // Size of the ship: 10 x 17
        ship.addPoint(-5, 9);
        ship.addPoint(5, 9);
        ship.addPoint(0, -8);
        
        x = X;
        y = Y;
    }
    
    /**
     * Updates the object's state as you want it to appear on 
     * the next frame of the animation.
     */
    public void nextFrame() {
        if(moving && speed < 1) {
            System.out.println("Speed - stop: " + speed);
            moving = false;
            thrusts = 0;
        }
        if(moving && frames % 3 == 0 && frames > 0) {
            
            System.out.println("Frames - decreasing:" + frames);
            speed = (speed * 90)/100;
            System.out.println("Speed - decreasing:" + speed);
        }
        if(moving) {
            frames++;
            System.out.println("Frames - increase: " + frames);
            move();
        }
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

        setVectorTarget(speed);
        
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
        x = x + 1 * ((vector_target.getX() - x));
        y = y + 1 * ((vector_target.getY() - y));
        x = (x <= 0) ? WIDTH + x : x % WIDTH;
        y = (y <= 0) ? WIDTH + y : y % WIDTH;
        System.out.println("X: " + x);
        System.out.println("Y: " + y);
        setVectorTarget(speed);
        moving = true;
    }
    
    /**
     * Rotate counter-clockwise
     */
    public void rotateLeft() {
        angle -= 0.2;
    }
    
    /**
     * Rotate clockwise
     */
    public void rotateRight() {
        angle += 0.2;
    }
    
    /**
     * Reset vector_target when direction of movement changes
     */
    private void setVectorTarget(double speed) {
        // -> positive x-axis
        // positive y-axis is reversed
        double x_direction_point = x + speed * Math.sin(angle);
        double y_direction_point = y - speed * Math.cos(angle);
        vector_target.setLocation(x_direction_point, y_direction_point);
    }
    
    /**
     * Send the ship into hyperspace
     */
    public void space() {
        double min = 20;
        double max = WIDTH - 20;
        double range = max - min + 1;
        x = (Math.random() * range) + min;
        y = (Math.random() * range) + min;
        setVectorTarget(speed);
    }
    
    /**
     * Set the number of thrusts
     */
    public void setThrusts() {
        thrusts ++;
        frames = 0;
        speed = 3 * thrusts;
        System.out.println("Speed: " + speed);
        System.out.println("Thrusts: " + thrusts);
    }
}
