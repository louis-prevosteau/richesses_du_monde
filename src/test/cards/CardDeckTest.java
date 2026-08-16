package test.cards;

import core.cards.CardDeck;
import core.cards.ICard;
import org.junit.jupiter.api.*;

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

}
