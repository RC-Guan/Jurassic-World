package game.items;

import edu.monash.fit2099.engine.Item;

/**
 * Base class for any item that can be picked up and dropped.
 */
public abstract class PortableItem extends Item {

	/**
	 * Constructor
	 * @param name name of the portable item.
	 * @param displayChar display character on the map.
	 */
	public PortableItem(String name, char displayChar) {
		super(name, displayChar, true);
	}
}
