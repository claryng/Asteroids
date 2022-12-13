package animation;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Asteroids implements AnimatedObject {
    
    // Width of the screen
    protected static final int WIDTH = 600;
    
    // Speed of the asteroid
    protected static final double SPEED = 2.0;
    
    // Min max
    private static final double MIN = -50;
    private static final double MAX = 650;
    
    // Coordinates of the asteroid
    private double locationX;
    private double locationY;
    
    // Targeted coordinates where the asteroid will flow to the screen
    private double targetedX;
    private double targetedY;
    
    // Direction angle
    private double angle;
    
    
    // Shape of the asteroid
    private Polygon asteroid;
    
    // Check if hit by the ship
    private boolean isHit = false;
    
    // Animation that contains the object
    private AbstractAnimation animation;
    
    // Random variable used to generate asteroids randomly
    private Random rand = new Random();
    
    // List of broken up asteroids
    private CopyOnWriteArrayList<Asteroids> asteroids;
    
    
    /**
     * Constructor
     */
    public Asteroids(AbstractAnimation animation) { 
        this.animation = animation;
//        this.setRandom();
    }
    
    public double getLocationX() {
        return locationX;
    }
    
    public double getLocationY() {
        return locationY;
    }
    
    public double getTargetedX() {
        return targetedX;
    }
    
    public double getTargetedY() {
        return targetedY;
    }
    
    public abstract double getAngle();
    
//    public boolean getHit() {
//        return isHit;
//    }
    
    public CopyOnWriteArrayList<Asteroids> getAsteroids() {
        return asteroids;
    }
    
    public void setLocationX(double x) {
        locationX = x;
    }
    
    public void setLocationY(double y) {
        locationY = y;
    }
    
    public void setTargetedX(double x) {
        targetedX = x;
    }
    
    public void setTargetedY(double y) {
        targetedY = y;
    }
    
   
    
    public double setRandomAngle() {
        // Bottom edge -- Correct
        if (targetedY == 570) {
            if (targetedX < 300) {
                angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / Math.abs((getLocationX() - getTargetedX())));
            } else {
                angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / ((-1) * Math.abs((getLocationX() - getTargetedX()))));
            }
        // Upper edge
        } else if (targetedY == 30) {
            if (targetedX < 300) {
                angle = Math.PI/2 + (Math.atan(Math.abs((getLocationY() - getTargetedY())) / (Math.abs((getLocationX() - getTargetedX())))));
            } else {
                angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / (Math.abs((getLocationX() - getTargetedX())))) - Math.PI;
            }       
        // Left edge
        } else if (targetedX == 30) {
            if (targetedX < 300) {
                angle = Math.PI/2 + (Math.atan(Math.abs((getLocationY() - getTargetedY())) / (Math.abs((getLocationX() - getTargetedX())))));
            } else {
                angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / Math.abs((getLocationX() - getTargetedX())));
            }
        // Right edge
        } else if (targetedX == 570) {
            if (targetedX < 300) {
                angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / ((-1) * Math.abs((getLocationX() - getTargetedX())))) - Math.PI;
            } else {
                angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / (Math.abs((getLocationX() - getTargetedX())))) - Math.PI/2;
            }
            
        }
        return angle;
    }
     
    /**
     * Move the ship in its current direction
     */
    public void move() {
        
        if (!isHit) {
              
            // Find coordinates using calculus: position vector
            setLocationX (this.getLocationX() + 1 * ((this.getTargetedX() - this.getLocationX())));
            setLocationY (this.getLocationY() + 1 * ((this.getTargetedY() - this.getLocationY())));
    
             //Wrap the ship around the screen
            setLocationX ((this.getLocationX() <= 0) ? WIDTH + this.getLocationX() : this.getLocationX() % WIDTH);
            setLocationY ((this.getLocationY() <= 0) ? WIDTH + this.getLocationY() : this.getLocationY() % WIDTH);
    
            // Change the vector target according to the new coordinates
            setTarget();
        } else {
            setLocationX(-300);
            setLocationY(-300);
        }
        
        
        // Set moving flag to true to continue moving in the next frames
//        moving = true;
    }
    
    public void setTarget() {
        setTargetedX(this.getLocationX() + SPEED * Math.sin(getAngle()));
        setTargetedY(this.getLocationY() - SPEED * Math.cos(getAngle()));
    }
    /**
     * 
     */
    public void setRandom() {
        locationX = Math.random() * (MAX - MIN) + MIN;
        
        // The asteroid is always coming from outside of screen
        if (locationX < 0 || locationX > 600) {
            locationY = Math.random() * (MAX - MIN) + MIN;
            // left edge
            if (locationX < 0) {
                targetedX = 30;
            // right edge
            } else if (locationX  > 600) {
                targetedX = 570;
            } 
            targetedY = Math.random() * (200) + 200;
        // Randomize if X is greater than 0
        } else if (locationX >= 0) {
            List<Integer> givenList = Arrays.asList(1, 2);
            
            Random rand = new Random();
            int randomElement = givenList.get(rand.nextInt(givenList.size()));
//            System.out.println(randomElement);
            
            // Upper edge
            if (randomElement == 1) {
                locationY = Math.random() * 50 - 50;
                targetedY = 30;
            // Bottom edge
            } else {
                locationY = Math.random() * 50 + 600;
                targetedY = 570;
            }
            targetedX = Math.random() * (200) + 200;
        }
//        System.out.println(locationY);
//        System.out.println(locationX);
//        System.out.println(targetedY);
//        System.out.println(targetedX);
       
    }
    
    /**
     * Moves the ball a small amount. If it reaches the left or right edge, it
     * bounces.
     */
    public abstract void nextFrame();
    
    /**
     * Returns the shape after applying the current translation and rotation
     * 
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
    
    public void split(double angle, double x, double y) {
        
        asteroids = new CopyOnWriteArrayList<>();
        
        if (this.getClass() == LargeAsteroids.class) {
            Asteroids a = new MediumAsteroids(animation, angle - Math.PI/4, x, y);
            Asteroids b = new MediumAsteroids(animation, angle + Math.PI/4, x, y);
            
            asteroids.add(a);
            asteroids.add(b);
            
        } else if (this.getClass() == MediumAsteroids.class){
            Asteroids a = new SmallAsteroids(animation, angle - Math.PI/4, x, y);
            Asteroids b = new SmallAsteroids(animation, angle + Math.PI/4, x, y);
            
            asteroids.add(a);
            asteroids.add(b);
              
        }
        this.isHit = true;
    }
    
}