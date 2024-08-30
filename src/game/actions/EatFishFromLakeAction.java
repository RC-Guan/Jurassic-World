package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.items.Edible;
import game.items.Fish;
import game.capabilities.Digestion;

import java.util.ArrayList;
import java.util.Random;

/**
 * Special Action for eat fish from lake.
 */
public class EatFishFromLakeAction extends Action {
    private final ArrayList<Fish> fish;

    /**
     * Constructor
     *
     * @param fish  a collection of Fish.
     */
    public EatFishFromLakeAction(ArrayList<Fish> fish) {
        this.fish = fish;
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
        if (!fish.isEmpty()) {
            int numFish = fish.size();
            int points = calculatePoints(fish.get(0), actor);
            Random r = new Random();
            if (r.nextDouble() <= 1.0 / 3.0) {
                return actor + " tried to eat from the lake, but couldn't find any fish";
            } else if (r.nextDouble() <= 2.0 / 3.0) {
                actor.heal(points);
                fish.remove(numFish - 1);
                return actor + " eats 1 fish from the lake";
            } else if (r.nextDouble() <= 1.0 && numFish >= 2) {
                actor.heal(points * 2);
                fish.remove(numFish - 1);
                fish.remove(numFish - 2);
                return actor + " eats 2 fish from the lake";
            }
        }
        return actor + " tried to eat from the lake, but couldn't find any fish";
    }

    /**
     * Calculates the restoration value from food.
     *
     * @param edibleItem food.
     * @param actor      the dinosaur.
     * @return restoration value.
     */
    public int calculatePoints(Edible edibleItem, Actor actor) {
        int points = edibleItem.getRestorationValue();
        if (edibleItem.isDifficultToEat() && actor.hasCapability(Digestion.POOR_DIGESTION)) {
            points = points / 2;
        }
        return points;
    }

    /**
     * Returns a descriptive string.
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " eats fish from lake";
    }
}
