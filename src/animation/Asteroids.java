package animation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    
    // Animation that contains the object
    private AbstractAnimation animation;
    
    // Random variable used to generate asteroids randomly
    private Random rand = new Random();
    
    /**
     * Constructor
     */
    public Asteroids(AbstractAnimation animation) { 
        this.animation = animation;
        this.setRandom();
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
    
    public double getAngle() {
        return angle;
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
    
    public void setAngle() {
        if (targetedY == 570) {
            angle = Math.atan(Math.abs((getLocationX() - getTargetedX())) / Math.abs((getLocationY() - getTargetedY())));
        } else if (targetedY == 30) {
            angle = Math.atan(Math.abs((getLocationX() - getTargetedX())) / Math.abs((getLocationY() - getTargetedY()))) - Math.PI;
        } else if (targetedX == 30 || targetedX == 570) {
            angle = Math.atan(Math.abs((getLocationY() - getTargetedY())) / Math.abs((getLocationX() - getTargetedX())));
        }
    }
    
     
    public void move() {
        
    }
    /**
     * 
     */
    public void setRandom() {
        locationX = Math.random() * (MAX - MIN) + MIN;
        System.out.println(locationX);
        
        // The asteroid is always coming from outside of screen
        if (locationX < 0 || locationX > 600) {
            locationY = Math.random() * (MAX - MIN) + MIN;
            // left edge
            if (locationX < 0) {
                targetedX = 30;
            // right edge
            } else {
                targetedX = 570;
            }
            targetedY = Math.random() * (150) + 250;
        // Randomize if X is greater than 0
        } else {
            List<Integer> givenList = Arrays.asList(1, 2);
            Random rand = new Random();
            int randomElement = givenList.get(rand.nextInt(givenList.size()));
            System.out.println(randomElement);
            
            // Upper edge
            if (randomElement == 1) {
                locationY = Math.random() * 50 - 50;
                System.out.println(locationY);
                targetedY = 30;
            // Asteroid is coming from the bottom edge
            } else {
                locationY = Math.random() * 50 + 600;
                System.out.println(locationY);
                targetedY = 570;
            }
            targetedX = Math.random() * (150) + 250;
            System.out.println(locationX);
        }
    }
    
    /**
     * Moves the ball a small amount. If it reaches the left or right edge, it
     * bounces.
     */
    public void nextFrame() {
        
//        // If hit by a bullet, the asteroid is destroyed
//        if (isHit) {
//            isDestroyed = true;
//        }
//        
//        // The asteroid keeps moving if it is not destroyed
//        if (!isDestroyed) {
//            frames++;
//            move();
     }
    
    /**
     * Draws an asteroid.
     * 
     * @param g the graphics context to draw on.
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
////        g.draw(getShape());
    }
    
}
