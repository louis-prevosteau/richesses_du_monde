package core.models;

import core.enums.CardType;
import core.strategies.DrawCardAction;
import core.strategies.ISquareAction;

public class CardSquare implements ISquare {

    private CardType type;
    private int position;
    private ISquareAction action;

    public CardSquare(CardType type, int position) {
        this.type = type;
        this.position = position;
        this.action = new DrawCardAction(type);
    }

    @Override
    public String getName() {
        return type.equals(CardType.NEWS) ? "Actualités" : "Joker";
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void landOn(Player player) {
        action.execute(player);
    }
}
