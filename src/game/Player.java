package game;

import edu.monash.fit2099.engine.*;
import game.actions.EndGame;

/**
 * Class representing the Player.
 */
public class Player extends Actor {
    private final Menu menu = new Menu();
    private int numTurns; //keeps track of the number of turns a player has taken

    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.numTurns = 0;
    }


    /**
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return a menu of actions the player is allowed to perform.
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if ((ChallengeModeManager.isActive()) && (numTurns >= ChallengeModeManager.getMoves())) {
            return new EndGame();
        }

        numTurns++;

        System.out.println(name + " has " + EcoPoint.getEcoPoint() + " EcoPoints.");

        Location here = map.locationOf(this);
        Actions hereActions = here.getGround().allowableActions(this, here, "here");
        actions.add(hereActions);
        actions.add(new EndGame());

        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();
        return menu.showMenu(this, actions, display);
    }

    @Override
    public boolean hasAttackCooling() {
        return false;
    }

    @Override
    public void triggerAttackCooling() {

    }

    @Override
    public boolean isFemale() {
        return false;
    }

    @Override
    public void rainedOn() {

    }

    /**
     * getter for the number of turns
     *
     * @return number of turns
     */
    public int getNumTurns() {
        return numTurns;
    }
}
