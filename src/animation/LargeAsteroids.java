package animation;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * This class creates a large asteroid and is an inheritance of Asteroids 
 */
public class LargeAsteroids extends Asteroids {
    
    private Polygon asteroid; 
    private double angle;
    
    /**
     * Constructor to create a large asteroid
     */
    public LargeAsteroids(AbstractAnimation animation) {
        
        super(animation);
        
        // Size of a large asteroid: 40 x 60
        asteroid = new Polygon();
        asteroid.addPoint(-2, -19);
        asteroid.addPoint(2, -20);
        asteroid.addPoint(7, -30);
        asteroid.addPoint(12, -30);
        asteroid.addPoint(20, -21);
        asteroid.addPoint(20, -17);
        asteroid.addPoint(13, -1);
        asteroid.addPoint(13, 1);
        asteroid.addPoint(18, 9);
        asteroid.addPoint(20, 11);
        asteroid.addPoint(15, 28);
        asteroid.addPoint(12, 30);
        asteroid.addPoint(-4, 30);
        asteroid.addPoint(-6, 30);
        asteroid.addPoint(-8, 17);
        asteroid.addPoint(-10, 17);
        asteroid.addPoint(-14, 22);
        asteroid.addPoint(-16, 22);
        asteroid.addPoint(-20, 11);
        asteroid.addPoint(-16, -20);
        asteroid.addPoint(-9, -30);
        asteroid.addPoint(-7, -30);
        
        // Set random starting point and angle
        setRandom();
        this.angle = setRandomAngle();     
    }
    
    /**
     * Returns the shape after applying the current translation and rotation
     * 
     * @return the shape located as we want it to appear
     */
    public Shape getShape() {
        
        // AffineTransform captures the movement and rotation we
        // want the asteroid to have
        AffineTransform affineTransform = new AffineTransform();

        // x, y are where the origin of the shape will be. In this
        // case, this is the center of the polygon. 
        affineTransform.translate(getLocationX(), getLocationY());

        AffineTransform at = affineTransform;

        // Create a shape that looks like our polygon, but centered
        // and rotated as specified by the AffineTransform object.
        return at.createTransformedShape(asteroid);
    }
    
    public void paint(Graphics2D g) {
        super.paint(g);
        g.draw(getShape());
    }
    
    public void move() {
        super.move();
    }
    
    public void setTarget() {
        super.setTarget();
    }
    
    public void nextFrame() {
        super.nextFrame();   
    }

    public double getAngle() {
        return angle;
    }
}
