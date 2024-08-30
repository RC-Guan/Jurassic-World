package game;

/**
 * A class that manages the eco points of the game.
 */
public class EcoPoint {
    private static int ecoPoint = 0;

    /**
     * Setter for ecoPoint.
     *
     * @param ecoPoint ecoPoint.
     */
    public static void setEcoPoint(int ecoPoint) {
        EcoPoint.ecoPoint = ecoPoint;
    }

    /**
     * Getter for ecoPoint.
     *
     * @return value of ecoPoint.
     */
    public static int getEcoPoint() {
        return ecoPoint;
    }

    /**
     * Increases the ecoPoint
     *
     * @param addEcoPoints ecoPoints to be added.
     */
    public static void increaseEcoPoints(int addEcoPoints) {
        ecoPoint += addEcoPoints;
        //System.out.println(addEcoPoints + " EcoPoint/s has been added.\n" + EcoPoint.ecoPoint + " in total.");
    }

    /**
     * Decreases the ecoPoint
     *
     * @param minusEcoPoints ecoPoints to be subtracted.
     */
    public static void decreaseEcoPoints(int minusEcoPoints) {
        ecoPoint -= minusEcoPoints;
        System.out.println("You have spent " + minusEcoPoints + " EcoPoints.");
    }
}
