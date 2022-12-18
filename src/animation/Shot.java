
package animation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D.Double;

/**
 * This class creates the Shot
 *
 */
public class Shot implements AnimatedObject {

    // Width of the screen
    private static final int WIDTH = 600;

    // Radius of the shot
    private static final int SHOT_SIZE = 5;

    // Fixed increment of speed from ship's speed
    private static final int INCREMENT = 5;

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
    private Double vectorTarget;

    // Used to check if the bullet is moving
    private boolean moving = false;

    // Used to keep track of current speed
    private double speed = 0;

    /**
     * Create the Shot object
     * @param animation the animation that this object is part of
     * @param speedShip the speed of the object carrying shot
     * @param angle the angle that the object is pointing
     * @param x coordinate of the object
     * @param y coordinate of the object
     */

    public Shot(AbstractAnimation animation, double speedShip,
            double rotatingAngle, double x, double y) {
        this.animation = animation;

        // Set the angle of flying and the coordinates of the shot
        this.angle = rotatingAngle;
        this.x = x;
        this.y = y;

        // Create the shot on the screen
        shot = new Ellipse2D.Double(x, y, SHOT_SIZE, SHOT_SIZE);

        // Set the speed of the shot
        this.speed = INCREMENT + speedShip;

        // Set direction vector of the shot
        vectorTarget = new Double(this.x, this.y - this.speed);
    }

    /**
     * Updates the object's state as you want it to appear on the next frame of
     * the animation.
     */
    public void nextFrame() {
        if (moving) {

            // Check if the shot gets out of the screen
            if (!(x < WIDTH + 5 && y < WIDTH + 5 && x > 0 - 5 && y > 0 - 5)) {

                // Stop moving if it gets out of the screen
                moving = false;
            } else {
                // Continue moving if is moving
                move();
            }
        }
    }

    /**
     * Draws the object
     * 
     * @param g the graphics context to draw on
     */
    public void paint(Graphics2D g) {

        // Draw the shot
        g.setColor(Color.WHITE);
        g.fill(shot);

        // Reset the coordinates of the shot
        shot.setFrame(x, y, SHOT_SIZE, SHOT_SIZE);
    }

    /**
     * Bullet flies
     */
    public void move() {

        // Set the direction vector
        vectorTarget.setLocation(x + (speed) * Math.sin(angle),
                y - (speed) * Math.cos(angle));

        // Find the point to move to from (x,y)
        // and update (x,y) to that point
        x += (vectorTarget.getX() - x);
        y += (vectorTarget.getY() - y);

        // Set moving variable to true to continue moving in the next frame
        moving = true;
    }
    
    /**
     * Get moving status
     * 
     * @return the status of the bullet
     */
    public boolean getMoving() {
        return moving;
    }
    
    /**
     * Get shape
     * 
     * @return the shot's shape
     */
    public Shape getShape() {
        return shot;
    }
    
    /**
     * Get the speed of the shot
     * @return the speed of the shot
     */
    protected double getSpeed() {
        return speed;
    }
    
    /**
     * Get the x coordinate
     * @return x coordinate
     */
    protected double getX() {
        return x;
    }
    
    /**
     * Get the y coordinate
     * @return y coordinate
     */
    protected double getY() {
        return y;
    }
    
    /**
     * Get the angle of the shot
     * @return angle of the shot
     */
    protected double getAngle() {
        return angle;
    }
    
    /**
     * Set the moving status of the shot
     * @param newMoving true if shot is moving, otherwise false
     */
    public void setMoving(boolean newMoving) {
        moving = newMoving;
    }
    
    /**
     * Set the x coordinate to the passed-in x value
     * @param newX passed-in value
     */
    protected void setX(double newX) {
        x = newX;
    }
    
    /**
     * Set the y coordinate to the passed-in y-value
     * @param newY passed-in value
     */
    protected void setY(double newY) {
        y = newY;
    }
}
