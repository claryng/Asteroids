package animation.UI;

import java.awt.BorderLayout;
import animation.AnimatedObject;
import animation.Asteroids;
import animation.LargeAsteroids;
import animation.MediumAsteroids;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import animation.AbstractAnimation;
import animation.AnimatedObject;
import animation.Ship;
import animation.Shot;
import animation.SmallAsteroids;

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
    
    private Asteroids asteroid1 = new LargeAsteroids(this);
    private Asteroids asteroid2 = new LargeAsteroids(this);
    private Asteroids asteroid3 = new LargeAsteroids(this);
    private Asteroids asteroid4 = new LargeAsteroids(this);
    private Asteroids asteroid5 = new LargeAsteroids(this);
    
    CopyOnWriteArrayList<Asteroids> asteroids = new CopyOnWriteArrayList<Asteroids>() {{add(asteroid1); add(asteroid2); add(asteroid3); add(asteroid4); add(asteroid5);}};

    private static JLabel livesUpdate;
    
    private static int lives = 3;

    private static JLabel scoreUpdate;

    private static int score = 0;

    private Ship ship = new animation.Ship(this);
    
    private static JLabel gameOverText = new JLabel();
    
    private static JButton replayButton = new JButton("Replay");

    private boolean moving = true;

    /**
     * Constructs an animation and initializes it to be able to accept key
     * input.
     */
    @SuppressWarnings("boxing")
    public GameGUI() {

        scoreUpdate = new JLabel(String.format("%04d", score));
        scoreUpdate.setForeground(Color.white);
        scoreUpdate.setBackground(Color.black);
        scoreUpdate.setFont(new Font("Monospaced", Font.PLAIN, 25));
        
        livesUpdate = new JLabel("Lives: " + lives);
        livesUpdate.setForeground(Color.white);
        livesUpdate.setBackground(null);
        livesUpdate.setFont(new Font("Monospaced", Font.PLAIN, 20));
        
        gameOverText.setForeground(Color.white);
        gameOverText.setBackground(Color.black);
        gameOverText.setFont(new Font("Monospaced", Font.PLAIN, 25));
        gameOverText.setHorizontalAlignment(SwingConstants.CENTER);
        
        replayButton.setForeground(Color.white);
        replayButton.setBackground(Color.black);
        replayButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        replayButton.setVerticalAlignment(SwingConstants.BOTTOM);
        replayButton.setBorderPainted(false);
        replayButton.setContentAreaFilled(false);
        replayButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                remove(replayButton);
                lives = 3;
                score = 0;
                remove(gameOverText);
                ship.die();
            }
            
        });
        
        setLayout(new BorderLayout());
        add(scoreUpdate, BorderLayout.PAGE_START);
        add(livesUpdate, BorderLayout.PAGE_START);
        add(gameOverText, BorderLayout.CENTER);
        
        // Allow the game to receive key input
        setFocusable(true);
        addKeyListener(this);
    }

    @SuppressWarnings("boxing")
    @Override
    /**
     * Updates the animated object for the next frame of the animation and
     * repaints the window.
     */
    protected void nextFrame() {
        if (moving) {

            for (Asteroids asteroid : asteroids) {
                asteroid.nextFrame();
            }

            // demo ship
            ship.nextFrame();

            Iterator<Shot> shots = ship.getShots().iterator();
            while (shots.hasNext()) {
                Shot shot = shots.next();
                shot.nextFrame();
            }

            // Remove out of screen shots
            for (Shot s : ship.getShots()) {
                if (!s.getMoving()) {
                    ship.getShots().remove(s);
                }
            }
            repaint();
            
            // Check collision of the asteroids with the ship
            for (Asteroids asteroid : asteroids) {
                if (checkCollision(asteroid.getShape(), ship.getShape())) {
                    ship.die();
                    lives--;
                    livesUpdate.setText("Lives: " + lives);
                    if (lives == 0) {
                        gameOver();
                    }
                } 
            }
            
            CopyOnWriteArrayList<Shot> shotList = ship.getShots(); 
            
            for (int i = 0; i < shotList.size(); i++) {
                for (animation.Asteroids asteroid : asteroids) {
                    if (checkCollision(asteroid.getShape(), shotList.get(i).getShape())) {
                        if (asteroid.getClass() == animation.LargeAsteroids.class) {
                            score+=20;
                            scoreUpdate.setText(String.format("%04d", score));
                        } else if (asteroid.getClass() == animation.MediumAsteroids.class) {
                            score+=50;
                            scoreUpdate.setText(String.format("%04d", score));
                        } else if (asteroid.getClass() == animation.SmallAsteroids.class) {
                            score+=100;
                            scoreUpdate.setText(String.format("%04d", score));
                        }
                        
                        if (score%10000 == 0) {
                            lives++;
                            livesUpdate.setText("Lives: " + lives);
                        }
                        asteroid.split(asteroid.getAngle(), asteroid.getLocationX(), asteroid.getLocationY()); 
                        asteroids.remove(asteroid);
                        asteroids.addAll(asteroid.getAsteroids());
                    }   
                }
            }
            
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
    public boolean checkCollision(Shape shape1, Shape shape2) {
        return shape1.intersects(shape2.getBounds2D());
    }
    
    /**
     * Add game over to the screen
     * 
     */
    private void gameOver() {
        gameOverText.setText("GAME OVER");
        add(replayButton, BorderLayout.PAGE_END);
        stop();
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

        // Paint ship
        ship.paint((Graphics2D) g);

        // Paint shots
        for (Shot shot : ship.getShots()) {
            shot.paint((Graphics2D) g);
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
