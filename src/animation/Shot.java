/**
 * 
 */
package animation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D.Double;

/**
 * This class creates the Ship
 *
 */
public class Shot implements AnimatedObject {
    
    // Width of the screen
    private static final int WIDTH = 600;
    
    //diameter of the shot
    private static final int SHOT_SIZE = 5;
    
    // Coordinates of the center of the shot
    private double x;
    private double y;
    
    // Shape of the shot
    private Ellipse2D shot;
    
    // The animation that this object is part of
    private AbstractAnimation animation;
    
    // Original Angle of direction
    private double angle = 0;
    
    // Direction of movement
    private Double vector_target;
    
    // Used to check if the bullet is moving
    private boolean moving = false;
    
    // Used to keep track of current speed
    private double speed = 5;
    
    /**
     * Create the Ship object
     * 
     * @param animation the animation that this object is part of
     */
    public Shot(AbstractAnimation animation, double speed, double x, double y, double angle) {
        this.animation = animation;
        
        this.x = x;
        this.y = y;
        this.angle = angle;
        shot = new Ellipse2D.Double(x, y, SHOT_SIZE, SHOT_SIZE);
        this.speed = this.speed + speed;
        
        vector_target = new Double(x, y - speed);
    }
    
    /**
     * Updates the object's state as you want it to appear on 
     * the next frame of the animation.
     */
    public void nextFrame() {
        
        if (x < 600 && y < 600 && x > 0 && y > 0) {
            x = x + (speed)*Math.sin(angle);
            y = y - (speed)*Math.cos(angle);
            System.out.println("Shot speed: " + speed +  " Coordinate: "+x + ", " + y + "Angle: " + angle);
        } else {
            x = -100;
            y = -100;
        }

        shot.setFrame(x, y, SHOT_SIZE, SHOT_SIZE);
    }
    
    /**
     * Draws the object
     * @param g the graphics context to draw on
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fill(shot);
    }
    
}
