package core.cards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CardDeck implements Iterable<ICard> {

    private final List<ICard> cards;
    private int currentIndex;

    public CardDeck() {
        this.cards = new ArrayList<>();
    }

    public List<ICard> getCards() {
        return cards;
    }

    public void addCard(ICard card) {}

    public void shuffle() {}

    public ICard draw() {
        return null;
    }

    public void returnsCard(ICard card) {}

    public boolean isEmpty() {
        return false;
    }

    public int getSize() {
        return 0;
    }

    public void clear() {}

    @Override
    public Iterator<ICard> iterator() {
        return null;
    }

    private class CardIterator implements Iterator<ICard> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public ICard next() {
            return null;
        }
    }
}
