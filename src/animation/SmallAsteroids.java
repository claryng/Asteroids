package animation;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * This class creates a small asteroid and 
 * is an inheritance of Asteroids 
 */
public class SmallAsteroids extends Asteroids {
    
    private Polygon asteroid;
    private double angle;
    
    /**
     * Constructor to create a small asteroid 
     * based on parent asteroid angle and coordinates
     */
    public SmallAsteroids(AbstractAnimation animation, double angle, double x, double y) {
        
        super(animation);
        
        this.angle = angle;
        
        // Size of a small asteroid: 10 x 15
        asteroid = new Polygon();
        asteroid.addPoint(0, 10);
        asteroid.addPoint(5, 5);
        asteroid.addPoint(5, 0);
        asteroid.addPoint(0, -5);
        asteroid.addPoint(-5, 0);
        asteroid.addPoint(-5, 5);
        // Starting point is the same as parent's current center
        setLocationX(x);
        setLocationY(y);
        
        setTarget();
        move();
        move();
        move();
    }

    public Shape getShape() {
        
        // AffineTransform captures the movement and rotation we
        // want the asteroid to have
        AffineTransform affineTransform = new AffineTransform();

        // x, y are where the origin of the shape will be. In this
        // case, this is the center of the polygon. See the constructor
        // to see where the points are.
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
