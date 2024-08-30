package game.grounds;

import edu.monash.fit2099.engine.Ground;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {

	/**
	 * Constructor for Floor.
	 */
	public Floor() {
		super('_');
	}

	@Override
	public void experienceRain(double rainfall) {
	}

	@Override
	public boolean isSquishable() {
		return false;
	}
}
