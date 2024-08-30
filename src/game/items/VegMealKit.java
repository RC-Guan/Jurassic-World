package game.items;

/**
 * A class representing Vegetarian Meal kit.
 */
public class VegMealKit extends PortableItem implements Edible, Purchasable {

    /**
     * Constructor
     */
    public VegMealKit() {
        super("Vegetarian Meal Kit", 'V');
    }

    public int getPrice() {
        return 100;
    }


    @Override
    public int getRestorationValue() {
        return 200;
    }

    @Override
    public boolean isDifficultToEat() {
        return false;
    }

    @Override
    public boolean isFilling() {
        return true;
    }

    @Override
    public boolean isPartiallyEdible() {
        return false;
    }

    @Override
    public void decreaseRestorationValue(int amount) {
    }
}
