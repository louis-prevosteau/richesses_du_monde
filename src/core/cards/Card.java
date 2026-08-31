package core.cards;

import core.enums.CardType;
import core.manager.GameManager;
import core.models.Player;

public abstract class Card implements ICard {

    private final String description;
    private final CardType type;

    protected Card(String description, CardType type) {
        this.description = description;
        this.type = type;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public CardType getType() {
        return type;
    }

    @Override
    public void apply(Player player) {
        executeEffect(player);
    }

    @Override
    public void returnToDeck() {
        GameManager gameManager = GameManager.getInstance();
        CardType cardType = getType();
        if (cardType == null) {
            throw new IllegalArgumentException("Erreur : Type de carte non défini");
        }

        if (cardType.equals(CardType.NEWS)) {
            CardDeck newsDeck = gameManager.getNews();
            if (newsDeck != null) newsDeck.returnsCard(this);
        }
        else if (cardType.equals(CardType.JOKER)) {
            CardDeck jokers = gameManager.getJokers();
            if (jokers != null) jokers.returnsCard(this);
        }
    }

    protected abstract void executeEffect(Player player);
}
