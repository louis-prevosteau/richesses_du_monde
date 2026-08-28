package test.commands;

import core.cards.JokerCard;
import core.commands.*;
import core.enums.CardType;
import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.manager.GameManager;
import core.models.Player;
import core.products.IProduct;
import core.products.Product;
import org.junit.jupiter.api.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    private Player player;
    private ICommand command;

    @BeforeEach()
    void setUp() {
        player = new Player("Alice");
    }

    @Test()
    @DisplayName("AddJokerCardCommand doit ajouter une carte joker au joueur")
    void testAddJokerCardCommandAddJokerToPlayer() {
        JokerCard card = new JokerCard("Joker", CardType.JOKER);
        command = new AddJokerCardCommand(player, card);
        command.execute();
        assertTrue(player.getJokers().contains(card));
    }

    @Test()
    @DisplayName("UseJokerCommand doit retirer une carte joker au joueur")
    void testAddJokerCardCommandRemoveJokerToPlayer() {
        JokerCard card = new JokerCard("Joker", CardType.JOKER);
        command = new AddJokerCardCommand(player, card);
        command.execute();
        command = new UseJokerCommand(player);
        command.execute();
        assertFalse(player.getJokers().contains(card));
    }

    @Test()
    @DisplayName("RollDiceCommand lance le dé pour un joueur")
    void testRollDiceCommandRollPlayerDice() {
        command = new RollDiceCommand(player);
        command.execute();
        assertEquals(player.getTotalDice(), player.getDice1() + player.getDice2());
    }

    @Test()
    @DisplayName("MoveCommand modifie la position du joueur")
    void testMoveCommand_4_SetPlayerPositionTo4() {
        int initPosition = player.getPosition();
        command = new MoveCommand(player, 4);
        command.execute();
        assertEquals(initPosition + 4, player.getPosition());
    }

    @Test()
    @DisplayName("BuyProductCommand doit permettre à un joueur d'acheter et d'acquérir la propriété d'un produit")
    void testBuyProductCommandPlayerOwnedProductBought() {
        IProduct product = new Product(Resource.PETROLE, 15, 42, Continent.ASIA_OCEANIA, Region.MOYEN_ORIENT, "Qatar");
        command = new BuyProductCommand(player, product);
        command.execute();
        player.displayProfile();
        assertTrue(player.getProperties().getOrDefault(Resource.PETROLE, Collections.emptyList()).contains(product));
        assertEquals(product.getOwner(), player);
    }

    @Test()
    @DisplayName("DrawCardCommand doit tirer une carte Actualités et appliqué son effet au joueur")
    void testDrawCardCommandNewsApplyingEffect() {
        GameManager.getInstance().reset();
        command = new DrawCardCommand(player, CardType.NEWS);
        command.execute();
        assertDoesNotThrow(() -> command.execute());
    }
}
