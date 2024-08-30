
package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.EcoPoint;
import game.items.Edible;
import game.capabilities.SmallBeak;
import game.dinosaurs.Dinosaur;
import game.items.CarnMealKit;
import game.items.Fruit;

/**
 * Special Action for feeding dinosaurs.
 */
public class FeedAction extends Action {
    private final Dinosaur subject;
    private final Edible edibleItem;

    /**
     * Constructor
     *
     * @param subject    the dinosaur
     * @param edibleItem an edible item
     */
    public FeedAction(Dinosaur subject, Edible edibleItem) {
        this.subject = subject;
        this.edibleItem = edibleItem;
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
        int points = calculatePoints(edibleItem, subject);
        subject.heal(points);
        if (edibleItem.getRestorationValue() <= 0) {
            actor.removeItemFromInventory((Item) edibleItem);
        }
        if (edibleItem instanceof Fruit || edibleItem instanceof CarnMealKit) {
            EcoPoint.increaseEcoPoints(10);
        }
        return "Player feeds " + subject + " " + edibleItem + " for " + points + " points.";
    }

    /**
     * Calculates the restoration value from food.
     *
     * @param edibleItem food.
     * @param actor      the dinosaur.
     * @return restoration value.
     */
    public int calculatePoints(Edible edibleItem, Dinosaur actor) {
        int points;
        if (edibleItem.isFilling()) {
            points = (actor.getMaxHitPoints());
        } else if (edibleItem.isPartiallyEdible() && actor.hasCapability(SmallBeak.SMALL_BEAK)) {
            points = 10;
            edibleItem.decreaseRestorationValue(points);
        } else {
            points = edibleItem.getRestorationValue();
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
        return "Feed " + subject + " " + edibleItem;
    }
}