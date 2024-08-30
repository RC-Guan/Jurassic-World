package game.actions;

import edu.monash.fit2099.engine.*;
import game.dinosaurs.Dinosaur;

import java.util.ArrayList;

import static game.capabilities.Pregnant.PREGNANT;

/**
 * Special Action for laying egg.
 */
public class LayEggAction extends Action {

    /**
     * Perform the Action of laying egg if the pregnant dinosaur is on the suitable ground.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";
        if (actor.hasCapability(PREGNANT)) {

            // Egg ready to be dropped
            Actions dropActions = new Actions();
            for (Item item : actor.getInventory()) {
                dropActions.add(item.getDropAction());
            }

            // Check if the dino is on the suitable ground, if yes lay egg.
            for (Action drop : dropActions) {
                if (actor instanceof Dinosaur && !(((Dinosaur) actor).getEggLayingGrounds().isEmpty())) {
                    ArrayList<Ground> grounds = ((Dinosaur) actor).getEggLayingGrounds();
                    for (Ground g : grounds) {
                        if (map.locationOf(actor).getGround().getClass() == g.getClass()) {
                            drop.execute(actor, map);
                            actor.removeCapability(PREGNANT);
                            result = actor + " has laid an egg on tree.";
                        }
                    }
                } else {
                    drop.execute(actor, map);
                    actor.removeCapability(PREGNANT);
                    result = actor + " has laid an egg on ground.";
                }
            }
        }
        return result;
    }

    /**
     * Doesn't return anything.
     *
     * @param actor The actor performing the action.
     * @return null
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
