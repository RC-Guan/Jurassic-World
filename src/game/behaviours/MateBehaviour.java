package game.behaviours;

import edu.monash.fit2099.engine.*;
import game.actions.BreedAction;
import game.capabilities.Baby;
import game.capabilities.Pregnant;

import static game.capabilities.Hungry.HUNGRY;

import java.util.ArrayList;


/**
 * A class that moves the dinosaur towards mating partner.
 */
public class MateBehaviour implements Behaviour {
    private Location there;
    private ArrayList<Ground> matingGrounds;

    /**
     * Constructor for MateBehaviour.
     *
     * @param matingGrounds a list of grounds the actors may mate on.
     */
    public MateBehaviour(ArrayList<Ground> matingGrounds) {
        this.matingGrounds = matingGrounds;
    }

    /**
     * Returns a MoveAction to move towards a dinosaur of opposite sex, if possible.
     * If no movement is possible, returns null.
     *
     * @param actor the Actor enacting the behaviour
     * @param map   the map that actor is currently on
     * @return an Action, or null if no MoveAction is possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        int currentDistance = Integer.MAX_VALUE;

        // Obtain a collection of mate choice.
        ArrayList<Location> mateChoice = new ArrayList<>();

        NumberRange widths = map.getXRange();
        NumberRange heights = map.getYRange();

        // If two actors are of the same class and opposite sex, then add to mate choice
        for (int x : widths) {
            for (int y : heights) {
                if (map.at(x, y).containsAnActor() && (map.at(x, y).getActor().getClass() == actor.getClass())
                        && !(actor.isFemale() == map.at(x, y).getActor().isFemale())
                        && !(actor.hasCapability(Baby.BABY)) && !(map.at(x, y).getActor().hasCapability(Baby.BABY))
                        && !actor.hasCapability(Pregnant.PREGNANT)) {
                    mateChoice.add(map.at(x, y));
                }
            }

            // Find the nearest choice from a pool of choices
            if (!mateChoice.isEmpty()) {
                for (Location location : mateChoice) {
                    int newDistance = distance(here, location);
                    if (newDistance < currentDistance) {
                        currentDistance = newDistance;
                        there = location;
                    }
                }

                // Move to the nearest mate choice
                for (Exit exit : here.getExits()) {
                    Location destination = exit.getDestination();
                    if (destination.canActorEnter(actor)) {
                        int newDistance = distance(destination, there);
                        if (newDistance < currentDistance) {
                            return new MoveActorAction(destination, exit.getName());
                        }
                    }
                }
            }
        }

        // Returns breed action if a well-fed actor is standing adjacent to the mate choice on a suitable ground type.
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (!actor.hasCapability(HUNGRY) && destination.containsAnActor()
                    && destination.getActor().getClass() == actor.getClass() && !actor.hasCapability(Pregnant.PREGNANT)
                    && !destination.getActor().hasCapability(Pregnant.PREGNANT) && !(actor.hasCapability(Baby.BABY))
                    && !(destination.getActor().hasCapability(Baby.BABY))) {

                // If they are on the suitable ground
                if (matingGrounds.isEmpty()) {
                    return new BreedAction(destination.getActor());
                } else {
                    for (Ground g : matingGrounds) {
                        if (destination.getGround().getClass() == g.getClass() &&
                                map.locationOf(actor).getGround().getClass() == g.getClass()) {
                            return new BreedAction(destination.getActor());
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