package core.models;

import core.strategies.ISquareAction;
import core.strategies.SellResourceAction;

public class SaleSquare implements ISquare {

    private int position;
    private ISquareAction action;

    public SaleSquare(int position) {
        this(position, new SellResourceAction());
    }

    public SaleSquare(int position, ISquareAction action) {
        this.position = position;
        this.action = action;
    }

    @Override
    public String getName() {
        return "Enchères";
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void landOn(Player player) {
        System.out.println(player.getName() + " arrive sur la case " + getName());
        action.execute(player);
    }
}
