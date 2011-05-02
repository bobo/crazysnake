package se.citerus.crazysnake.brain;

import se.citerus.crazysnake.Movement;

import static se.citerus.crazysnake.Movement.LEFT;

/**
 * Inherently left-drifting.
 */
public class OhlyBrain extends PoliticalBrain {

    @Override
    public Movement getDodgeProblemsAheadDirection() {
        return LEFT;
    }

}
