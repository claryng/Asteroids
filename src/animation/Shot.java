/**
 * 
 */
package animation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D.Double;

/**
 * This class creates the Ship
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
    private Double vector_target;

    // Used to check if the bullet is moving
    private boolean moving = false;

    // Used to keep track of current speed

    private double speed = 0;

    /**
     * Create the Ship object
     * 
     * @param animation the animation that this object is part of
     */

    public Shot(AbstractAnimation animation, double speedShip, double angle,
            double x, double y) {
        this.animation = animation;
        this.angle = angle;
        this.x = x;
        this.y = y;
        shot = new Ellipse2D.Double(x, y, SHOT_SIZE, SHOT_SIZE);

        this.speed = INCREMENT + speedShip;

        vector_target = new Double(this.x, this.y - this.speed);
    }

    /**
     * Updates the object's state as you want it to appear on the next frame of
     * the animation.
     */
    public void nextFrame() {
        if (moving) {
            move();
            System.out.println("Shot speed: " + speed + " Coordinate: " + x
                    + ", " + y + "Angle: " + angle);
            if (!(x < 600 && y < 600 && x > 0 && y > 0)) {
                moving = false;
            }
        }
    }

    /**
     * Draws the object
     * 
     * @param g the graphics context to draw on
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fill(shot);
        shot.setFrame(x, y, SHOT_SIZE, SHOT_SIZE);
    }

    /**
     * Bullet flies
     */
    public void move() {
        x += (vector_target.getX() - x);
        y += (vector_target.getY() - y);
        vector_target.setLocation(x + (speed) * Math.sin(angle),
                y - (speed) * Math.cos(angle));
        moving = true;
    }

    /**
     * Get status
     * @return the status ò the 
     */
    public boolean getMoving() {
        return moving;
    }
}
