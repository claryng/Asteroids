package animation;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Abstract class for creating asteroids of 3 sizes: large, medium, small
 */
public abstract class Asteroids implements AnimatedObject {
    
    // Width of the screen
    protected static final int WIDTH = 600;
    
    // Speed of the asteroid
    protected static final double SPEED = 2.0;
    
    // Maximum and minimum X and Y coordinates where the asteroid flies in
    private static final double MIN = -50;
    private static final double MAX = 650;
    
    // Center coordinates of an asteroid
    private double locationX;
    private double locationY;
    
    // Direction coordinates of an asteroid: The point the asteroid will fly to 
    private double targetedX;
    private double targetedY;
    
    // Direction angle
    private double angle;

    // Shape of the asteroid
    private Polygon asteroid;
    
    // Check if asteroid hit by the shot
    private boolean isHit = false;
    
    // Animation that contains the object
    private AbstractAnimation animation;
    
    // List of smaller asteroids broken up from larger asteroids
    private CopyOnWriteArrayList<Asteroids> asteroids;
    
    /**
     * Create the Asteroids object
     * @param animation the animation that this object is part of
     */
    public Asteroids(AbstractAnimation animation) { 
        this.animation = animation;
    }
    
    /**
     * Return the asteroid angle
     * @return asteroid angle
     */
    public abstract double getAngle();
    
    /**
     * Returns the x coordinate of the asteroid
     * @return the x coordinate of the center of the asteroid
     */
    public double getLocationX() {
        return locationX;
    }
    
    /**
     * Returns the y coordinate of the asteroid
     * @return the y coordinate of the center of the asteroid
     */
    public double getLocationY() {
        return locationY;
    }
    
    /**
     * Return the x coordinate of the direction point
     * @return the x coordinate of the point where we want the asteroid to fly to
     */
    public double getTargetedX() {
        return targetedX;
    }
    
    /**
     * Return the y coordinate of the direction point
     * @return the y coordinate of the point where we want the asteroid to fly to
     */
    public double getTargetedY() {
        return targetedY;
    }
    
    /**
     * Return the list of smaller asteroids broken up from one asteroid
     * @return list of broken up asteroids
     */
    public CopyOnWriteArrayList<Asteroids> getAsteroids() {
        return asteroids;
    }
    
    /**
     * Reset x coordinate of the center of the asteroid
     * @param x
     */
    public void setLocationX(double x) {
        locationX = x;
    }
    
    /**
     * Reset y coordinate of the center of the asteroid
     * @param x
     */
    public void setLocationY(double y) {
        locationY = y;
    }
    
    /**
     * Reset x coordinate of the point where asteroid will fly to
     * @param x
     */
    public void setTargetedX(double x) {
        targetedX = x;
    }
    
    /**
     * Reset y coordinate of the point where asteroid will fly to
     * @param x
     */
    public void setTargetedY(double y) {
        targetedY = y;
    }

    /**
     * Randomly set the angle with which a large asteroid will fly into the screen
     * based on the edge where the asteroid starts
     * @return a random direction angle
     */
    public double setRandomAngle() {
        // The asteroid is flying from the bottom edge of screen
        if (targetedY == 570) {
            
            // Left side of bottom edge
            if (targetedX < 300) {
                angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / Math.abs((getLocationX() - getTargetedX())));
            // Right side of bottom edge
            } else {
                angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / ((-1) * Math.abs((getLocationX() - getTargetedX()))));
            }
        
