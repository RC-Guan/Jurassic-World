package game.grounds;

import edu.monash.fit2099.engine.*;
import game.actions.PurchaseItemAction;
import game.capabilities.Flying;
import game.dinosaurs.Allosaur;
import game.dinosaurs.Brachiosaur;
import game.dinosaurs.Pterodactyl;
import game.dinosaurs.Stegosaur;
import game.items.*;

import java.util.LinkedHashMap;

/**
 * A class that represents vending machine.
 */
public class VendingMachine extends Ground {
    private int age;
    private final LinkedHashMap<Purchasable, Integer> vendingMachine;

    /**
     * Constructor
     */
    public VendingMachine() {
        super('H');
        setAge(0);
        vendingMachine = new LinkedHashMap<>();

        Fruit fruit = new Fruit();
        VegMealKit vegMealKit = new VegMealKit();
        CarnMealKit carnMealKit = new CarnMealKit();
        Egg stegoEgg = new Egg("Stegosaur Egg", new Stegosaur(true));
        Egg pteroEgg = new Egg("Pterodactyl Egg", new Pterodactyl(true));
        Egg brachioEgg = new Egg("Brachiosaur Egg", new Brachiosaur(true));
        Egg alloEgg = new Egg("Allosaur Egg", new Allosaur());

        LaserGun laserGun = new LaserGun();

        vendingMachine.put(fruit, 10);
        vendingMachine.put(vegMealKit, 10);
        vendingMachine.put(carnMealKit, 10);
        vendingMachine.put(stegoEgg, 10);
        vendingMachine.put(pteroEgg, 10);
        vendingMachine.put(brachioEgg, 10);
        vendingMachine.put(alloEgg, 10);
        vendingMachine.put(laserGun, 1);
    }

    /**
     * Sets the age of the vending machine.
     *
     * @param age age of the vending machine.
     */
    private void setAge(int age) {
        this.age = age;
    }

    /**
     * The vending machine refills at the start of the game and every 100 turns after.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        age++;
        if (age == 100) {
            this.refill();
            this.setAge(0);
        }
    }

    /**
     * The vending machine refills every 100 turns after.
     */
    private void refill() {
        for (Purchasable item : vendingMachine.keySet()) {
            this.vendingMachine.replace(item, 10);
        }
    }

    /**
     * Getter for vending machine.
     *
     * @return vending machine.
     */
    public LinkedHashMap<Purchasable, Integer> getVendingMachine() {
        return vendingMachine;
    }

    /**
     * Makes walking actors unable to enter vending machine.
     *
     * @param actor the Actor to check
     * @return if the actor can enter the location.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(Flying.FLYING);
    }

    /**
     * Returns the value of the item.
     *
     * @param item the item
     * @return the value of the item.
     */
    public int getItem(Purchasable item) {
        return vendingMachine.getOrDefault(item, null);
    }

    /**
     * Player has a collection of allowable actions to purchase items from the vending machine.
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return A collection of allowable actions.
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = super.allowableActions(actor, location, direction);
        for (Purchasable item : vendingMachine.keySet()) {
            if (this.getItem(item) > 0) {
                actions.add(new PurchaseItemAction(this, item));
            }
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
}
