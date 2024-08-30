package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Player;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.behaviours.*;
import game.capabilities.*;
import game.grounds.EdibleGround;
import game.items.Corpse;
import game.items.Edible;

import java.util.ArrayList;
import java.util.Random;

/**
 * Base class for a Dinosaur.
 */
public abstract class Dinosaur extends Actor implements BabyBearer {
    /**
     * The age of the dinosaur if was born from an egg.
     */
    protected int age;
    /**
     * True if the dinosaur is female, false if male.
     */
    protected boolean isFemale;
    /**
     * The current water level for the dinosaur.
     */
    protected int waterLevel = 60;
    /**
     * The maximum water level for the dinosaur.
     */
    protected int maxWaterLevel = 100;
    /**
     * The amount of that the water level of a dino increases for each sip
     */
    protected int sipSize = 30;
    /**
     * Below this water level a dinosaur would be thirsty. Default is set to 40.
     */
    protected int thirstLevel = 40;
    /**
     * Below this hunger level a dinosaur would be hungry.
     */
    protected int hungerLevel;
    /**
     * The number of turns a dinosaur can be unconscious before it dies.
     */
    protected int unconsciousTurnsLimit;
    /**
     * The number of turns a dinosaur has been unconscious.
     */
    protected int unconsciousTurns;
    /**
     * The number of turns before a dinosaur egg will be laid.
     */
    protected int gestationTurnsLimit;
    /**
     * The number of turns before a dinosaur egg will hatch.
     */
    protected int hatchTime;
    /**
     * The number of turns before a baby dinosaur will take to grow into an adult.
     */
    protected int growTime;
    /**
     * The amount of time a corpse of a dinosaur will stay in the game
     */
    protected int rotTime;
    /**
     * The amount of ecoPoints it generates from an egg hatching.
     */
    protected int hatchPoint;
    /**
     * The price of the dinosaur egg if purchased from vending machine.
     */
    protected int price;
    /**
     * A list of behaviours a dinosaur has.
     */
    protected ArrayList<Behaviour> behaviours = new ArrayList<>();
    /**
     * A list of food items a dinosaur can have.
     */
    protected ArrayList<Edible> items = new ArrayList<>();
    /**
     * A list of grounds a dinosaur can for food.
     */
    protected ArrayList<EdibleGround> grounds = new ArrayList<>();
    /**
     * A list of preys a dinosaur can attack.
     */
    protected ArrayList<Actor> attackables = new ArrayList<>();
    /**
     * A list of preys a dinosaur can attack.
     */
    protected ArrayList<Actor> edibleActors = new ArrayList<>();
    /**
     * A list of potential ground for mating
     */
    protected ArrayList<Ground> matingGrounds = new ArrayList<>();
    /**
     * A list of potential ground for laying eggs.
     */
    protected ArrayList<Ground> eggLayingGrounds = new ArrayList<>();
    /**
     * If the dinosaur was attacked in a defined period of time.
     */
    protected boolean attacked = false;
    /**
     * A counter for attack cooling mechanism.
     */
    protected int coolingCounter = 0;
    /**
     * Used for probability.
     */
    protected Random rand = new Random();

    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Dinosaur in the display
     * @param hitPoints   the Dinosaur's starting hit points
     * @param isBaby      whether the dinosaur is a baby
     */
    public Dinosaur(String name, char displayChar, int hitPoints, boolean isBaby) {
        super(name, displayChar, hitPoints);
        if (isBaby) {
            addCapability(Baby.BABY);
        }
        this.isFemale = !rand.nextBoolean();

        behaviours.add(new ThirstyBehaviour());
        behaviours.add(new HungerBehaviour(items, grounds, attackables, edibleActors));
        behaviours.add(new MateBehaviour(matingGrounds));
        behaviours.add(new WanderBehaviour());
    }

    /**
     * constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Dinosaur in the display
     * @param hitPoints   the Dinosaur's starting hit points
     * @param isFemale    the Dinosaur's sex
     * @param isBaby      whether the dinosaur is a baby
     */
    public Dinosaur(String name, char displayChar, int hitPoints, boolean isFemale, boolean isBaby) {
        super(name, displayChar, hitPoints);
        this.isFemale = isFemale;
        if (isBaby) {
            addCapability(Baby.BABY);
        }

        behaviours.add(new ThirstyBehaviour());
        behaviours.add(new HungerBehaviour(items, grounds, attackables, edibleActors));
        behaviours.add(new MateBehaviour(matingGrounds));
        behaviours.add(new WanderBehaviour());
    }

