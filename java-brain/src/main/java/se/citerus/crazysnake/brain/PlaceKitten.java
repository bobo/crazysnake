/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.citerus.crazysnake.brain;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import se.citerus.crazysnake.BaseBrain;
import se.citerus.crazysnake.Direction;
import se.citerus.crazysnake.GameState;
import se.citerus.crazysnake.Movement;
import se.citerus.crazysnake.Position;
import se.citerus.crazysnake.Snake;

/**
 *
 * @author bobo
 */
public class PlaceKitten extends BaseBrain {

    @Override
    public Movement getNextMove(GameState state) {
        Snake myself = state.getSnake(getName());

        Position closesFruit = getClosesFruit(myself.getHeadPosition(), state.getFruitPositions());
        if (closesFruit != null) {
            Movement candidate = convertPositionsToDirections(myself.getDirection(), myself.getHeadPosition(), closesFruit);
            if (!isCollision(candidate, state, myself)) {
                return candidate;
            }
        }

        return pickOptimalMovement(state, myself);

    }
    Movement pickOptimalMovement(GameState state, Snake myself) {
        int max=0;
        Movement result=Movement.FORWARD;
        for (Movement movement : Movement.values()) {
            int current = getNumberOfFreePositions(state, myself, movement);
            if(current>max)  {
                max=current;
                result=movement;
            }
        }
        return result;

    }
    
    
    int getNumberOfFreePositions(GameState state, Snake myself, Movement m) {
        
        Position current = getUpdatedPosition(myself.getHeadPosition(), myself.getDirection(), m);
        int count=0;
        while(state.getSquare(current).isUnoccupied() && count<=10) {
          current = getUpdatedPosition(current, myself.getDirection(), Movement.FORWARD);
          count++;
        }
        return count;
    }
    
    boolean hasClearPath(Position snake, Position fruit, GameState state) {
        List<Position> positionsBetweenPoints = getPositionsBetweenPoints(snake, fruit);
        for (Position position : positionsBetweenPoints) {
            if(!state.getSquare(position).isUnoccupied())
                return false;
        }
        return true;
    }
    
   List<Position> getPositionsBetweenPoints(Position snake, Position fruit) {
       Position current = snake;
//       Movement move = convertPositionsToDirections(Direction.EAST, snake, fruit);
//      getUpdatedPosition(current, Direction.EAST, Movement.LEFT)
       return Collections.EMPTY_LIST;
   }
    
    

    Position getUpdatedPosition(Position p, Direction d, Movement step) {
        if (d.equals(Direction.EAST) && step.equals(Movement.FORWARD)) {
            return moveRight(p);
        } else if (d.equals(Direction.NORTH) && step.equals(Movement.RIGHT)) {
            return moveRight(p);
        } else if (d.equals(Direction.SOUTH) && step.equals(Movement.LEFT)) {
            return moveRight(p);
        } else if (d.equals(Direction.EAST) && step.equals(Movement.LEFT)) {
            return moveUp(p);
        } else if (d.equals(Direction.WEST) && step.equals(Movement.RIGHT)) {
            return moveUp(p);
        } else if (d.equals(Direction.NORTH) && step.equals(Movement.FORWARD)) {
            return moveUp(p);
        } else if (d.equals(Direction.EAST) && step.equals(Movement.RIGHT)) {
            return moveDown(p);
        } else if (d.equals(Direction.WEST) && step.equals(Movement.LEFT)) {
            return moveDown(p);
        } else if (d.equals(Direction.SOUTH) && step.equals(Movement.FORWARD)) {
            return moveDown(p);
        } else {
            return moveLeft(p);
        }
    }

    private Position moveLeft(Position p) {
        return new Position(p.getX() - 1, p.getY());
    }

    private Position moveDown(Position p) {
        return new Position(p.getX(), p.getY() + 1);
    }

    private Position moveUp(Position p) {
        return new Position(p.getX(), p.getY() - 1);
    }

    private Position moveRight(Position p) {
        return new Position(p.getX() + 1, p.getY());
    }

    boolean isCollision(Movement m, GameState state, Snake myself) {

        System.out.println(m + ":" + myself.getHeadPosition() + ":" + getUpdatedPosition(myself.getHeadPosition(), myself.getDirection(), m) + "::" + state.getSquare(getUpdatedPosition(myself.getHeadPosition(), myself.getDirection(), m)).isUnoccupied());
        return !state.getSquare(getUpdatedPosition(myself.getHeadPosition(), myself.getDirection(), m)).isUnoccupied();

    }

    Movement convertPositionsToDirections(Direction d, Position snake, Position fruit) {

        Position diff = new Position(fruit.getX() - snake.getX(), fruit.getY() - snake.getY());
        if (diff.getX() > 0) {
            return Direction.newMovement(d, Direction.EAST);

        } else if (diff.getX() == 0) {
            if (diff.getY() > 0) {
                return Direction.newMovement(d, Direction.SOUTH);
            } else {
                return Direction.newMovement(d, Direction.NORTH);
            }
        } else {
            return Direction.newMovement(d, Direction.WEST);
        }

    }

    int calculateDistance(Position p1, Position p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    List<Position> getSortedFruits(final Position base, final Direction d, List<Position> fruits) {
        Collections.sort(fruits, new Comparator<Position>() {

            @Override
            public int compare(Position t, Position t1) {
                int tvalue = 0, t1value = 0;
                if (isInMyDirection(base, d, t)) tvalue = 2;
                if (isInMyDirection(base, d, t1)) t1value = 2;
                System.out.println(t+":"+tvalue+"distance: "+calculateDistance(base, t)+" _ "+t1+":"+t1value+" distance: "+calculateDistance(base, t1));
                return (calculateDistance(base, t)-tvalue) > (calculateDistance(base, t1)-t1value) ? 1 : -1;
            }
        });
        return fruits;

    }

    Position getOptimalFruit(Position base, Direction d, List<Position> fruits) {
        try {
            return getSortedFruits(base, d, fruits).get(0);
        } catch (Exception e) {
            return null;
        }   

    }
    
    boolean isInMyDirection(Position my, Direction d, Position fruit) {
        Position diff = new Position(fruit.getX() - my.getX(), fruit.getY() - my.getY());
        if ((diff.getX() > 0 && d.equals(Direction.EAST)) ||
            (diff.getY() > 0 && d.equals(Direction.SOUTH)) ||
            (diff.getX() < 0 && d.equals(Direction.WEST)) ||
            (diff.getY() < 0 && d.equals(Direction.NORTH))) {
            return true;
        }
        return false;
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

}
