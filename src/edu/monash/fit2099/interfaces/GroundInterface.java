package edu.monash.fit2099.interfaces;

/**
 * This interface provides the ability to add methods to Ground, without modifying code in the engine,
 * or downcasting references in the game.   
 */

public interface GroundInterface {
    /**
     * what happens when the ground is rained on
     * @param rainfall the amount of rainfall experienced.
     */
    void experienceRain(double rainfall);

    /**
     * determines if ground is squishable
     * @return true if ground is squishable else false
     */
    boolean isSquishable();
}