    /**
     * Figure out what to do next in different scenarios.
     * Each turn, the dinosaur will increase its age, decrease hit points and reduce its water level.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn.
     *                   Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the next action
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        age++;
        if (this.hitPoints > 0) {
            hitPoints--;
        }
        if (this.waterLevel > 0) {
            waterLevel--;
        }
        if (this.attacked) {
            coolingCounter++;
            if (coolingCounter == 20) {
                this.attacked = false;
                coolingCounter = 0;
            }
        }

        if (this.hasCapability(Baby.BABY) && (age >= growTime)) {
            this.removeCapability(Baby.BABY);
        }

        System.out.println(this + " at "
                + map.locationOf(this).x() + "," + map.locationOf(this).y() + " has " +
                +this.getHitPoints() + "/" + getMaxHitPoints() + " hit points");
        hunger(map.locationOf(this));
        thirst(map.locationOf(this));

        if (unconscious(map.locationOf(this), map))
            return new DoNothingAction();

        for (Behaviour behaviour : behaviours) {
            Action action = behaviour.getAction(this, map);
            if (action != null) {
                return action;
            }
        }
        return new DoNothingAction();
    }

    /**
     * Sets the maximum water level for dinosaur.
     *
     * @param maxWaterLevel the maximum capacity of water level.
     */
    protected void setMaxWaterLevel(int maxWaterLevel) {
        this.maxWaterLevel = maxWaterLevel;
    }

    /**
     * Sets the gestation turns for dinosaur.
     *
     * @param turns number of turns for gestation.
     */
    protected void setGestationTurnsLimit(int turns) {
        gestationTurnsLimit = turns;
    }

    /**
     * Sets the hatch time for dinosaur.
     *
     * @param turns number of turns before eggs will hatch.
     */
    protected void setHatchTime(int turns) {
        hatchTime = turns;
    }

    /**
     * Sets the growing time for dinosaur.
     *
     * @param turns number of turns before dinosaurs will turn into an adult.
     */
    protected void setGrowTime(int turns) {
        growTime = turns;
    }

    /**
     * Sets the unconscious turns for a dinosaur before it dies.
     *
     * @param turns number of turns the unconscious turns for a dinosaur before it dies.
     */
    protected void setUnconsciousTurnsLimit(int turns) {
        unconsciousTurnsLimit = turns;
    }

    /**
     * Sets the rot time for a dead dinosaur.
     *
     * @param turns number of turns before dead dinosaurs will rot.
     */
    protected void setRotTime(int turns) {
        rotTime = turns;
    }

    /**
     * Sets the hunger level for the dinosaur.
     * If a dinosaur is below certain number of hit points, it is hungry.
     *
     * @param hungerLevel number of hit points
     */
    protected void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    /**
     * Sets the amount of EcoPoint the dinosaur egg generates from hatching.
     *
     * @param hatchPoint the amount of EcoPoint the dinosaur egg generates from hatching.
     */
    protected void setHatchPoint(int hatchPoint) {
        this.hatchPoint = hatchPoint;
    }

    /**
     * Sets the price of dinosaur egg.
     *
     * @param price the price of dinosaur egg from vending machine
     */
    protected void setPrice(int price) {
        this.price = price;
    }

    /**
     * A collection of allowable actions for other actors to perform on the actor.
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return a collection of allowable actions by other actors.
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = super.getAllowableActions(otherActor, direction, map);
        actions.add(new AttackAction(this));
        if (otherActor.getClass() == Player.class) {
            for (Item edibleItem : otherActor.getInventory()) {
                for (Edible dinoItem : this.getItems()) {
                    if (edibleItem.getClass() == dinoItem.getClass())
                        actions.add(new FeedAction(this, (Edible) edibleItem));
                }
            }
        }
        return actions;
    }

    /**
     * Returns the sex of the dinosaur.
     *
     * @return true if the dino is female, false otherwise.
     */
    public boolean isFemale() {
        return this.isFemale;
    }

    /**
     * Returns the gestation turns of the dinosaur.
     *
     * @return the gestation turns of the dinosaur.
     */
    public int getGestationTurnsLimit() {
        return gestationTurnsLimit;
    }

