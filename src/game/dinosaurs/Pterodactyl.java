package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.actions.EatFromLocationAction;
import game.behaviours.Behaviour;
import game.behaviours.HungerBehaviour;
import game.behaviours.WanderBehaviour;
import game.capabilities.Flying;
import game.capabilities.SmallBeak;
import game.grounds.Lake;
import game.grounds.Tree;
import game.items.*;

import java.util.ArrayList;

/**
 * A carnivorous dinosaur.
 */
public class Pterodactyl extends Dinosaur {

    protected int flyingCounter; //keeps track of how long pterodactyl has been flying

    /**
     * Constructor for Pterodactyl.
     *
     * @param isBaby true if is baby, false otherwise.
     */
    public Pterodactyl(Boolean isBaby) {
        super("Pterodactyl", 'p', 100, isBaby);
        if (isBaby) {
            hitPoints = 10;
        } else {
            hitPoints = 50;
        }
        this.addCapability(SmallBeak.SMALL_BEAK);
        this.addCapability(Flying.FLYING);

        setUnconsciousTurnsLimit(20);
        setGestationTurnsLimit(10);
        setHatchTime(20);
        setGrowTime(30);
        setRotTime(20);
        setHungerLevel(50);
        setHatchPoint(100);
        setPrice(300);

        // Adding food candidate
        items.add(new Fish());
        items.add(new CarnMealKit());
        items.add(new Corpse());
        items.add(new Egg());
        grounds.add(new Lake());

        matingGrounds.add(new Tree());
        eggLayingGrounds.add(new Tree());
    }

    /**
     * Second constructor for Pterodactyl.
     *
     * @param isFemale true if is female, false otherwise.
     * @param isBaby   true if is baby, false otherwise.
     */
    public Pterodactyl(Boolean isFemale, Boolean isBaby) {
        super("Pterodactyl", 'p', 100, isFemale, isBaby);
        if (isBaby) {
            hitPoints = 10;
        } else {
            hitPoints = 50;
        }
        this.addCapability(SmallBeak.SMALL_BEAK);
        this.addCapability(Flying.FLYING);

        setUnconsciousTurnsLimit(20);
        setGestationTurnsLimit(10);
        setHatchTime(20);
        setGrowTime(30);
        setRotTime(20);
        setHungerLevel(50);
        setHatchPoint(100);
        setPrice(300);

        // Adding food candidate
        items.add(new Corpse());
        items.add(new Fish());
        items.add(new CarnMealKit());
        items.add(new Egg());
        grounds.add(new Lake());

        matingGrounds.add(new Tree());
        eggLayingGrounds.add(new Tree());
    }

    /**
     * Most basic constructor for Pterodactyl.
     */
    public Pterodactyl() {
        super("Pterodactyl", 'p', 100, false);
    }

    /**
     * Figures out the Pterodactyl's next action.
     * Resets the flying fuel every 30 turns until it lands on a tree.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn.
     *                   Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return Pterodactyl's next action.
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location here = map.locationOf(this);
        flyingCounter++;
        if (here.getGround().getClass() == Tree.class) {
            flyingCounter = 0;
            if (!this.hasCapability(Flying.FLYING)) {
                this.addCapability(Flying.FLYING);
            }
        } else {
            if (this.flyingCounter >= 30 && this.hasCapability(Flying.FLYING)) {
                this.removeCapability(Flying.FLYING);
            }
        }
        Action returnAction =  super.playTurn(actions, lastAction, map, display);
        if (returnAction instanceof EatFromLocationAction){
            if (((EatFromLocationAction) returnAction).getEdibleItem() instanceof Corpse){
                for (Exit exit :here.getExits()){
                    if (exit.getDestination().containsAnActor()){
                        if (exit.getDestination().getActor() instanceof Dinosaur){
                            ArrayList<Edible> noCorpseItems = (ArrayList<Edible>) items.remove(0);
                            Behaviour modifiedBehaviour = new HungerBehaviour(noCorpseItems, grounds, attackables, edibleActors);
                            returnAction = modifiedBehaviour.getAction(this, map);
                        }
                    }
                }
            }
        }
        if (returnAction == null){
            returnAction = new WanderBehaviour().getAction(this, map);
        }
        return returnAction;
    }

    @Override
    public boolean hasAttackCooling() {
        return false;
    }

    @Override
    public void triggerAttackCooling() {
    }

    /**
     * The other Dinosaurs that eat Pterodactyl's corpse gain 30 food points.
     *
     * @return 30 hit points.
     */
    @Override
    public int getCorpseRestorationValue() {
        return 30;
    }

    /**
     * Returns an Pterodactyl baby.
     *
     * @return an Pterodactyl baby.
     */
    @Override
    public Dinosaur createBaby() {
        return new Pterodactyl(true);
    }
}
