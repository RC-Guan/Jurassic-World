package game.items;

/**
 * A class representing Carnivorous Meal kit.
 */
public class CarnMealKit extends PortableItem implements Edible, Purchasable {

    /**
     * Constructor
     */
    public CarnMealKit() {
        super("Carnivore Meal Kit", 'C');
    }

    public int getPrice() {
        return 500;
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
