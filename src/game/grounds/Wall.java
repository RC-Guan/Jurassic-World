package game.grounds;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import game.capabilities.Flying;

/**
 * A class that represents wall.
 */
public class Wall extends Ground {

	/**
	 * Constructor for Wall.
	 */
	public Wall() {
		super('#');
	}

	/**
	 * Lets flying actors enter, but not walking ones.
	 *
	 * @param actor the Actor to check
	 * @return if the actor can enter the location.
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return actor.hasCapability(Flying.FLYING);
	}
	
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}

	@Override
	public void experienceRain(double rainfall) {

	}

	@Override
	public boolean isSquishable() {
		return false;
	}
}
