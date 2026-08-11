package core.cards;

import core.enums.CardType;
import core.models.Player;

public interface ICard {

    String getDescription();

    CardType getType();

    void apply(Player player);

    void returnToDeck();
}
