package game.behaviours;

import edu.monash.fit2099.engine.*;
import game.actions.DrinkWaterAction;
import game.capabilities.Thirsty;
import game.grounds.Lake;

import java.util.ArrayList;

/**
 * A class that moves the dinosaur towards water source.
 */
public class ThirstyBehaviour implements Behaviour {
    private ArrayList<Location> lakeLocations = new ArrayList<>();

    /**
     * Constructor for ThirstyBehaviour
     */
    public ThirstyBehaviour() {
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!actor.hasCapability(Thirsty.THIRSTY))
            return null;
        else {
            //for dinosaurs flying over
            Location here = map.locationOf(actor);
            if (here.getGround().getClass() == Lake.class && ((Lake) here.getGround()).getSip() > 0) {
                return new DrinkWaterAction(here);
            }
            //for dinosaurs adjacent to the lake
            for (Exit exit : here.getExits()) {
                Location destination = exit.getDestination();
                if (destination.getGround().getClass() == Lake.class && (((Lake) destination.getGround()).getSip() > 0)) {
                    return new DrinkWaterAction(destination);
                }
            }

            // find lake locations
            NumberRange widths = map.getXRange();
            NumberRange heights = map.getYRange();

            for (int x : widths) {
                for (int y : heights) {
                    Location potentialLocation = map.at(x, y);
                    Ground potentialGround = potentialLocation.getGround();
                    if (potentialGround.getClass() == Lake.class)
                        lakeLocations.add(potentialLocation);
                }
            }

            //move towards closest lake
            if (!lakeLocations.isEmpty()) {
                int currentDistance = Integer.MAX_VALUE;
                Location closestLake = lakeLocations.get(0);

                for (Location location : lakeLocations) {
                    int newDistance = distance(here, location);
                    if (newDistance < currentDistance) {
                        currentDistance = newDistance;
                        closestLake = location;
                    }
                }

                for (Exit exit : here.getExits()) {
                    Location destination = exit.getDestination();
                    if (destination.canActorEnter(actor)) {
                        int newDistance = distance(destination, closestLake);
                        if (newDistance < currentDistance) {
                            return new MoveActorAction(destination, exit.getName());
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