        // The asteroid is flying from the upper edge of screen
        } else if (targetedY == 30) {
            
            // Left side of upper edge
            if (targetedX < 300) {
                angle = Math.PI/2 + (Math.atan(Math.abs((getLocationY() - getTargetedY())) / (Math.abs((getLocationX() - getTargetedX())))));
            // Right side of upper edge
            } else {
                angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / (Math.abs((getLocationX() - getTargetedX())))) - Math.PI;
            }       
        
        // The asteroid is flying from the left edge of screen
        } else if (targetedX == 30) {
            
            // Upper side of left edge
            if (targetedX < 300) {
                angle = Math.PI/2 + (Math.atan(Math.abs((getLocationY() - getTargetedY())) / (Math.abs((getLocationX() - getTargetedX())))));
            // Bottom side of left edge
            } else {
                angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / Math.abs((getLocationX() - getTargetedX())));
            }
        
        // The asteroid is flying from the right edge of screen
        } else if (targetedX == 570) {
            
            // Upper side of right edge
            if (targetedX < 300) {
                angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / ((-1) * Math.abs((getLocationX() - getTargetedX())))) - Math.PI;
            // Bottom side of right edge
            } else {
                angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / (Math.abs((getLocationX() - getTargetedX())))) - Math.PI/2;
            }
            
        }
        return angle;
    }
    /**
     * Move the asteroid in its current direction
     */
    public void move() {
        
        if (!isHit) {
            // Find coordinates using calculus: position vector
            setLocationX (this.getLocationX() + 1 * ((this.getTargetedX() - this.getLocationX())));
            setLocationY (this.getLocationY() + 1 * ((this.getTargetedY() - this.getLocationY())));
    
            //Wrap the asteroid around the screen
            setLocationX ((this.getLocationX() <= 0) ? WIDTH + this.getLocationX() : this.getLocationX() % WIDTH);
            setLocationY ((this.getLocationY() <= 0) ? WIDTH + this.getLocationY() : this.getLocationY() % WIDTH);
    
            // Change the vector target according to the new coordinates
            setTarget();
        
        // Disappears by moving to outside of screen if hit by a shot
        } else {
            setLocationX(-300);
            setLocationY(-300);
        }
    }
    
    /**
     * Reset asteroid targeted direction point 
     */
    public void setTarget() {
        setTargetedX(this.getLocationX() + SPEED * Math.sin(getAngle()));
        setTargetedY(this.getLocationY() - SPEED * Math.cos(getAngle()));
    }
    
    /**
     * Randomly choose x and y coordinates of a large asteroid's starting point and
     * set the x and y coordinates of the point the asteroid will fly to on the screen
     * Asteroid must fly in from outside of screen 
     */
    public void setRandom() {
        // Random starting x coordinate
        locationX = Math.random() * (MAX - MIN) + MIN;
        
        // Asteroid is always coming from outside of screen
        if (locationX < 0 || locationX > 600) {
            locationY = Math.random() * (MAX - MIN) + MIN;
            
            // Asteroid is coming from left edge of screen
            if (locationX < 0) {
                targetedX = 30;
            
            // Asteroid is coming from right edge of screen 
            } else if (locationX  > 600) {
                targetedX = 570;
            }
            targetedY = Math.random() * (200) + 200;
            
        // If starting x coordinate is in the screen, starting y coordinate must be outside of screen
        } else if (locationX >= 0) {
            
            // Randomly choose between 0 and 1
            // If 1, asteroid is coming from upper edge
            // If 2, asteroid is coming from bottom edge
            List<Integer> randomList = Arrays.asList(1, 2);
            Random rand = new Random();
            int randomElement = randomList.get(rand.nextInt(randomList.size()));
            
            if (randomElement == 1) {
                locationY = Math.random() * 50 - 50;
                targetedY = 30;
            } else {
                locationY = Math.random() * 50 + 600;
                targetedY = 570;
            }
            targetedX = Math.random() * (200) + 200;
        }
    }
   
    /**
     * Moves the asteroid by its specified amount in each frame
     */
     public void nextFrame() {
         move();
     }
    
    /**
     * Returns the shape of the asteroid after applying the current translation and rotation
     * @return the shape located as we want it to appear
     */
    public abstract Shape getShape();
    
    /**
     * Draws an asteroid.
     * 
     * @param g the graphics context to draw on.
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
    }
    
    /**
     * Split an asteroid into smaller asteroids if hit by a shot
     * Each large asteroid is split into two medium asteroids
     * Each medium asteroid is split into two smaller asteroids
     * Smaller asteroids move at the same speed. Speed is offset by +/- pi/4 radians
     * @param angle angle of parent asteroid 
     * @param x x coordinate of the center of parent asteroid
     * @param y y coordinate of the center of parent asteroid
     */
    public void split(double angle, double x, double y) {
        
        // List of broken up asteroids
        asteroids = new CopyOnWriteArrayList<>();
        
        // Split a large asteroid
        if (this.getClass() == LargeAsteroids.class) {
            Asteroids a = new MediumAsteroids(animation, angle - Math.PI/4, x, y);
            Asteroids b = new MediumAsteroids(animation, angle + Math.PI/4, x, y);
            
            asteroids.add(a);
            asteroids.add(b);
        
            // Split a medium asteroid
            
        } else if (this.getClass() == MediumAsteroids.class){
            Asteroids a = new SmallAsteroids(animation, angle - Math.PI/4, x, y);
            Asteroids b = new SmallAsteroids(animation, angle + Math.PI/4, x, y);
            
            asteroids.add(a);
            asteroids.add(b);  
        }  
        this.isHit = true;
    }
    
}