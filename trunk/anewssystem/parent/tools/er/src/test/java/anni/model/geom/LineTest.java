package anni.model.geom;

import junit.framework.*;


public class LineTest extends TestCase {
    public void testOne() {
        assertTrue(true);
    }

    public void testCross() {
        Line line1 = new Line(550.0, 290.0, 760.0, 290.0);
        Line line2 = new Line(145.0, 470.0, 655.0, 350.0);
        Point p = line1.getCrossPoint(line2);
        assertNull(p);
    }

    public void testCross2() {
        Line line1 = new Line(550.0, 410.0, 760.0, 410.0);
        Line line2 = new Line(145.0, 470.0, 655.0, 350.0);
        Point p = line1.getCrossPoint(line2);
        assertNull(p);
    }

    public void testCross3() {
        Line line1 = new Line(550.0, 290.0, 550.0, 410.0);
        Line line2 = new Line(145.0, 470.0, 655.0, 350.0);
        Point p = line1.getCrossPoint(line2);
        assertNotNull(p);
        assertEquals(550, p.getX(), 0.01);
        assertEquals(374.7, p.getY(), 0.01);
    }
}
