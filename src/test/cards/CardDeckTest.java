package test.cards;

import core.cards.CardDeck;
import core.cards.ICard;
import core.cards.JokerCard;
import core.enums.CardType;
import org.junit.jupiter.api.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class CardDeckTest {

    private CardDeck deck;

    @BeforeEach()
    void setUp() {
        deck = new CardDeck();
    }

    @Test()
    void testDeckIsEmptyWhenInitialized() {
        assertTrue(deck.isEmpty());
    }

    @Test
    @DisplayName("iterator() doit parcourir toutes les cartes du paquet")
    void testIterator() {

        CardDeck deck = new CardDeck();

        ICard card1 = new JokerCard("Joker 1", CardType.JOKER);
        ICard card2 = new JokerCard("Joker 2", CardType.JOKER);

        deck.addCard(card1);
        deck.addCard(card2);

        Iterator<ICard> iterator = deck.iterator();

        assertTrue(iterator.hasNext());
        assertSame(card1, iterator.next());

        assertTrue(iterator.hasNext());
        assertSame(card2, iterator.next());

        assertFalse(iterator.hasNext());
    }

}
