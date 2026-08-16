package test.cards;

import core.cards.CardDeck;
import core.cards.CardFactory;
import core.cards.ICard;
import core.enums.CardType;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardFactoryTest {

    @Test()
    @DisplayName("createNewsDeck() doit retourner une instance de CardDeck et de type News.")
    void testCreateNewsDeckReturnsCardDeckInstanceTypeNews() {
        assertNotNull(CardFactory.createNewsDeck());
        List<ICard> cards = CardFactory.createNewsDeck().getCards();
        assertInstanceOf(CardDeck.class, CardFactory.createNewsDeck());
        assertTrue(cards.stream().allMatch(card -> card.getType().equals(CardType.NEWS)));
    }

    @Test()
    @DisplayName("createJokerDeck() doit retourner une instance de CardDeck et de type Joker.")
    void testCreateJokerDeckReturnsCardDeckInstanceTypeJoker() {
        assertNotNull(CardFactory.createJokerDeck());
        List<ICard> cards = CardFactory.createJokerDeck().getCards();
        assertInstanceOf(CardDeck.class, CardFactory.createJokerDeck());
        assertTrue(cards.stream().allMatch(card -> card.getType().equals(CardType.JOKER)));
    }
}
