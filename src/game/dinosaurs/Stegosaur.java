package game.dinosaurs;

import game.grounds.Bush;
import game.items.Fruit;
import game.items.VegMealKit;

/**
 * A herbivorous dinosaur.
 */
public class Stegosaur extends Dinosaur {

    /**
     * Constructor.
     * All Stegosaurs are represented by a 's' and have max 100 hit points.
     *
     * @param isBaby true if the stegosaur is baby, false otherwise.
     */
    public Stegosaur(Boolean isBaby) {
        super("Stegosaur", 's', 100, isBaby);
        if (isBaby) {
            hitPoints = 10;
        } else {
            hitPoints = 50;
        }

        setUnconsciousTurnsLimit(20);
        setGestationTurnsLimit(10);
        setHatchTime(20);
        setGrowTime(30);
        setRotTime(20);
        setHungerLevel(50);
        setHatchPoint(100);
        setPrice(200);

        // Adding food candidate
        items.add(new Fruit());
        items.add(new VegMealKit());
        grounds.add(new Bush());
    }

    /**
     * Constructor.
     * All Stegosaurs are represented by a 's' and have max 100 hit points.
     *
     * @param isFemale true if the stegosaur is female, false otherwise.
     * @param isBaby   true if the stegosaur is baby, false otherwise.
     */
    public Stegosaur(Boolean isFemale, Boolean isBaby) {
        super("Stegosaur", 's', 100, isFemale, isBaby);
        if (isBaby) {
            hitPoints = 10;
        } else {
            hitPoints = 50;
        }

        setUnconsciousTurnsLimit(20);
        setGestationTurnsLimit(10);
        setHatchTime(20);
        setGrowTime(30);
        setRotTime(20);
        setHungerLevel(50);
        setHatchPoint(100);
        setPrice(200);

        // Adding food candidate
        items.add(new Fruit());
        items.add(new VegMealKit());
        grounds.add(new Bush());
    }

    /**
     * Constructor for Stegosaur.
     */
    public Stegosaur() {
        super("Stegosaur", 's', 100, false);
    }

    /**
     * Triggers a cooling period for the stegosaur to not be attacked.
     */
    @Override
    public void triggerAttackCooling() {
        this.attacked = true;
    }

    /**
     * Determines whether the stegosaur has a cooling period to not be attacked.
     *
     * @return whether the stegosaur has a cooling period to not be attacked.
     */
    @Override
    public boolean hasAttackCooling() {
        return this.attacked;
    }

    /**
     * Returns a Stegosaur baby.
     *
     * @return a Stegosaur baby.
     */
    @Override
    public Dinosaur createBaby() {
        return new Stegosaur(true);
    }
}
