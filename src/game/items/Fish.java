package game.items;

import edu.monash.fit2099.engine.Item;

/**
 * A class representing fish.
 */
public class Fish extends Item implements Edible {

    /**
     * Constructor for fish.
     */
    public Fish() {
        super("fish", '*', false);
    }

    @Override
    public int getRestorationValue() {
        return 5;
    }

    @Override
    public boolean isDifficultToEat() {
        return false;
    }

    @Override
    public boolean isFilling() {
        return false;
    }

    @Override
    public boolean isPartiallyEdible() {
        return false;
    }

    @Override
    public void decreaseRestorationValue(int amount) {

    }
}
