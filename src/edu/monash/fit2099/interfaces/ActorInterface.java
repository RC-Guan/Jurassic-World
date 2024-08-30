package edu.monash.fit2099.interfaces;

/**
 * This interface provides the ability to add methods to Actor, without modifying code in the engine,
 * or downcasting references in the game.   
 */

public interface ActorInterface {
    /**
     * if this actor has Attack Cooling
     * @return
     */
    boolean hasAttackCooling();

    /**
     * triggers the attack cooling for actors.
     */
    void triggerAttackCooling();

    /**
     * if this actor isFemale
     * @return
     */
    boolean isFemale();

    /**
     * what happens when this actor is rained on
     */
    void rainedOn();
}
