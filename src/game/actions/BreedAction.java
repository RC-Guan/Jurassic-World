package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.dinosaurs.Dinosaur;
import game.items.Egg;

import static game.capabilities.Baby.BABY;
import static game.capabilities.Hungry.HUNGRY;
import static game.capabilities.Pregnant.PREGNANT;

/**
 * Special Action for breeding.
 */
public class BreedAction extends Action {

    /**
     * The target actor for mating
     */
    protected Actor target;

    /**
     * Constructor.
     *
     * @param target the Actor for mating
     */
    public BreedAction(Actor target) {
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
        String result = actor + " attempts to mate " + target;

        // If meets all conditions, a new egg is created and the mother dino carries the egg.
        if (actor.isFemale() != target.isFemale() && !actor.hasCapability(HUNGRY)
                && !actor.hasCapability(BABY) && !target.hasCapability(BABY)
                && !actor.hasCapability(PREGNANT) && !target.hasCapability(PREGNANT)) {

            if (!(actor instanceof Dinosaur)) {
                result += System.lineSeparator() + "Attempt failed.";
                return result;
            }

            Dinosaur dino = (Dinosaur) actor;
            Egg egg = new Egg(actor.getClass() + " Egg", dino.createBaby());

            if (!actor.isFemale()) {
                target.addCapability(PREGNANT);
                target.addItemToInventory(egg);
                result += System.lineSeparator() + target + " is pregnant.";
            } else {
                actor.addCapability(PREGNANT);
                actor.addItemToInventory(egg);
                result += System.lineSeparator() + actor + " is pregnant.";
            }
        } else {
            result += System.lineSeparator() + "Attempt failed.";
        }
        return result;
    }

    /**
     * Returns a descriptive string.
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " mates " + target;
    }
}

