package game.actions;

import edu.monash.fit2099.engine.*;
import game.dinosaurs.Dinosaur;
import game.grounds.Lake;

/**
 * Special action for drinking water.
 */
public class DrinkWaterAction extends Action {
    private Location location;

    /**
     * Constructor for DrinkWaterAction.
     *
     * @param location the current location.
     */
    public DrinkWaterAction(Location location) {
        this.location = location;
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
        Lake lake = (Lake) location.getGround();
        if (lake.getSip() > 0) {
            lake.decreaseSip();
            ((Dinosaur) actor).addWaterLevel();
        }
        return actor + " drinks water.";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + "drinks water.";
    }

}
