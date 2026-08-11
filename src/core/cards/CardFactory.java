package core.cards;

import core.enums.Resource;

public class CardFactory {

    public static CardDeck createNewsDeck() {
        return null;
    }

    public static CardDeck CardDeckcreateJokerDeck() {
        return null;
    }

    public CardDeckBuilder builder() {
        return null;
    }

    public static class CardDeckBuilder {
        private final CardDeck deck;

        public CardDeckBuilder() {
            this.deck = new CardDeck();
        }

        public CardDeckBuilder addPayCard(String description, Resource resource, int[] amounts) {
            return null;
        }

        public CardDeckBuilder addReceiveCard(String description, Resource resource, int[] amounts) {
            return null;
        }

        public CardDeckBuilder addGiftCard(String description, int amountPerPlayer) {
            return null;
        }

        public CardDeck build() {
            return null;
        }
    }
}
