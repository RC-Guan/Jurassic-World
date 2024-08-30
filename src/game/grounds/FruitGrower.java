package game.grounds;

import game.items.Fruit;

import java.util.ArrayList;

/**
 * Interface for all possible grounds to grow fruit.
 */
public interface FruitGrower {
    /**
     * A list of fruit.
     */
    ArrayList<Fruit> fruit = new ArrayList<>();

    /**
     * A method to determine whether the ground has grown fruit.
     *
     * @return true if it did grow fruit, false otherwise.
     */
    boolean growFruit();
}