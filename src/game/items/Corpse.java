package game.items;

import edu.monash.fit2099.engine.Location;
import game.dinosaurs.Dinosaur;

/**
 * A class representing corpse.
 */
public class Corpse extends PortableItem implements Edible {
    private Dinosaur dinosaur;
    private int rotCounter;
    private int restorationValue;

    /**
     * Constructor for Corpse.
     * @param dinosaur dinosaur that the corpse is of
     */
    public Corpse(Dinosaur dinosaur) {
        super("Corpse", 'X');
        this.dinosaur = dinosaur;
        this.restorationValue = dinosaur.getCorpseRestorationValue();
    }

    /**
     * Constructor without variables
     */
    public Corpse() {
        super("Corpse", 'X');
    }

    /**
     * Corpse experiences time and rots.
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        rotCounter++;
        if (rotCounter == dinosaur.getRotTime())
            currentLocation.removeItem(this);
    }

    @Override
    public int getRestorationValue() {
        return this.restorationValue;
    }

    @Override
    public void decreaseRestorationValue(int amount) {
        this.restorationValue -= amount;
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
        return true;
    }
}
