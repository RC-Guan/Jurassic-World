package game.grounds;

import edu.monash.fit2099.engine.*;
import game.EcoPoint;
import game.capabilities.*;
import game.items.Fruit;
import game.actions.EatFruitFromPlantAction;
import game.Player;
import game.actions.SearchForFruitAction;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that represents tree.
 */
public class Tree extends Ground implements FruitGrower, EdibleGround {
    private int age = 0;
    private ArrayList<Fruit> fruit;
    private Random r = new Random();

    /**
     * Constructor for tree.
     */
    public Tree() {
        super('+');
        fruit = new ArrayList<>();
    }

    /**
     * Tree experiences the joy of time, it may grow fruit.
     *
     * @param here the current location
     */
    @Override
    public void tick(Location here) {
        super.tick(here);
        increaseAge();
        if (growFruit())
            dropFruit(here);
    }

    /**
     * Tree grows into big trees.
     */
    private void increaseAge() {
        age++;
        if (age == 10)
            displayChar = 't';
        if (age == 20)
            displayChar = 'T';
    }

    /**
     * Tree has a chance of growing fruit.
     *
     * @return true if tree grows fruit, false otherwise.
     */
    public boolean growFruit() {
        boolean didGrow = false;
        if (r.nextBoolean()) {
            Fruit f = new Fruit();
            fruit.add(f);
            didGrow = true;
            EcoPoint.increaseEcoPoints(1);
        }
        return didGrow;
    }

    /**
     * Tree has a chance of dropping fruit.
     *
     * @param location the current location.
     */
    private void dropFruit(Location location) {
        for (Fruit f : fruit) {
            if (r.nextDouble() <= 0.05) {
                location.addItem(f);
            }
        }
    }

    /**
     * A collection of allowable actions for actors around the location.
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return A collection of allowable actions.
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = super.allowableActions(actor, location, direction);
        if ((actor.getClass() == Player.class && location.map().locationOf(actor) == location)) {
            actions.add(new SearchForFruitAction(this.fruit));
        }
        return actions;
    }

    @Override
    public void experienceRain(double rainfall) {
    }

    @Override
    public boolean isSquishable() {
        return false;
    }

    @Override
    public Action getEatAction(Actor actor) {
        return new EatFruitFromPlantAction(fruit);
    }
}

