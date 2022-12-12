/**
 * 
 */
package animation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Amy
 *
 */
class ShotTest {
    
    private AnimationStub demo = new AnimationStub();
    
    private double shipSpeed = 3;
    private double shipAngle = 0;
    private double x = 300;
    private double y = 300;
    
    private Shot shot = new Shot(demo, shipSpeed, shipAngle, x, y);

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
    }

  @Test
  void testNextFrame() {
      shot.setMoving(true);
      shot.nextFrame();
      assertEquals(shot.getX(), shot.getSpeed() + shot.getY()*Math.cos(shot.getAngle()));
  }

  @Test
  @SuppressWarnings("boxing")
  void testOutOfBoundShot() {
      
      shot.setX(600);
      shot.setY(300);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setX(0);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setY(0);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setX(600);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setX(300);
      shot.setY(0);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setY(600 + shot.getSpeed());
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setX(0);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setX(600);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
  }
  
}
