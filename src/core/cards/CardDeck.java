package core.cards;

import java.util.*;
import java.util.logging.Logger;

public class CardDeck implements Iterable<ICard> {

    private final List<ICard> cards;
    private int currentIndex;
    private static final Logger logger = Logger.getLogger(CardDeck.class.getName());

    public CardDeck() {
        this.cards = new ArrayList<>();
        this.currentIndex = 0;
    }

    public List<ICard> getCards() {
        return cards;
    }

    public void addCard(ICard card) { cards.add(card); }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public ICard draw() {
        ICard card = cards.get(currentIndex);
        currentIndex = (currentIndex + 1) % cards.size();
        logger.info("Carte tirée: " + card.getDescription());
        return card;
    }

    public void returnsCard(ICard card) {
        cards.add(card);
        logger.info("Carte remise dans le paquet");
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int getSize() {
        return cards.size();
    }

    @Override
    public Iterator<ICard> iterator() {
        return new CardIterator();
    }

    private class CardIterator implements Iterator<ICard> {

        private int position;

        @Override
        public boolean hasNext() {
            return position < cards.size();
        }

        @Override
        public ICard next() {
            if (!hasNext()) throw new NoSuchElementException();
            return cards.get(position++);
        }
    }
}
