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
import se.citerus.crazysnake.Direction;
import static org.junit.Assert.*;
import se.citerus.crazysnake.GameState;
import se.citerus.crazysnake.Movement;
import se.citerus.crazysnake.Position;

/**
 *
 * @author bobo
 */
public class SimpleBrainTest {
    private final Position base = position(15,15);
    private final Position left = position(14,15);
    private final Position right = position(16,15);
    private final Position up = position(15,14);
    final Position down = position(15,16);
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

        PlaceKitten instance = new PlaceKitten();
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
        List<Position> candidates = Arrays.asList(position(10,10), base,
                position(10,0),
                position(0,10),
                position(10,10));
        PlaceKitten instance = new PlaceKitten();
        Position result = instance.getPositionWithMinDistanceFrom(position(0,0), candidates);
        assertEquals(result,position(10,0));
        result = instance.getPositionWithMinDistanceFrom(position(14,13), candidates);
        assertEquals(base,result);
    }
    
//    @Test
//    public void testGetOptimalFruits() {
//        List<Position> candidates = Arrays.asList(position(10, 15), position(15, 10) );
//        PlaceKitten instance = new PlaceKitten();
//        Position pos = instance.getOptimalFruit(base, Direction.NORTH, candidates);
//        assertEquals(pos, position(15,10));
//        
//    }
    
    @Test
    public void testIsInMyDirection() {
        PlaceKitten instance = new PlaceKitten();
        assertTrue(instance.isInMyDirection(base, Direction.NORTH, position(15, 10)));
        assertFalse(instance.isInMyDirection(base, Direction.WEST, position(15, 10)));
        assertTrue(instance.isInMyDirection(base, Direction.WEST, position(10, 15)));
        assertFalse(instance.isInMyDirection(base, Direction.NORTH, position(10, 15)));
    }
    
    
    
//    @Test
//    public void testGetSortedFruits() {
//        List<Position> candidates = Arrays.asList(position(10,10), base,
//                position(10,0),
//                position(0,10),
//                position(10,10));
//        PlaceKitten instance = new PlaceKitten();
//        List<Position> result = instance.getSortedFruits(position(0,0), candidates);
//        assertEquals(result.get(0), position(10,0));
//        assertEquals(result.get(1), position(0,10));
//        assertEquals(result.get(2), position(10,10));
//        assertEquals(result.get(3), position(10,10));
//        assertEquals(result.get(4), base);
//    }
    
    @Test
    public void testSimplePosition() {
        PlaceKitten instance = new PlaceKitten();
        Movement expectedMove = instance.convertPositionsToDirections(Direction.WEST, base, up);        
        assertEquals(Movement.RIGHT, expectedMove);
        expectedMove = instance.convertPositionsToDirections(Direction.WEST, base, position(15,16));
        assertEquals(Movement.LEFT, expectedMove);
        expectedMove = instance.convertPositionsToDirections(Direction.WEST, base, position(14,15));
        assertEquals(Movement.FORWARD, expectedMove);
    }
    
    
    
    @Test
    public void testGetUpdatedPosition() {
        PlaceKitten instance = new PlaceKitten();
        
        Position updatedPosition = instance.getUpdatedPosition(base,Direction.EAST, Movement.FORWARD);
        assertEquals(right,updatedPosition);
         updatedPosition = instance.getUpdatedPosition(base,Direction.EAST, Movement.LEFT);
        assertEquals(up,updatedPosition);
        updatedPosition = instance.getUpdatedPosition(base,Direction.EAST, Movement.RIGHT);        
        assertEquals(down,updatedPosition);
        updatedPosition = instance.getUpdatedPosition(base,Direction.NORTH, Movement.FORWARD);
        assertEquals("up",up,updatedPosition);
        updatedPosition = instance.getUpdatedPosition(base,Direction.NORTH, Movement.LEFT);
        assertEquals(left,updatedPosition);
        updatedPosition = instance.getUpdatedPosition(base,Direction.NORTH, Movement.RIGHT);
        assertEquals(right,updatedPosition);
        updatedPosition = instance.getUpdatedPosition(base,Direction.WEST, Movement.FORWARD);
        assertEquals(left,updatedPosition);
        updatedPosition = instance.getUpdatedPosition(base,Direction.WEST, Movement.LEFT);
        assertEquals(down,updatedPosition);
        updatedPosition = instance.getUpdatedPosition(base,Direction.WEST, Movement.RIGHT);
        assertEquals(up,updatedPosition);
        updatedPosition = instance.getUpdatedPosition(base,Direction.SOUTH, Movement.FORWARD);
        assertEquals(down,updatedPosition);
        updatedPosition = instance.getUpdatedPosition(base,Direction.SOUTH, Movement.LEFT);
        assertEquals(right,updatedPosition);
        updatedPosition = instance.getUpdatedPosition(base,Direction.SOUTH, Movement.RIGHT);
        assertEquals(left,updatedPosition);
        
    }
    
    
    
    private Position position(int x, int y) {
        return new Position(x, y);
    }
    
    
    
}
