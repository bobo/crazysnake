package se.citerus.crazysnake.brain;

import static se.citerus.crazysnake.Movement.*;
import se.citerus.crazysnake.Movement;

/**
 * Left-Rigth-Left etc...
 */
public class WetterstrandBrain extends PoliticalBrain {

    private int count;

    @Override
    public Movement getDodgeProblemsAheadDirection() {
        
        return (count++ % 2) == 0 ? RIGHT : LEFT;
    }

}
