package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.items.CarnMealKit;
import game.items.Corpse;
import game.items.Egg;

/**
 * A carnivorous dinosaur.
 */
public class Allosaur extends Dinosaur {

    /**
     * Constructor.
     * All Allosaurs are represented by a 'a' and have max 100 hit points.
     */
    public Allosaur() {
        super("Allosaur", 'a', 100, true);
        hitPoints = 20;

        setGestationTurnsLimit(20);
        setHatchTime(50);
        setGrowTime(50);
        setRotTime(20);
        setHungerLevel(50);
        setHatchPoint(1000);
        setPrice(1000);

        // Adding food candidate
        items.add(new Corpse());
        items.add(new CarnMealKit());
        items.add(new Egg());
        edibleActors.add(new Pterodactyl());
        attackables.add(new Stegosaur());
    }

    /**
     * Returns an Allosaur baby.
     *
     * @return an Allosaur baby.
     */
    @Override
    public Dinosaur createBaby() {
        return new Allosaur();
    }

    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(20, "bites");
    }

    @Override
    public boolean hasAttackCooling() {
        return false;
    }

    @Override
    public void triggerAttackCooling() {
    }
}

