package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Class representing one map within the system.
 * Each map will have a chance to rain every 10 turns.
 * The map may border another map within the system.
 * <p>
 * The system can have multiple maps, and Actors can move between them. Only the
 * map that the player is currently on will be displayed, but Actors on all maps
 * will be queried on each turn for their moves -- that is, time does not stop
 * when the player leaves a map.
 * <p>
 * It's important to put the GameMap in the World before using it.
 */
public class MyGameMap extends GameMap {
    private int turn = 0;

    /**
     * Constructor that creates a map from a sequence of ASCII strings.
     *
     * @param groundFactory Factory to create Ground objects
     * @param lines         List of Strings representing rows of the map
     */
    public MyGameMap(GroundFactory groundFactory, List<String> lines) {
        super(groundFactory, lines);
    }

    /**
     * Called once per turn, so that maps can experience the passage of time.
     * The sky may rain every 10 turns, with a probability of 20%.
     */
    public void tick() {
        turn++;

        // Tick over all the items in inventories.
        for (Actor actor : actorLocations) {
            if (this.contains(actor)) {
                for (Item item : new ArrayList<>(actor.getInventory())) { // Copy the list in case the item wants to leave
                    item.tick(actorLocations.locationOf(actor), actor);
                }
            }
        }

        for (int y : heights) {
            for (int x : widths) {
                this.at(x, y).tick();
            }
        }

        // Calculate chance of rain.
        double rainfall = getRandomDouble(0.6, 0.1);

        Random r = new Random();
        if (turn == 10 && r.nextDouble() <= 0.2) {
            System.out.println("It's raining!");
            for (int x : widths) {
                for (int y : heights) {
                    this.at(x, y).getGround().experienceRain(rainfall);
                    if (this.at(x, y).containsAnActor()) {
                        this.at(x, y).getActor().rainedOn();
                    }
                    turn = 0;
                }
            }
        }
    }

    /**
     * Returns random double between minimum and maximum range.
     *
     * @param max maximum range.
     * @param min minimum range.
     * @return a random double between minimum and maximum range.
     */
    private double getRandomDouble(double max, double min) {
        return (Math.random() * (max - min)) + min;
    }

    /**
     * Add game map edges according to the orientation given.
     *
     * @param orientation The orientation of the map, determines which orientation the map will be added to the existing maps.
     *                    E.G. if orientation is NORTH, then it will be added to the NORTH of the existing gameMap.
     * @param gameMap     the game map that borders the current map.
     */
    protected void addMapEdges(Orientation orientation, MyGameMap gameMap) {

        if (gameMap.getXRange().max() == this.widths.max() && gameMap.getYRange().max() == this.heights.max()) {
            switch (orientation) {
                case NORTH:
                    for (int x : widths) {
                        int y = this.heights.max();
                        int thatY = gameMap.getYRange().min();
                        Location here = this.at(x, y);
                        addEdgeExit(here, gameMap, x, thatY, "South", "2");
                        Location there = gameMap.at(x, thatY);
                        addEdgeExit(there, this, x, y, "North", "8");
                    }
                    break;
                case SOUTH:
                    for (int x : widths) {
                        int y = this.heights.min();
                        int thatY = gameMap.getYRange().max();
                        Location here = this.at(x, y);
                        addEdgeExit(here, gameMap, x, thatY, "North", "8");
                        Location there = gameMap.at(x, thatY);
                        addEdgeExit(there, this, x, y, "South", "2");
                    }
                    break;
                case WEST:
                    for (int y : heights) {
                        int x = this.widths.max();
                        int thatX = gameMap.getXRange().min();
                        Location here = this.at(x, y);
                        addEdgeExit(here, gameMap, thatX, y, "East", "6");
                        Location there = gameMap.at(thatX, y);
                        addEdgeExit(there, this, x, y, "West", "4");
                    }
                    break;
                case EAST:
                    for (int y : heights) {
                        int x = this.widths.min();
                        int thatX = gameMap.getXRange().max();
                        Location here = this.at(x, y);
                        addEdgeExit(here, gameMap, thatX, y, "West", "4");
                        Location there = gameMap.at(thatX, y);
                        addEdgeExit(there, this, x, y, "East", "6");
                    }
                    break;
            }
        }
    }

    /**
     * Builder method for making Exits on the edges.
     *
     * @param here    the current location
     * @param gameMap the game map that borders the current map
     * @param x       X coordinate
     * @param y       Y coordinate
     * @param name    name of the Exit
     * @param hotKey  the hotkey for the appropriate Action
     */
    private void addEdgeExit(Location here, MyGameMap gameMap, int x, int y, String name, String hotKey) {
        if (gameMap.getXRange().contains(x) && gameMap.getYRange().contains(y)) {
            here.addExit(new Exit(name, gameMap.at(x, y), hotKey));
        }
    }
}
