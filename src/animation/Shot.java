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
    
<<<<<<< HEAD
    // Radius of the shot
    private static final int SHOT_SIZE = 5;
    
    // Fixed increment of speed from ship's speed
    private static final int INCREMENT = 5;
    
=======
    //diameter of the shot
    private static final int SHOT_SIZE = 5;
    
>>>>>>> 971b94d06f498d7ceb6b094913a10987fccaf72c
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
<<<<<<< HEAD
    private double speed = 0;
=======
    private double speed = 5;
>>>>>>> 971b94d06f498d7ceb6b094913a10987fccaf72c
    
    /**
     * Create the Ship object
     * 
     * @param animation the animation that this object is part of
     */
<<<<<<< HEAD
    public Shot(AbstractAnimation animation, double speedShip, double angle, double x, double y) {
        this.animation = animation;
        this.angle = angle;
        this.x = x;
        this.y = y;
        shot = new Ellipse2D.Double(x,y,SHOT_SIZE,SHOT_SIZE);
        
        this.speed = INCREMENT + speedShip;
        
        vector_target = new Double(this.x, this.y - this.speed);
=======
    public Shot(AbstractAnimation animation, double speed, double x, double y, double angle) {
        this.animation = animation;
        
        this.x = x;
        this.y = y;
        this.angle = angle;
        shot = new Ellipse2D.Double(x, y, SHOT_SIZE, SHOT_SIZE);
        this.speed = this.speed + speed;
        
        vector_target = new Double(x, y - speed);
>>>>>>> 971b94d06f498d7ceb6b094913a10987fccaf72c
    }
    
    /**
     * Updates the object's state as you want it to appear on 
     * the next frame of the animation.
     */
    public void nextFrame() {
<<<<<<< HEAD
        if(moving) {
            move();
            System.out.println("Shot speed: " + speed +  " Coordinate: " + x + ", " + y + "Angle: " + angle);
            if(!(x < 600 && y < 600 && x > 0 && y > 0)) {
                moving = false;
            }
        }
=======
        
        if (x < 600 && y < 600 && x > 0 && y > 0) {
            x = x + (speed)*Math.sin(angle);
            y = y - (speed)*Math.cos(angle);
            System.out.println("Shot speed: " + speed +  " Coordinate: "+x + ", " + y + "Angle: " + angle);
        }

        shot.setFrame(x, y, SHOT_SIZE, SHOT_SIZE);
>>>>>>> 971b94d06f498d7ceb6b094913a10987fccaf72c
    }
    
    /**
     * Draws the object
     * @param g the graphics context to draw on
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fill(shot);
<<<<<<< HEAD
        shot.setFrame(x,y,SHOT_SIZE,SHOT_SIZE);
    }
    
    /**
     * Bullet flies
     */
    public void move() {
        x += (vector_target.getX() - x);
        y += (vector_target.getY() - y);
        vector_target.setLocation(x + (speed) * Math.sin(angle),y - (speed) * Math.cos(angle)); 
        moving = true;
    }
    
    /**
     * Get status
     */
    public boolean getMoving() {
        return moving;
    }
=======
    }
    
>>>>>>> 971b94d06f498d7ceb6b094913a10987fccaf72c
}
