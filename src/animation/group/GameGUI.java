package animation.group;

import java.awt.BorderLayout;
import animation.AnimatedObject;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.util.LinkedList;
import java.util.List;

import animation.AbstractAnimation;
import animation.Ship;
import animation.Shot;
import animation.UFO;

/**
 * This class provides a simple demonstration of how you would implement an
 * animation (or game!) that contains multiple animated objects.
 *
 */
public class GameGUI extends AbstractAnimation implements KeyListener {
    // The width of the window, in pixels.
    private static final int WINDOW_WIDTH = 600;

    // The height of the window, in pixels.
    private static final int WINDOW_HEIGHT = 600;

    // The object that moves during the animation. You might have
    // many objects!
    
    private animation.Asteroids asteroid1 = new animation.LargeAsteroids(this);
    private animation.Asteroids asteroid2 = new animation.LargeAsteroids(this);
    private animation.Asteroids asteroid3 = new animation.LargeAsteroids(this);
    private animation.Asteroids asteroid4 = new animation.LargeAsteroids(this);
    private animation.Asteroids asteroid5 = new animation.LargeAsteroids(this);
    
    CopyOnWriteArrayList<animation.Asteroids> asteroids = new CopyOnWriteArrayList<animation.Asteroids>() {{add(asteroid1); add(asteroid2); add(asteroid3); add(asteroid4); add(asteroid5);}};

    
//    private AnimatedObjectDemo shape = new AnimatedObjectDemo(this);

//    private static JLabel scoreUpdate;
//    
//    private static String score = "0000";
//    
//    private static JLabel livesText;
//    
//    private static int lives = 4;
    private static JLabel scoreUpdate;

    private static String score = "0000";

    private Ship ship = new animation.Ship(this);

//    private animation.Shot shot;

//    private animation.UFO ufo = new animation.UFO(this);

    private boolean moving = true;

    /**
     * Constructs an animation and initializes it to be able to accept key
     * input.
     */
    public GameGUI() {

//        ufo.appear();

        scoreUpdate = new JLabel(score);
        scoreUpdate.setForeground(Color.white);
        scoreUpdate.setBackground(Color.black);
        scoreUpdate.setFont(new Font("Monospaced", Font.PLAIN, 25));

        setLayout(new BorderLayout());
        add(scoreUpdate, BorderLayout.PAGE_START);

        // Allow the game to receive key input
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    /**
     * Updates the animated object for the next frame of the animation and
     * repaints the window.
     */
    protected void nextFrame() {
        if (moving) {
            
            for (animation.Asteroids asteroid : asteroids) {
                asteroid.nextFrame();
            }
//            asteroid1.nextFrame();
//            asteroid2.nextFrame();
//            asteroid3.nextFrame();
//            asteroid4.nextFrame();
//            asteroid5.nextFrame();
//            repaint();
//            if (checkCollision (shape, triangle)) {
//                moving = false;
//            }
//            ufo.nextFrame();
//            shot.nextFrame();

//            ufo.nextFrame();

            // demo ship
            ship.nextFrame();

            Iterator<Shot> shots = ship.getShots().iterator();
            while (shots.hasNext()) {
                animation.Shot shot = (animation.Shot) shots.next();
                shot.nextFrame();
                if (!shot.getMoving()) {
                    shots.remove();
                }
            }

            repaint();
            
            for (animation.Asteroids asteroid : asteroids) {
                if (checkCollisionShipAsteroid(asteroid, ship)) {
                    ship.die();
                } 
            }
            
            LinkedList<Shot> shotList = ship.getShots(); 
//            ArrayList<animation.Asteroids> smallerAsteroids = new ArrayList<>();
            for (int i = 0; i < shotList.size(); i++) {
                for (animation.Asteroids asteroid : asteroids) {
                    if (checkCollisionShotAsteroid(asteroid, shotList.get(i))) {
                        asteroid.split(); 
                        asteroids.remove(asteroid);
                        asteroids.addAll(asteroid.getAsteroids());
                    }   
                }
            }

//            if (checkCollision (ufo, ship)) {
////                ufo.die();
//            }
            

        }
    }

    /**
     * Check whether two object collide. This tests whether their shapes
     * intersect.
     * 
     * @param shape1 the first shape to test
     * @param shape2 the second shape to test
     * @return true if the shapes intersect
     */
    private boolean checkCollisionShipAsteroid(animation.Asteroids asteroid,Ship ship) {
        return ship.getShape().intersects(asteroid.getShape().getBounds2D());
    }
    
    /**
     * 
     */
    public boolean checkCollisionShotAsteroid(animation.Asteroids asteroid, Shot shot) {
        return asteroid.getShape().intersects(shot.getShape().getBounds2D());
    }
    
    /**
     * Paint the animation by painting the objects in the animation.
     * 
     * @param g the graphic context to draw on
     */
    public void paintComponent(Graphics g) {
        // Note that your code should not call paintComponent directly.
        // Instead your code calls repaint (as shown in the nextFrame
        // method above, and repaint will call paintComponent.

        super.paintComponent(g);
        for (animation.Asteroids asteroid : asteroids) {
            asteroid.paint((Graphics2D) g);
        }


//        ufo.paint((Graphics2D) g);

        // SHIP demo
        ship.paint((Graphics2D) g);
//        for (animation.Shot s : ship.getShots()) {
//            s.paint((Graphics2D) g);
//        }
        
        for(Iterator<Shot> shots = ship.getShots().iterator(); shots.hasNext();) {
            shots.next().paint((Graphics2D) g);
        }
    }

    @Override
    /**
     * This is called on the downward action when the user presses a key. It
     * notifies the animated ball about presses of up arrow, right arrow, left
     * arrow, and the space bar. All other keys are ignored.
     * 
     * @param e information about the key pressed
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
        case KeyEvent.VK_UP:
            ship.thrust();
            ship.move();
            break;
        case KeyEvent.VK_RIGHT:
            ship.rotateRight();
            break;
        case KeyEvent.VK_LEFT:
            ship.rotateLeft();
            break;
        case KeyEvent.VK_H:
            ship.space();
            break;
        case KeyEvent.VK_SPACE:
            ship.addShots();
            ship.fire();
            break;
        default:
            // Ignore all other keys

        }
    }

    @Override
    /**
     * This is called when the user releases the key after pressing it. It does
     * nothing.
     * 
     * @param e information about the key released
     */
    public void keyReleased(KeyEvent e) {
        // Nothing to do
    }

    @Override
    /**
     * This is called when the user presses and releases a key without moving
     * the mouse in between. Does nothing.
     * 
     * @param e information about the key typed.
     */
    public void keyTyped(KeyEvent e) {
        // Nothing to do
    }

    /**
     * The main method creates a window for the animation to run in, initializes
     * the animation and starts it running.
     * 
     * @param args none
     */
    public static void main(String[] args) {
        // JFrame is the class for a window. Create the window,
        // set the window's title and its size.
        JFrame f = new JFrame();
        f.setTitle("Animation Demo");
        f.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        // This says that when the user closes the window, the
        // entire program should exit.
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.getContentPane().setBackground(Color.BLACK);

        // Create the animation.
        GameGUI demo = new GameGUI();

        // Add the animation to the window
        Container contentPane = f.getContentPane();
        contentPane.add(demo, BorderLayout.CENTER);

        // Display the window.
        f.setVisible(true);

        // Start the animation
        demo.start();
    }

}
