package animation;

import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class Asteroid {
 
    
    // Speed of the asteroids. Smaller asteroids move faster than larger asteroids.
    private static final double S_SPEED = 1.5;
    private static final double M_SPEED = 1.0;
    private static final double L_SPEED = 0.5;
    
    // Number of children a large asteroid has
    private static final int NUM_OF_CHILDREN = 3;
    
    // Coordinates of the asteroid
    private double x;
    private double y;
    
    // Shape of the asteroid
    private Polygon asteroid;
    
    // Size of the asteroid
    private int size;
    
    // Check if the asteroid is destroyed
    private boolean isDestroyed = false;
    
    // Random variable used to generate asteroids randomly
    private Random r = new Random();
    
    // Animation that contains the object
    private AbstractAnimation animation;
    
}
