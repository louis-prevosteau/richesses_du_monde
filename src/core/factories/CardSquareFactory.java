package core.factories;

import core.enums.CardType;
import core.models.CardSquare;
import core.models.ISquare;

public class CardSquareFactory extends SquareFactory {

    private final CardType type;

    public CardSquareFactory(CardType type) {
        this.type = type;
    }

    @Override
    public ISquare createSquare(int position) {
        return new CardSquare(type, position);
    }
}
