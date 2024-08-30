package game.grounds;

import edu.monash.fit2099.engine.*;
import game.actions.EatFishFromLakeAction;
import game.capabilities.Flying;
import game.items.Fish;

import java.util.ArrayList;
import java.util.Random;


/**
 * A class that represents lake.
 */
public class Lake extends Ground implements EdibleGround {
    private int sip;
    private final ArrayList<Fish> fish;
    private Random r = new Random();

    /**
     * Constructor.
     * Each lake starts with a capacity of 25 sips and 5 fish.
     */
    public Lake() {
        super('~');
        this.sip = 25;

        fish = new ArrayList<>();
        Fish f = new Fish();
        for (int i = 0; i < 5; i++) {
            fish.add(f);
        }
    }

    /**
     * Lake experiences the joy of time, new fish may born and the sky might rain to add water.
     *
     * @param here the current location.
     */
    @Override
    public void tick(Location here) {
        super.tick(here);
        growFish();
    }

    /**
     * getter for sip
     *
     * @return value of sip
     */
    public int getSip() {
        return sip;
    }

    /**
     * Each turn, there is a probability of 60% for a new fish to be born.
     * Each lake can also hold up to maximum 25 fish.
     */
    private void growFish() {
        if (r.nextDouble() <= 0.6 && (fish.size() < 25)) {
            Fish f = new Fish();
            fish.add(f);
        }
    }

    /**
     * Increases sip when it rains.
     * Maximum is 25 sips.
     *
     * @param rainfall rainfall
     */
    @Override
    public void experienceRain(double rainfall) {
        if (sip + 20 * rainfall < 25) {
            sip += 20 * rainfall;
        } else {
            this.sip = 25;
        }
    }

    @Override
    public boolean isSquishable() {
        return false;
    }

    /**
     * Decreases the amount of sips in the lake by 1
     */
    public void decreaseSip() {
        this.sip -= 1;
    }

    /**
     * Returns the eat from ground action for lake (eat fish).
     *
     * @param actor the actor at the location or adjacent to the location.
     * @return the action of eating fish.
     */
    @Override
    public Action getEatAction(Actor actor) {
        return new EatFishFromLakeAction(this.fish);
    }

    /**
     * Lets flying actors enter, but not walking ones.
     *
     * @param actor the Actor to check
     * @return if the actor can enter the location.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(Flying.FLYING);
    }
}
