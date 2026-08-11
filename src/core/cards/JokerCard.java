package core.cards;

import core.enums.CardType;
import core.models.Player;

public class JokerCard extends Card {

    public JokerCard(String description, CardType type) {
        super(description, type);
    }

    @Override
    public void executeEffect(Player player) {

    }
}
