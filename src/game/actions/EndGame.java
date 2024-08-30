package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.ChallengeModeManager;
import game.EcoPoint;
import game.Player;

/**
 * A very special action for ending the game.
 */
public class EndGame extends Action {

    /**
     * ends the game, in challenge mode, figures out win/loss
     * @param actor The actor performing the action. (always player)
     * @param map The map the actor is on.
     * @return string with game result, or game has been ended
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        if (ChallengeModeManager.isActive()){
            int ecoPoints = EcoPoint.getEcoPoint();
            int turns = ((Player)actor).getNumTurns();
            System.out.println("You accumulated " + ecoPoints + " EcoPoints" + "in " + turns + " turns");
            if (ecoPoints >= ChallengeModeManager.getEcoPoints() && turns <= ChallengeModeManager.getMoves()){
                return "You have won";
            } else return "You have lost";
        }
        return "You have ended the Game";
    }

    /**
     * Returns a descriptive string.
     *
     * @param actor The actor performing the action. (not used)
     * @return the text we put on the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "End the Game";
    }
}
