package animation.group;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.Timer;

import animation.AbstractAnimation;

/**
 * This class demonstrates how shapes can be rotated and translated.
 *
 */
public class ShipGUI {
    // The shape that is drawn
    private Polygon p;
    
    // The left edge of the shape
    private double x;
    
    // The top edge of the shape
    private double y;
    
    private double angle = 0;
    
    private AbstractAnimation animation;
    
    /**
     * Constructs a triangle
     */
    public ShipGUI (AbstractAnimation animation) {
        p = new Polygon();
        p.addPoint(-11, 20);
        p.addPoint(0, -20);
        p.addPoint(11, 20);
        
        x = (int)Math.floor(Math.random()*(540 - 20+1)+20);
        y = (int)Math.floor(Math.random()*(540 - 20+1)+20);
        this.animation = animation;
        
        System.out.println(x + ", " + y);
        System.out.println(animation.getWidth() + ", " + animation.getHeight());
    }

    /**
     * Draws the triangle
     * @param g the graphics context to draw on
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.draw(getShape());
    }

    /**
     * Returns the shape after applying the current translation
     * and rotation
     * @return the shape located as we want it to appear
     */
    public Shape getShape() {
        
        AffineTransform at1 = new AffineTransform();
        
        at1.translate(x, y);
        
        at1.rotate(angle);
        
        AffineTransform at = at1;
        
        return at.createTransformedShape(p);
    }
    
    /**
     * 
     */
    public void forward() {
        System.out.println(angle);
        x = x + Math.sin(angle);
        y = y - Math.cos(angle);
        System.out.println(x + ", " + y);
        System.out.println ("Up");
    }

    /**
     * 
     */
    public void thrust() {
        x = x + Math.round(Math.sin(angle));
        y = y - Math.round(Math.cos(angle));
        p.addPoint(5, 20);
        p.addPoint(0, 25);
        p.addPoint(-5, 20);
        System.out.println ("Thrust");
    }
    
    /**
     * 
     */
    public void right () {
        angle = angle + Math.PI/10;
        System.out.println ("Right");
    }
    
    /**
     * 
     */
    public void left () {
        angle = angle - Math.PI/10;
        System.out.println ("Left");
    }
    
    /**
     * 
     */
    public void space () {
        
        x = Math.floor(Math.random()*(540 - 20+1)+20);
        y = Math.floor(Math.random()*(540 - 20+1)+20);
        
        
        System.out.println ("Hyperspace Goooooo!");
    }
    
}
