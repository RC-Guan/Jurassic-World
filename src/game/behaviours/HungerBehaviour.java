package game.behaviours;


import edu.monash.fit2099.engine.*;
import game.capabilities.Hungry;
import game.capabilities.SmallBeak;
import game.items.Edible;
import game.actions.AttackAction;
import game.actions.EatDinoWholeAction;
import game.actions.EatFromLocationAction;
import game.grounds.EdibleGround;

import java.util.ArrayList;

/**
 * A class that moves the dinosaur towards food source.
 */
public class HungerBehaviour implements Behaviour {
    private final ArrayList<Edible> items;
    private final ArrayList<EdibleGround> grounds;
    private final ArrayList<Actor> attackable;
    private final ArrayList<Actor> edibleActors;

    /**
     * Constructor for HungerBehaviour.
     *
     * @param items        A list of food items a dinosaur can have.
     * @param grounds      A list of grounds a dinosaur can for food.
     * @param attackable   A list of preys a dinosaur can attack.
     * @param edibleActors A list of preys a dinosaur can attack.
     */
    public HungerBehaviour(ArrayList<Edible> items, ArrayList<EdibleGround> grounds,
                           ArrayList<Actor> attackable, ArrayList<Actor> edibleActors) {
        this.items = items;
        this.grounds = grounds;
        this.attackable = attackable;
        this.edibleActors = edibleActors;
    }

    /**
     * Returns a MoveAction to move towards a location of food source, if possible.
     * If no movement is possible, returns null.
     *
     * @param actor the Actor enacting the behaviour
     * @param map   the map that actor is currently on
     * @return an Action, or null if no MoveAction is possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!actor.hasCapability(Hungry.HUNGRY)) {
            return null;
        }

        //searches current location for food
        Location here = map.locationOf(actor);
        Ground hereGround = here.getGround();
        for (EdibleGround ground : grounds) {
            if (ground.getClass() == hereGround.getClass()) {
                return ((EdibleGround) hereGround).getEatAction(actor);
            }
        }

        for (Item potentialItem : here.getItems()) {
            for (Edible edibleItem : items)
                if (edibleItem.getClass() == potentialItem.getClass())
                    return new EatFromLocationAction((Edible) potentialItem);
        }

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                Actor potentialActor = destination.getActor();
                for (Actor targetActor : edibleActors) {
                    if (targetActor.getClass() == potentialActor.getClass() && !potentialActor.hasCapability(SmallBeak.SMALL_BEAK)) {
                        return new EatDinoWholeAction(potentialActor);
                    }
                }
            }
        }

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                Actor potentialActor = destination.getActor();
                for (Actor targetActor : attackable) {
                    if (targetActor.getClass() == potentialActor.getClass() && !targetActor.hasAttackCooling()) {
                        return new AttackAction(potentialActor);
                    }
                }
            }
        }

        //otherwise, looks for closest food and moves towards it
        ArrayList<Location> foodLocations = new ArrayList<>();

        NumberRange widths = map.getXRange();
        NumberRange heights = map.getYRange();

        for (int x : widths) {
            for (int y : heights) {
                Location potentialLocation = map.at(x, y);
                Ground potentialGround = potentialLocation.getGround();
                for (EdibleGround ground : grounds) {
                    if (ground.getClass() == potentialGround.getClass()) {
                        foodLocations.add(potentialLocation);
                    }
                }

                if (potentialLocation.containsAnActor()) {
                    Actor potentialActor = potentialLocation.getActor();
                    for (Actor targetActor : attackable) {
                        if (targetActor.getClass() == potentialActor.getClass())
                            foodLocations.add(potentialLocation);
                    }
                } else {
                    for (Item potentialItem : map.at(x, y).getItems()) {
                        for (Edible edibleItem : items)
                            if (edibleItem.getClass() == potentialItem.getClass())
                                foodLocations.add(potentialLocation);
                    }
                }
            }
        }

        if (!foodLocations.isEmpty()) {
            // Calculates the nearest location with food
            int currentDistance = Integer.MAX_VALUE;
            Location closestFood = foodLocations.get(0);

            for (Location location : foodLocations) {
                int newDistance = distance(here, location);
                if (newDistance < currentDistance) {
                    currentDistance = newDistance;
                    closestFood = location;
                }
            }

            for (Exit exit : here.getExits()) {
                Location destination = exit.getDestination();
                if (destination.canActorEnter(actor)) {
                    int newDistance = distance(destination, closestFood);
                    if (newDistance < currentDistance) {
                        return new MoveActorAction(destination, exit.getName());
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