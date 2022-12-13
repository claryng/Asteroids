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
    private Double vectorTarget = new Double(x, y - shipSpeed);
    
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
      while (shipSpeed < 18) {
          shot.nextFrame();
          System.out.println(shot.getSpeed());

          vectorTarget.setLocation(shot.getX() + (shipSpeed) * Math.sin(shipAngle),
                  shot.getY() - (shipSpeed) * Math.cos(shipAngle));

          x += (vectorTarget.getX() - x);
          y += (vectorTarget.getY() - y);
          
          assertEquals(shot.getX(), (shot.getY() + shot.getSpeed())*Math.cos(shot.getAngle()));
          shipSpeed+=3;
          shot = new Shot(demo, shipSpeed, shipAngle, x, y);
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
