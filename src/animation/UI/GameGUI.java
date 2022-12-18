package animation.UI;

import java.awt.BorderLayout;
import animation.Asteroids;
import animation.LargeAsteroids;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import animation.AbstractAnimation;
import animation.Ship;
import animation.Shot;

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

    // Create 5 large asteroids at the start of the game
    private Asteroids asteroid1 = new LargeAsteroids(this);
    private Asteroids asteroid2 = new LargeAsteroids(this);
    private Asteroids asteroid3 = new LargeAsteroids(this);
    private Asteroids asteroid4 = new LargeAsteroids(this);
    private Asteroids asteroid5 = new LargeAsteroids(this);
    
    // List contains all asteroids on the screen
    CopyOnWriteArrayList<Asteroids> asteroids = new CopyOnWriteArrayList<Asteroids>() {{add(asteroid1); add(asteroid2); add(asteroid3); add(asteroid4); add(asteroid5);}};

    //Label used to show the lives on the screen
    private static JLabel livesUpdate;
    
    //number of lives the ship have left
    private static int lives = 3;

    //Label used to show the scores on the screen
    private static JLabel scoreUpdate;

    //number of scores the ship earned
    private static int score = 0;

    //Label used to show the reult of a game
    private static JLabel gameResult = new JLabel();
    
    //Button used to replay the game when died or win
    private static JButton replayButton = new JButton("Replay");
    
    //ship object
    private Ship ship = new Ship(this);

    //use to check if the frame is moving
    private boolean moving = true;

    /**
     * Constructs an animation and initializes it to be able to accept key
     * input.
     */
    @SuppressWarnings("boxing")
    public GameGUI() {

        //modify score lablel UI
        //set score format to stay at 4 digits
        scoreUpdate = new JLabel(String.format("%04d", score));
        scoreUpdate.setForeground(Color.white);
        scoreUpdate.setBackground(null);
        scoreUpdate.setFont(new Font("Monospaced", Font.PLAIN, 25));
        
        //modify lives lablel UI
        livesUpdate = new JLabel("Lives: " + lives);
        livesUpdate.setForeground(Color.white);
        livesUpdate.setBackground(null);
        livesUpdate.setFont(new Font("Monospaced", Font.PLAIN, 20));
        livesUpdate.setVerticalAlignment(SwingConstants.TOP);
        
        //modify game result lablel UI
        gameResult.setForeground(Color.white);
        gameResult.setBackground(Color.black);
        gameResult.setFont(new Font("Monospaced", Font.PLAIN, 25));
        gameResult.setHorizontalAlignment(SwingConstants.CENTER);
        
        //modify replay button UI
        replayButton.setForeground(Color.white);
        replayButton.setBackground(Color.black);
        replayButton.setFont(new Font("Monospaced", Font.PLAIN, 25));
        replayButton.setVerticalAlignment(SwingConstants.BOTTOM);
        replayButton.setBorderPainted(false);
        replayButton.setContentAreaFilled(false);
        replayButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //creating new asteroid
                newAsteroids();
                
                //remove the replay button, update the new game score, result 
                //and lives on the screen
                remove(replayButton);
                lives = 3;
                livesUpdate.setText("Lives: " + lives);
                score = 0;
                scoreUpdate.setText(String.format("%04d", score));
                gameResult.setText("");
                
                //reset the ship to it's original starting point
                ship.die();
                
                //start the thread
                start();
            }
            
        });
        
        //create the info panel containing score and lives
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(scoreUpdate);
        infoPanel.add(livesUpdate);
        //set background to transparent
        infoPanel.setOpaque(false);
        
        //set layout and add the labels
        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.PAGE_START);
        add(gameResult, BorderLayout.CENTER);
        
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
                    
                    //when the asteroid hit the ship
                    //ship will die, deduct the lives count and reset the lives label
                    ship.die();
                    lives--;
                    livesUpdate.setText("Lives: " + lives);
                    
                    //end the game when ship have no more lives
                    if (lives == -1) {
                        gameOver();
                    }
                } 
            }
            
            CopyOnWriteArrayList<Shot> shotList = ship.getShots(); 
            
            for (int i = 0; i < shotList.size(); i++) {
                for (animation.Asteroids asteroid : asteroids) {
                    
                    //increase score when shot hit an asteroid
                    if (checkCollision(asteroid.getShape(), shotList.get(i).getShape())) {
                        
                        //check if the shot is hitting large asteroids
                        if (asteroid.getClass() == animation.LargeAsteroids.class) {
                            score+=20;
                            scoreUpdate.setText(String.format("%04d", score));
                        } 
                        
                        //check if the shot is hitting medium asteroids
                        else if (asteroid.getClass() == animation.MediumAsteroids.class) {
                            score+=50;
                            scoreUpdate.setText(String.format("%04d", score));
                        } 
                        
                        //check if the shot is hitting small asteroids
                        else if (asteroid.getClass() == animation.SmallAsteroids.class) {
                            score+=100;
                            scoreUpdate.setText(String.format("%04d", score));
                        }
                        
                        //for each 10000 score add a life
                        if (score%10000 == 0) {
                            lives++;
                            livesUpdate.setText("Lives: " + lives);
                        }
                        
                        //split the asteroid
                        asteroid.split(asteroid.getAngle(), asteroid.getLocationX(), asteroid.getLocationY()); 
                        
                        //remove this asteroid and add the smaller in
                        asteroids.remove(asteroid);
                        asteroids.addAll(asteroid.getAsteroids());
                    }   
                }
            }
            
            //if there is not more asteroid, the player win
            if (asteroids.size() == 0) {
                win();
            }
            
        }
    }
    
    /**
     * Check whether two objects collide. This tests whether their shapes
     * intersect.
     * 
     * @param shape1 asteroid
     * @param shape2 shot
     * @return true if the shapes intersect
     */
    public boolean checkCollision(Shape shape1, Shape shape2) {
        return shape1.intersects(shape2.getBounds2D());
    }
    
    /**
     * Add game over to the screen and stop the thread
     * 
     */
    private void gameOver() {
        gameResult.setText("GAME OVER");
        add(replayButton, BorderLayout.PAGE_END);
        stop();
    }
    
    /**
     * Add winning message to the screen (not working though) and stop the thread
     * 
     */
    private void win() {
        gameResult.setText("YOU WON!");
        add(replayButton, BorderLayout.PAGE_END);
        stop();
    }
    
    /**
     * Add 5 new asteroids
     * 
     */
    private void newAsteroids() {
        asteroid1 = new LargeAsteroids(this);
        asteroid2 = new LargeAsteroids(this);
        asteroid3 = new LargeAsteroids(this);
        asteroid4 = new LargeAsteroids(this);
        asteroid5 = new LargeAsteroids(this);
        asteroids = new CopyOnWriteArrayList<Asteroids>() {{add(asteroid1); add(asteroid2); add(asteroid3); add(asteroid4); add(asteroid5);}};
    }
    
    /**
     * Paint the animation by painting the objects in the animation.
     * 
     * @param g the graphic context to draw on
     */
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        // Paint asteroids
        for (Asteroids asteroid : asteroids) {
            asteroid.paint((Graphics2D) g);
        }
        
        // Paint ship
        ship.paint((Graphics2D) g);
        
        // Paint shots
        for(Iterator<Shot> shots = ship.getShots().iterator(); shots.hasNext();) {
            shots.next().paint((Graphics2D) g);
        }
        
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
        GameGUI game = new GameGUI();

        // Add the animation to the window
        Container contentPane = f.getContentPane();
        contentPane.add(game, BorderLayout.CENTER);

        // Display the window.
        f.setVisible(true);

        // Start the animation
        game.start();
    }

}
