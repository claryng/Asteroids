/**
 * 
 */
package animation;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        // testing ship at any speed and any angle
        while (shipSpeed <= 20) {

            // only need to test and angle below 2 pi because
            // it will be the same for angle more than 2 pi
            while (shipAngle < 2) {

                shot.setMoving(true);
                shot.nextFrame();

                // check if the shot x and y is on the right track
                x = x + shot.getSpeed() * Math.sin(shot.getAngle());
                y = y - shot.getSpeed() * Math.cos(shot.getAngle());
                assertEquals(shot.getX(), x);
                assertEquals(shot.getY(), y);

                // check if shot is still on the right track after 2 frames
                shot.nextFrame();
                x = x + shot.getSpeed() * Math.sin(shot.getAngle());
                y = y - shot.getSpeed() * Math.cos(shot.getAngle());
                assertEquals(shot.getX(), x);
                assertEquals(shot.getY(), y);

                // reset the x and y of shot
                x = 300;
                y = 300;

                // try another speed and angle
                shipSpeed += 3;
                shipAngle += 0.2;

                // reset the shot
                shot = new Shot(demo, shipSpeed, shipAngle, x, y);
            }
        }
    }

    @Test
    /**
     * Testing shot that has gone out of bound at different situations
     */
    void testOutOfBoundShot() {

        // x out of bound, y normal
        shot.setX(605);
        shot.setY(300);
        shot.nextFrame();
        assertFalse(shot.getMoving());

        shot.setMoving(true);
        shot.setX(-5);
        shot.nextFrame();
        assertFalse(shot.getMoving());

        // x and y out of bound, y out of bound at 0
        shot.setMoving(true);
        shot.setY(-5);
        shot.nextFrame();
        assertFalse(shot.getMoving());

        shot.setMoving(true);
        shot.setX(605);
        shot.nextFrame();
        assertFalse(shot.getMoving());

        // x normal, y out of bound
        shot.setMoving(true);
        shot.setX(300);
        shot.setY(-5);
        shot.nextFrame();
        assertFalse(shot.getMoving());

        shot.setMoving(true);
        shot.setY(605);
        shot.nextFrame();
        assertFalse(shot.getMoving());

        shot.setMoving(true);
        shot.setX(-5);
        shot.nextFrame();
        assertFalse(shot.getMoving());

        shot.setMoving(true);
        shot.setX(605);
        shot.nextFrame();
        assertFalse(shot.getMoving());
    }

}
