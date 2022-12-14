/**
 * 
 */
package animation;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D.Double;

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
      while (shipSpeed <= 20) {
          while(shipAngle < 2) {
              shot.setMoving(true);
              shot.nextFrame();
              x = x + shot.getSpeed()*Math.sin(shot.getAngle());
              y = y - shot.getSpeed()*Math.cos(shot.getAngle());
              assertEquals(shot.getX(), x);
              assertEquals(shot.getY(), y);
              x = 300;
              y = 300;
              shipSpeed+=3;
              shipAngle+=0.2;
              shot = new Shot(demo, shipSpeed, shipAngle, x, y);
          }
      }
  }

  @Test
  @SuppressWarnings("boxing")
  void testOutOfBoundShot() {
      
      shot.setX(605);
      shot.setY(300);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setX(-5);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setY(-5);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setX(605);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setX(300);
      shot.setY(-5);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setY(605 + shot.getSpeed());
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setX(-5);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
      
      shot.setMoving(true);
      shot.setX(605);
      shot.nextFrame();
      assertEquals(shot.getMoving(), false);
  }
  
}
