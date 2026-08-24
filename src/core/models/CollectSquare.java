package core.models;

import core.strategies.ISquareAction;
import core.strategies.ReceiveMoneyAction;

public class CollectSquare implements ISquare {

    private String name;
    private int position;
    private ISquareAction action;

    public CollectSquare(String name, int position) {
        this.name = name;
        this.position = position;
        action = new ReceiveMoneyAction(500000);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void landOn(Player player) {
        System.out.println(player.getName() + " arrive sur la case " + name);
        action.execute(player);
    }
}
