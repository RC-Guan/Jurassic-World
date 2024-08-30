package game.actions;

import edu.monash.fit2099.engine.*;
import game.dinosaurs.Dinosaur;

/**
 * Special action for consuming a whole dinosaur.
 */
public class EatDinoWholeAction extends Action {
    /**
     * The Actor that is to be attacked
     */
    protected Actor target;

    /**
     * Constructor.
     *
     * @param target the Actor to attack
     */
    public EatDinoWholeAction(Actor target) {
        this.target = target;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.heal(((Dinosaur) actor).getMaxHitPoints());
        map.removeActor(target);
        return actor + " eats " + target + " whole";
    }

    /**
     * Returns a descriptive string.
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " eats " + target + " whole";
    }
}
