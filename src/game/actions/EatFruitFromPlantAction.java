package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.items.Edible;
import game.items.Fruit;
import game.capabilities.Digestion;

import java.util.ArrayList;

/**
 * Special Action for eat fruit from plant.
 */
public class EatFruitFromPlantAction extends Action {
    private final ArrayList<Fruit> fruit;

    /**
     * Constructor
     *
     * @param fruit a collection of fruit.
     */
    public EatFruitFromPlantAction(ArrayList<Fruit> fruit) {
        this.fruit = fruit;
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
        int numFruit = fruit.size();
        if (fruit.isEmpty()) {
            return actor + " tried to eat from the plant, but couldn't find any fruit.";
        }else {
            int points = calculatePoints(fruit.get(0), actor);
            points = points*numFruit;
            actor.heal(points);
            fruit.clear();
            return actor + " eats fruits from plant.";
        }
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
        return actor + " eats fruit from plant";
    }
}
