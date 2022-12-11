/**
 * 
 */
package animation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
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
    private Double vectorTarget;

    // Used to check if the bullet is moving
    private boolean moving = false;

    // Used to keep track of current speed

    private double speed = 0;

    /**
     * Create the Ship object
     * 
     * @param animation the animation that this object is part of
     * @param speedShip the speed of the object carrying shot
     * @param angle the angle that the object is pointing
     * @param x coordinate of the object
     * @param y coordinate of the object
     */

    public Shot(AbstractAnimation animation, double speedShip,
            double rotatingAngle, double x, double y) {
        this.animation = animation;
        this.angle = rotatingAngle;
        this.x = x;
        this.y = y;
        shot = new Ellipse2D.Double(x, y, SHOT_SIZE, SHOT_SIZE);

        this.speed = INCREMENT + speedShip;

        vectorTarget = new Double(this.x, this.y - this.speed);
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
            if (!(x < 605 && y < 605 && x > -5 && y > -5)) {
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
        vectorTarget.setLocation(x + (speed) * Math.sin(angle),
                y - (speed) * Math.cos(angle));
        x += (vectorTarget.getX() - x);
        y += (vectorTarget.getY() - y);
        moving = true;
    }

    /**
     * Get status
     * 
     * @return the status of the bullet
     */
    public boolean getMoving() {
        return moving;
    }

    @Override
    public Shape getShape() {
        return shot;
    }
}
