package core.cards;

import core.enums.CardType;
import core.models.Player;

public class PayCard extends Card {
    public PayCard(String description, CardType type) {
        super(description, type);
    }

    @Override
    public void executeEffect(Player player) {

    }
}
