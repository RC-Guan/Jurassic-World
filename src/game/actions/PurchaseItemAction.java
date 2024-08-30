package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.EcoPoint;
import game.grounds.VendingMachine;
import game.items.Purchasable;

/**
 * Special Action for the player to purchase an item.
 */
public class PurchaseItemAction extends Action {
    private final VendingMachine vending;
    private final Purchasable item;

    /**
     * Constructor for PurchaseItemAction.
     *
     * @param vending the vending machine
     * @param item    items for purchase
     */
    public PurchaseItemAction(VendingMachine vending, Purchasable item) {
        this.vending = vending;
        this.item = item;
    }

    /**
     * Executes the action of purchasing an item.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return the string output.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        int price = item.getPrice();
        if (EcoPoint.getEcoPoint() >= price) {
            EcoPoint.decreaseEcoPoints(price);
            actor.addItemToInventory((Item) item);
            int value = vending.getItem(item);
            value--;
            vending.getVendingMachine().replace(item, vending.getItem(item), value);
            return "Player purchased " + item + " for " + item.getPrice() + "." +
                    System.lineSeparator() + vending.getItem(item) + " " + item + "s left." +
                    System.lineSeparator() + EcoPoint.getEcoPoint() + " EcoPoints left.";
        } else {
            return "Not enough EcoPoints";
        }
    }

    /**
     * Returns a descriptive string.
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Purchase " + item + " for " + item.getPrice() + " EcoPoints";
    }
}