    /**
     * Returns the hatchTime of the dinosaur egg.
     *
     * @return the hatchTime of the dinosaur egg.
     */
    public int getHatchTime() {
        return hatchTime;
    }

    /**
     * Returns the rotTime of the dinosaur corpse.
     *
     * @return the rotTime of the dinosaur corpse.
     */
    public int getRotTime() {
        return rotTime;
    }

    /**
     * Get the restoration value from consuming the dead dinosaur.
     *
     * @return the restoration value from consuming the dead dinosaur.
     */
    public int getCorpseRestorationValue() {
        return 50;
    }

    /**
     * Getter for max hit points.
     *
     * @return the max hit points of the dinosaur.
     */
    public int getMaxHitPoints() {
        return this.maxHitPoints;
    }

    /**
     * Getter for hit points.
     *
     * @return the hit points of the dinosaur.
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Getter for hatchPoint.
     *
     * @return the eco points the dinosaur egg generates from hatching.
     */
    public int getHatchPoint() {
        return this.hatchPoint;
    }

    /**
     * Getter for price of the dinosaur egg if purchased from the vending machine.
     *
     * @return the price of the dinosaur egg.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Getter for items ArrayList.
     *
     * @return ArrayList of Items a dinosaur can eat.
     */
    public ArrayList<Edible> getItems() {
        return items;
    }

    /**
     * Getter for potential egg laying grounds.
     *
     * @return ArrayList of grounds a dinosaur can lay egg on.
     */
    public ArrayList<Ground> getEggLayingGrounds() {
        return this.eggLayingGrounds;
    }

    /**
     * Increases the dinosaurs water level.
     */
    public void addWaterLevel() {
        this.waterLevel += sipSize;
        if (this.waterLevel > this.maxWaterLevel) {
            this.waterLevel = maxWaterLevel;
        }
    }

    /**
     * Sets the water level to 10 when an unconscious dino is rained on.
     */
    @Override
    public void rainedOn() {
        if (!this.isConscious()) {
            this.waterLevel = 10;
        }
    }

    /**
     * If the dinosaur is unconscious, it keeps counting and eventually turns the dino into corpse.
     *
     * @param currentLocation the current location
     * @param map             game map.
     */
    public boolean unconscious(Location currentLocation, GameMap map) {
        if (this.hitPoints <= 0) {
            System.out.println(this + " at " + currentLocation.x() + "," +currentLocation.y()
                    + " is unconscious");
            unconsciousTurns++;
            if (unconsciousTurns == unconsciousTurnsLimit)
                death(currentLocation);
            return true;
        } else if (this.waterLevel <= 0) {
            System.out.println(this + " at " + currentLocation.x() + "," + currentLocation.y()
                    + " is unconscious");
            unconsciousTurns++;
            if (unconsciousTurns >= 15 && this.waterLevel <= 0) {
                death(currentLocation);
            }
            return true;
        } else {
            this.unconsciousTurns = 0;
            return false;
        }
    }

    /**
     * Carries out the process of death for a dinosaur.
     *
     * @param currentLocation the current location.
     */
    public void death(Location currentLocation) {
        System.out.println(this + " has died");
        currentLocation.addItem(new Corpse(this));
        currentLocation.map().removeActor(this);
    }

    /**
     * checks if dino is hungry, if so, adds hungry capability and prints hunger message
     *
     * @param currentLocation current location of the actor.
     */
    public void hunger(Location currentLocation) {
        if (this.hitPoints < this.hungerLevel) {
            this.addCapability(Hungry.HUNGRY);
            System.out.println(this + " at " + currentLocation.x() + "," + currentLocation.y()
                    + " is hungry!");
        } else {
            if (this.hasCapability(Hungry.HUNGRY)) {
                this.removeCapability(Hungry.HUNGRY);
            }
        }
    }

    /**
     * checks if dino is thirsty, if so, adds thirsty capability and prints thirst message
     *
     * @param currentLocation current location of the actor.
     */
    public void thirst(Location currentLocation) {
        if (this.waterLevel < this.thirstLevel) {
            this.addCapability(Thirsty.THIRSTY);
            System.out.println(this + " at " + currentLocation.x() + "," + currentLocation.y()
                    + " is thirsty!");
        } else {
            if (this.hasCapability(Thirsty.THIRSTY)) {
                this.removeCapability(Thirsty.THIRSTY);
            }
        }
    }
}