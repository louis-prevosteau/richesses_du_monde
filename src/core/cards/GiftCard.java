package core.cards;

import core.enums.CardType;
import core.models.Player;

public class GiftCard extends Card {

    public GiftCard(String description, CardType type) {
        super(description, type);
    }

    @Override
    public void executeEffect(Player player) {

    }
}
