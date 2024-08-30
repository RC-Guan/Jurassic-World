package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.EcoPoint;
import game.items.Fruit;

import java.util.ArrayList;
import java.util.Random;

/**
 * Special Action for the player to search for fruit.
 */
public class SearchForFruitAction extends Action {
    private final ArrayList<Fruit> fruit;
    private final Random r;

    /**
     * Constructor of searching for fruit.
     * @param fruit  An arraylist of fruit.
     */
    public SearchForFruitAction(ArrayList<Fruit> fruit) {
        this.r = new Random();
        this.fruit = fruit;
    }

    /**
     * Executes the action of searching for fruit.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return the output string of the action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (fruit.isEmpty())
            return "You searched for fruit, but you can’t find any ripe ones.";

        else if (r.nextDouble() <= 0.4){
            int size = fruit.size();
            for (Fruit i: fruit){
                actor.addItemToInventory(i);
            }
            fruit.clear();
            EcoPoint.increaseEcoPoints(10);
            return "Success! " + size + " Fruit added to inventory";
        }
        else return "You searched for fruit, but you can’t find any ripe ones.";

    }

    /**
     * The menu description of the search for fruit action.
     *
     * @param actor The actor performing the action.
     * @return the menu description of the action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " searches for fruit";
    }
}
