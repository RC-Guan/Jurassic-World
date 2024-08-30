package game.items;


import edu.monash.fit2099.engine.Location;

/**
 * A class representing fruit.
 */
public class Fruit extends PortableItem implements Edible, Purchasable {
    private int rotCounter;

    /**
     * Constructor of Fruit.
     */
    public Fruit() {
        super("fruit", 'o');
    }

    /**
     * Returns the price of fruit if purchasing from vending machine.
     * @return the price of fruit.
     */
    public int getPrice() {
        return 30;
    }

    /**
     * Fruit experience time on ground. It can rot over time.
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        rot(currentLocation);
    }

    /**
     * Fruit rots away.
     * @param currentLocation current location of fruit.
     */
    private void rot(Location currentLocation) {
        rotCounter += 1;
        if (this.rotCounter >= 15) {
            currentLocation.removeItem(this);
        }
    }

    @Override
    public boolean isDifficultToEat() {
        return true;
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

    @Override
    public int getRestorationValue() {
        return 10;
    }
}
