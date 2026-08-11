package core.cards;

import core.enums.CardType;
import core.models.Player;

public abstract class Card implements ICard {

    private String description;
    private CardType type;

    public Card(String description, CardType type) {
        this.description = description;
        this.type = type;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public CardType getType() {
        return null;
    }

    @Override
    public void apply(Player player) {

    }

    @Override
    public void returnToDeck() {

    }

    public abstract void executeEffect(Player player);
}
