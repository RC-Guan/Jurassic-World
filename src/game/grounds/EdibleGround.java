package game.grounds;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;

/**
 * Interface for Ground types that has edible items on.
 */
public interface EdibleGround {
    /**
     * Returns an eat action for the actor.
     *
     * @param actor the actor who is at the location of the ground
     * @return an action the actor performs.
     */
    Action getEatAction(Actor actor);
}
