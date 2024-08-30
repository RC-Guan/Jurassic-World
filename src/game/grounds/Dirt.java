package game.grounds;

import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {
    private Random r = new Random();

    /**
     * Constructor.
     */
    public Dirt() {
        super('.');
    }


    /**
     * Dirt experiences the joy of time, it may grow into a bush.
     *
     * @param here the current location.
     */
    @Override
    public void tick(Location here) {
        super.tick(here);
        growBush(here);
    }

    /**
     * Calculates the chance for growing bush.
     *
     * @param here the current location.
     */
    private void growBush(Location here) {
        int adjacentTrees = 0;
        int adjacentBushes = 0;

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            Ground ground = destination.getGround();
            if (ground.getClass() == Tree.class) {
                adjacentTrees += 1;
            } else if (ground.getClass() == Bush.class) {
                adjacentBushes += 1;
            }
        }
        if (adjacentTrees == 0 && adjacentBushes < 2) {
            if (r.nextDouble() <= 0.01)
                here.setGround(new Bush());
        } else if (adjacentTrees == 0 && (r.nextDouble() <= 0.1)) {
            here.setGround(new Bush());
        }
    }

    @Override
    public void experienceRain(double rainfall) {

    }

    @Override
    public boolean isSquishable() {
        return false;
    }
}
