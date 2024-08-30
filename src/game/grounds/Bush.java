package game.grounds;


import edu.monash.fit2099.engine.*;
import game.items.Fruit;
import game.actions.EatFruitFromPlantAction;
import game.Player;
import game.actions.SearchForFruitAction;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that represents bush.
 */
public class Bush extends Ground implements FruitGrower, EdibleGround {
    private final ArrayList<Fruit> fruit;
    private Random r = new Random();

    /**
     * Constructor.
     */
    public Bush() {
        super('v');
        fruit = new ArrayList<>();
    }

    /**
     * Bush experiences the joy of time, it may grow fruit.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        growFruit();
    }

    /**
     * Bush has a chance of growing fruit.
     */
    public boolean growFruit() {
        boolean didGrow = false;
        if (r.nextDouble() <= 0.1) {
            Fruit f = new Fruit();
            fruit.add(f);
            didGrow = true;
        }
        return didGrow;
    }

    /**
     * A collection of allowable actions for actors at the location.
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return A collection of allowable actions.
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {

        Actions actions = super.allowableActions(actor, location, direction);
        if (location.map().locationOf(actor) == location && actor.getClass() == Player.class) {
            actions.add(new SearchForFruitAction(this.fruit));
        }
        return actions;
    }

    @Override
    public void experienceRain(double rainfall) {

    }

    @Override
    public boolean isSquishable() {
        return true;
    }

    @Override
    public Action getEatAction(Actor actor) {
        return new EatFruitFromPlantAction(this.fruit);
    }
}
