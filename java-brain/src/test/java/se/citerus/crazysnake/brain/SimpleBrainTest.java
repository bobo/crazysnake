/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.citerus.crazysnake.brain;

import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.citerus.crazysnake.GameState;
import se.citerus.crazysnake.Movement;
import se.citerus.crazysnake.Position;

/**
 *
 * @author bobo
 */
public class SimpleBrainTest {
    
    public SimpleBrainTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNextMove method, of class SimpleBrain.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getNextMove");

        SimpleBrain instance = new SimpleBrain();
        Position p1 = new Position(0, 0);
        int calculateDistance = instance.calculateDistance(p1, position(0,0));
        assertEquals(calculateDistance, 0);
        calculateDistance = instance.calculateDistance(p1, position(0,1));
        assertEquals(calculateDistance, 1);        
        calculateDistance = instance.calculateDistance(p1, position(10,15));
        assertEquals(calculateDistance, 25);
        calculateDistance = instance.calculateDistance(position(10,15), position(10,15));
        assertEquals(calculateDistance, 0);
        calculateDistance = instance.calculateDistance(position(10,15), position(0,0));
        assertEquals(calculateDistance, 25);
    }
    
    @Test
    public void testGetMin() {
        List<Position> candidates = Arrays.asList(position(10,10),
                position(15,15),
                position(10,0),
                position(0,10),
                position(10,10));
        SimpleBrain instance = new SimpleBrain();
        Position result = instance.getPositionWithMinDistanceFrom(position(0,0), candidates);
        assertEquals(result,position(10,0));
        result = instance.getPositionWithMinDistanceFrom(position(14,13), candidates);
        assertEquals(position(15,15),result);
    }
    
    private Position position(int x, int y) {
        return new Position(x, y);
    }
}
