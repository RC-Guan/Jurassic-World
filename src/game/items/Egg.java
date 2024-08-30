package game.items;

import edu.monash.fit2099.engine.*;
import game.EcoPoint;
import game.actions.LayEggAction;
import game.dinosaurs.Dinosaur;
import game.capabilities.Baby;
import game.capabilities.Hungry;

/**
 * A class representing egg.
 */
public class Egg extends PortableItem implements Edible, Purchasable {
    private final Dinosaur babyDinosaur;
    private int hatchCounter = 0;
    private int gestationCounter = 0;

    /**
     * Constructor for Egg.
     *
     * @param name the name of the egg.
     * @param dino the egg contains a baby dino.
     */
    public Egg(String name, Dinosaur dino) {
        super(name, '0');
        this.babyDinosaur = dino;
        this.babyDinosaur.addCapability(Baby.BABY);
        this.babyDinosaur.addCapability(Hungry.HUNGRY);
    }

    /**
     * Basic constructor for Egg.
     */
    public Egg() {
        super("Egg", '0');
        babyDinosaur = null;
    }

    @Override
    public int getPrice() {
        return babyDinosaur.getPrice();
    }

    @Override
    public int getRestorationValue() {
        return 10;
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

    /**
     * Inform an egg on the ground of the passage of time.
     * If it is the turn to hatch and no actors are on the same ground, a baby dinosaur will hatch.
     *
     * @param currentLocation The location of the ground on which the egg lies.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        hatchCounter++;

        if (hatchCounter >= babyDinosaur.getHatchTime() && !currentLocation.containsAnActor()) {
            currentLocation.addActor(babyDinosaur);
            System.out.println("----------------------------\nA baby " + babyDinosaur + " has hatched.");
            EcoPoint.increaseEcoPoints(babyDinosaur.getHatchPoint());
            currentLocation.removeItem(this);
        }
    }

    /**
     * Inform an egg carried by the pregnant dinosaur of the passage of time.
     * If it is the turn for the female dino to lay an egg, it will lay it on the same ground as the female dino.
     *
     * @param currentLocation The location of the ground on which the egg lies.
     * @param actor           The actor carrying the item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation);
        gestationCounter++;
        if ((actor instanceof Dinosaur) && (gestationCounter >= babyDinosaur.getGestationTurnsLimit())) {
            Action layEgg = new LayEggAction();
            String result = layEgg.execute(actor, currentLocation.map());
            System.out.println(result);
        }
    }
}
