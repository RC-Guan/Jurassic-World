package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.capabilities.*;
import game.grounds.Bush;
import game.grounds.Dirt;
import game.grounds.Tree;
import game.items.Fruit;
import game.items.VegMealKit;

import java.util.Random;

/**
 * A herbivorous dinosaur.
 */
public class Brachiosaur extends Dinosaur {

    /**
     * Constructor.
     * All Brachiosaur are represented by a 'b' and have max 160 hit points.
     */
    public Brachiosaur(Boolean isBaby) {
        super("Brachiosaur", 'b', 160, isBaby);
        if (isBaby) {
            hitPoints = 10;
        } else {
            hitPoints = 100;
        }
        sipSize = 80;
        setMaxWaterLevel(200);
        setUnconsciousTurnsLimit(15);
        setGestationTurnsLimit(30);
        setHatchTime(30);
        setGrowTime(50);
        setRotTime(40);
        setHungerLevel(140);
        setHatchPoint(1000);
        setPrice(500);
        this.addCapability(Digestion.POOR_DIGESTION);

        // Adding food candidate
        items.add(new Fruit());
        items.add(new VegMealKit());
        grounds.add(new Tree());
    }

    /**
     * Second type of constructor that sets the sex of Brachiosaur.
     * All Brachiosaur are represented by a 'b' and have max 160 hit points.
     */
    public Brachiosaur(boolean isFemale, Boolean isBaby) {
        super("Brachiosaur", 'b', 160, isFemale, isBaby);
        if (isBaby) {
            hitPoints = 10;
        } else {
            hitPoints = 100;
        }
        sipSize = 80;
        setMaxWaterLevel(200);
        setUnconsciousTurnsLimit(15);
        setGestationTurnsLimit(30);
        setHatchTime(30);
        setGrowTime(50);
        setRotTime(40);
        setHungerLevel(140);
        setHatchPoint(1000);
        setPrice(500);
        this.addCapability(Digestion.POOR_DIGESTION);

        // Adding food candidate
        items.add(new Fruit());
        items.add(new VegMealKit());
        grounds.add(new Tree());
    }

    /**
     * Brachiosaur would kill bush half of the time.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn.
     *                   Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the next action for Brachiosaur.
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        squishGround(map.locationOf(this));
        return super.playTurn(actions, lastAction, map, display);
    }

    /**
     * Returns the restoration value of a Brachiosaur corpse
     *
     * @return the restoration value of a Brachiosaur corpse.
     */
    @Override
    public int getCorpseRestorationValue() {
        return 100;
    }

    /**
     * Checks if ground is bush, then turns bush to dirt 50% of the time
     *
     * @param here the current location.
     */
    private void squishGround(Location here) {
        if (here.getGround().isSquishable()) {
            Random r = new Random();
            if (r.nextBoolean()) {
                here.setGround(new Dirt());
            }
        }
    }

    /**
     * Returns a Brachiosaur baby.
     *
     * @return a Brachiosaur baby.
     */
    @Override
    public Dinosaur createBaby() {
        return new Brachiosaur(true);
    }

    @Override
    public boolean hasAttackCooling() {
        return false;
    }

    @Override
    public void triggerAttackCooling() {
    }
}
