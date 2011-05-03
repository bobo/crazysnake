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
            } else {
                if (!isCollision(Movement.LEFT, state, myself)) {
                    return Movement.LEFT;
                }
                else if (!isCollision(Movement.RIGHT, state, myself)) {
                    return Movement.RIGHT;
                }
                else {
                    return Movement.FORWARD;
                }
            }
        } else {
            return Movement.FORWARD;
        }
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
System.out.println(m+":"+myself.getHeadPosition()+":"+getUpdatedPosition(myself.getHeadPosition(), myself.getDirection(), m)+"::"+state.getSquare(getUpdatedPosition(myself.getHeadPosition(), myself.getDirection(), m)).isLethalCollision());
        return state.getSquare(getUpdatedPosition(myself.getHeadPosition(), myself.getDirection(), m)).isLethalCollision();

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

    List<Position> getSortedFruits(final Position base, List<Position> fruits) {
        Collections.sort(fruits, new Comparator<Position>() {

            @Override
            public int compare(Position t, Position t1) {
                return calculateDistance(base, t) > calculateDistance(base, t1) ? 1 : -1;
            }
        });
        return fruits;

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

    boolean hasClearPath(Position base, Position goal, GameState state) {


        return false;
    }
}
