package game.items;

import edu.monash.fit2099.engine.WeaponItem;
/**
 * A class representing weapon laser gun.
 */
public class LaserGun extends WeaponItem implements Purchasable {

    /**
     * Constructor for LaserGun
     */
    public LaserGun() {
        super("LaserGun", 'G', 100, "shoots");
    }

    public int getPrice() {
        return 500;
    }
}
