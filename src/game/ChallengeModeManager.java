package game;

public class ChallengeModeManager {
    private static boolean active = false;
    private static int moves;
    private static int ecoPoints;

    public static void setActive(boolean active){
        ChallengeModeManager.active = active;
    }

    public static void setMoves(int moves) {
        ChallengeModeManager.moves = moves;
    }

    public static void setEcoPoints(int ecoPoints) {
        ChallengeModeManager.ecoPoints = ecoPoints;
    }

    public static int getMoves() {
        return moves;
    }

    public static int getEcoPoints() {
        return ecoPoints;
    }

    public static boolean isActive() {
        return active;
    }
}
