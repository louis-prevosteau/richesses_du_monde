package core.cards;

import core.enums.CardType;
import core.models.Player;

public class ReceiveCard extends Card {
    public ReceiveCard(String description, CardType type) {
        super(description, type);
    }

    @Override
    public void executeEffect(Player player) {

    }
}
