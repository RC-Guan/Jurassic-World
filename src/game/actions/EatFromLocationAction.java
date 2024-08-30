package game.actions;

import edu.monash.fit2099.engine.*;
import game.capabilities.Digestion;
import game.items.Edible;
import game.capabilities.SmallBeak;
import game.dinosaurs.Dinosaur;

/**
 * Special Action for eat from location.
 */
public class EatFromLocationAction extends Action {
    private Edible edibleItem;

    /**
     * Constructor.
     *
     * @param edibleItem the item to be eaten.
     */
    public EatFromLocationAction(Edible edibleItem) {
        this.edibleItem = edibleItem;
    }

    /**
     * Calculates the restoration value from food.
     *
     * @param edibleItem food.
     * @param dino       the dinosaur.
     * @return restoration value.
     */
    public int calculatePoints(Edible edibleItem, Dinosaur dino) {
        int points = edibleItem.getRestorationValue();
        if (edibleItem.isDifficultToEat() && dino.hasCapability(Digestion.POOR_DIGESTION)) {
            points = points / 2;
        }
        if (edibleItem.isFilling()) {
            points = dino.getMaxHitPoints();
        }
        if (edibleItem.isPartiallyEdible() && dino.hasCapability(SmallBeak.SMALL_BEAK)) {
            points = 10;
            System.out.println("points are 10 bc small beak");
        }

        return points;
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
        Location here = map.locationOf(actor);
        int restorationValue = calculatePoints(edibleItem, (Dinosaur) actor);
        actor.heal(restorationValue);
        if (!edibleItem.isPartiallyEdible()) {
            here.removeItem((Item) edibleItem);
        } else {
            edibleItem.decreaseRestorationValue(restorationValue);
            if (edibleItem.getRestorationValue() <= 0) {
                here.removeItem((Item) edibleItem);
            }
        }
        return actor + " eats " + edibleItem;
    }

    /**
     * getter for the action's edible item
     * @return the edible item
     */
    public Edible getEdibleItem() {
        return edibleItem;
    }

    /***
     * Returns a descriptive string.
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " eats " + edibleItem;
    }
}
