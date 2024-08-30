package game;
import edu.monash.fit2099.engine.Display;

/**
 * The main class for the Jurassic World game.
 */
public class Application {

    public static void main(String[] args) {
        JurassicWorld world = new JurassicWorld(new Display());
        world.runGame();
    }
}
