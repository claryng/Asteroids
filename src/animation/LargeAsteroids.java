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
        // Size of a large asteroid: 30 x 60
        asteroid = new Polygon();
        asteroid.addPoint(0, 30);
        asteroid.addPoint(20, 15);
        asteroid.addPoint(20, -15);
        asteroid.addPoint(0, -30);
        asteroid.addPoint(-20, -15);
        asteroid.addPoint(-20, 15);
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
