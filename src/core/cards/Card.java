package core.cards;

import core.enums.CardType;
import core.manager.GameManager;
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
        if (gameManager == null) {
            System.err.println("❌ Erreur : GameManager non initialisé");
            return;
        }

        CardType cardType = getType();
        if (cardType == null) {
            System.err.println("❌ Erreur : Type de carte non défini");
            return;
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
