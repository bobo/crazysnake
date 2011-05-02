/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.citerus.crazysnake.brain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import se.citerus.crazysnake.BaseBrain;
import se.citerus.crazysnake.GameState;
import se.citerus.crazysnake.Movement;
import se.citerus.crazysnake.Position;
import se.citerus.crazysnake.Snake;

/**
 *
 * @author bobo
 */
public class SimpleBrain extends BaseBrain{

    @Override
    public Movement getNextMove(GameState state) {
        Snake myself = state.getSnake(getName());
   
        List<Position> fruitPositions = state.getFruitPositions();
        for (Position position : fruitPositions) {
            
        }
        
        
        return Movement.FORWARD;
    }
   
    int calculateDistance(Position p1, Position p2) {
        return Math.abs(p1.getX()-p2.getX())+Math.abs(p1.getY()-p2.getY());
    }
    
    Position getClosesFruit(Position base, List<Position> fruits) {
        return getPositionWithMinDistanceFrom(base, fruits);
    }
    
    Position getPositionWithMinDistanceFrom(Position base, List<Position> candidates) {
        int currentMin = 10000;
        Position closest = null;
        for (Position position : candidates) {
            int distance = calculateDistance(base, position);
            if (distance < currentMin) {
                currentMin = distance;
                closest = position;
            }
        }
        return closest;
    }
    
    Set<Position> getSnakePositions(Collection<Snake> snakes) {
        Set<Position> positions = new HashSet<Position>();
        for (Snake snake : snakes) {
            positions.add(snake.getHeadPosition());
        }
        return positions;
    }
    
    boolean hasClearPath(Position base, Position goal,GameState state){
        
        
            return false;
    }
    
}
