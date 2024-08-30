package game.actions;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;
import game.items.Corpse;
import game.dinosaurs.Dinosaur;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

    /**
     * The Actor that is to be attacked
     */
    protected Actor target;
    /**
     * Random number generator
     */
    protected Random rand = new Random();

    /**
     * Constructor.
     *
     * @param target the Actor to attack
     */
    public AttackAction(Actor target) {
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

        Weapon weapon = actor.getWeapon();

        int damage = weapon.damage();
        String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

        target.hurt(damage);
        target.triggerAttackCooling();
        actor.heal(damage);

        if (!target.isConscious()) {
            Item corpse = new Corpse((Dinosaur) target);
            map.locationOf(target).addItem(corpse);

            Actions dropActions = new Actions();
            for (Item item : target.getInventory())
                dropActions.add(item.getDropAction());
            for (Action drop : dropActions)
                drop.execute(target, map);
            map.removeActor(target);

            result += System.lineSeparator() + target + " is killed.";
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
        return actor + " attacks " + target;
    }
}
