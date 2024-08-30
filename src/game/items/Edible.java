package game.items;

/**
 * Interface for diet.
 */
public interface Edible {
    /**
     * getter for restoration value of egg
     * @return restoration value of egg
     */
    int getRestorationValue();

    /**
     * returns true if item is difficult to eat
     * @return true if item is difficult to eat else false
     */
    boolean isDifficultToEat();
    /**
     * returns true if item fills up dino's HP
     * @return true if item fills up dino's HP else false
     */
    boolean isFilling();

    /**
     * returns true if item can be eaten over multiple turns
     * @return true if item can be eaten over multiple turns else false
     */
    boolean isPartiallyEdible();

    /**
     * allows item to be partially eaten, removes the points from it each turn
     */
    void decreaseRestorationValue(int amount);
}
