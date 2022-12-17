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
        
        // Size of a small asteroid: 16 x 16
        asteroid = new Polygon();
        asteroid.addPoint(2, -8);
        asteroid.addPoint(5, -8);
        asteroid.addPoint(3, -2);
        asteroid.addPoint(8, 2);
        asteroid.addPoint(6, 8);
        asteroid.addPoint(2, 5);
        asteroid.addPoint(-3, 6);
        asteroid.addPoint(-6, 3);
        asteroid.addPoint(-7, 4);
        asteroid.addPoint(-7, -4);
        asteroid.addPoint(-2, -3);
        // Starting point is the same as parent's current center
        setLocationX(x);
        setLocationY(y);
        
        setTarget();
        move();
        move();
    }
    
//    public void setAngle(double parentAngle, int no) {
//        super.setAngle(parentAngle, no);
//    }
    
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
