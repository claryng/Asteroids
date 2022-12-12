/**
 * 
 */
package animation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D.Double;
import java.util.concurrent.CopyOnWriteArrayList;

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

    // Original Angle of direction
    private double angle = 0;

    // Direction of movement: The pointing point of the ship (the triangle)
    private Double vectorTarget = new Double(X, Y - 3);

    // Used to check if the ship is moving
    private boolean moving = false;

    // Used to keep track of current speed
    private double speed = 0;

    // Used to keep track of number of frames
    private int frames = 1;

    // List of bullets
    private CopyOnWriteArrayList<Shot> shotList = new CopyOnWriteArrayList<>();

    // Rotating angle
    private double rotatingAngle = 0;

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
     * Updates the object's state as you want it to appear on the next frame of
     * the animation.
     */
    public void nextFrame() {

        // Stop moving when speed is near 0
        if (moving && speed < 0.5) {
            moving = false;
            angle = rotatingAngle;
        }

        // Speed decreases by 10% every 3 frames
        if (moving && frames % 3 == 0 && frames > 0) {
            speed = (speed * 90) / 100;
        }

        // The ship keeps moving when speed is larger than 1
        if (moving) {
            frames++;
            move();
        }
    }

    /**
     * Draws the object
     * 
     * @param g the graphics context to draw on
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.draw(getShape());
    }

    /**
     * Returns the shape after applying the current translation and rotation
     * 
     * @return the shape located as we want it to appear
     */
    public Shape getShape() {

        // AffineTransform captures the movement and rotation we
        // want the ship to have
        AffineTransform affineTransform = new AffineTransform();

        // x, y are where the origin of the shape will be. In this
        // case, this is the center of the triangle. See the constructor
        // to see where the points are.
        affineTransform.translate(x, y);

        setVectorTarget(speed);

        // Rotate the ship
        affineTransform.rotate(rotatingAngle);

        AffineTransform at = affineTransform;

        // Create a shape that looks like our triangle, but centered
        // and rotated as specified by the AffineTransform object.
        return at.createTransformedShape(ship);
    }

    /**
     * Move the ship in its current direction
     */
    public void move() {
        // Find coordinates using calculus: position vector
        x = x + 1 * ((vectorTarget.getX() - x));
        y = y + 1 * ((vectorTarget.getY() - y));

        // Wrap the ship around the screen
        x = (x <= 0) ? WIDTH + x : x % WIDTH;
        y = (y <= 0) ? WIDTH + y : y % WIDTH;

        // Change the vector target according to the new coordinates
        setVectorTarget(speed);

        // Set moving flag to true to continue moving in the next frames
        moving = true;
    }

    /**
     * Rotate counter-clockwise
     */
    public void rotateLeft() {
        rotatingAngle -= 0.2;
    }

    /**
     * Rotate clockwise
     */
    public void rotateRight() {
        rotatingAngle += 0.2;
    }

    /**
     * Reset vector_target when direction of movement changes
     * 
     * @param speed the speed is the radius of the circle with (x,y) center
     */
    private void setVectorTarget(double speed) {
        // -> positive x-axis
        // positive y-axis is reversed
        double x_direction_point = x + speed * Math.sin(angle);
        double y_direction_point = y - speed * Math.cos(angle);
        vectorTarget.setLocation(x_direction_point, y_direction_point);
    }

    /**
     * Send the ship into hyperspace
     */
    public void space() {
        // Set max, min value so that the ship still jumps to coordinates inside
        // the screen
        double min = 20;
        double max = WIDTH - 20;

        // Generate random coordinates to send the ship into hyperspace
        double range = max - min + 1;
        x = (Math.random() * range) + min;
        y = (Math.random() * range) + min;

        // Change the vector target according to new coordinates
        setVectorTarget(speed);
    }

    /**
     * Set frames and increase speed at each thrust by 3 pixels each frame
     */
    public void thrust() {
        frames = 0;
        speed += 3;
        angle = rotatingAngle;
    }

    /**
     * Fire shots
     */
    public void fire() {
        for (Shot shot : shotList) {
            shot.move();
        }
    }

    /**
     * Add shots to list of shots
     */
    public void addShots() {
        Shot shot = new animation.Shot(animation, speed, rotatingAngle,
                vectorTarget.getX(), vectorTarget.getY());
        shotList.add(shot);
    }

    /**
     * Get the list of shots
     * @return list of shots
     */
    public CopyOnWriteArrayList<Shot> getShots() {
        return shotList;
    }
    
    /**
     * Die
     */
    public void die() {
        x = X;
        y = Y;
        setVectorTarget(3);
        speed = 0;
    }
    
}
