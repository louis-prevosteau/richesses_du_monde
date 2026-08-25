package test.cards;

import core.cards.*;
import core.enums.CardType;
import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.manager.GameManager;
import core.models.Player;
import core.products.IProduct;
import core.products.Product;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    private ICard pay, receive, payCondition, receiveCondition, gift;
    private Player player;

    @BeforeEach()
    void setUp() {
        GameManager.getInstance().getPlayers().clear();
        pay = new PayCard("TestPay", CardType.NEWS, new int[]{4000000}, null);
        receive = new ReceiveCard("TestReceive", CardType.NEWS, new int[]{4000000}, null);
        payCondition = new PayCard("TestPayWithCondition", CardType.NEWS, new int[]{4000000, 2000000}, Resource.PETROLE);
        receiveCondition = new ReceiveCard("TestReceiveWithCondition", CardType.NEWS, new int[]{4000000, 2000000}, Resource.PETROLE);
        gift = new GiftCard("TestGift", CardType.NEWS, 1000000);
        player = new Player("Alice");
    }

    @Test()
    @DisplayName("Une PayCard diminue l'argent du jouueur.")
    void testPayCardApplyDecreasePlayerMoney() {
        int initMoney = player.getMoney();
        pay.apply(player);
        assertTrue(player.getMoney() < initMoney);
    }

    @Test()
    @DisplayName("Une ReceiveCard augmente l'argent du jouueur.")
    void testReceiveCardApplyIncreasePlayerMoney() {
        int initMoney = player.getMoney();
        receive.apply(player);
        assertTrue(player.getMoney() > initMoney);
    }

    @Test()
    @DisplayName("PayCard : Si un joueur possède la ressource indiquée sur la carte, il paie plus cher.")
    void testPayWithConditionWhenPlayerGetResource() {
        IProduct product = new Product(
                Resource.PETROLE,
                15,
                42,
                Continent.ASIA_OCEANIA,
                Region.MOYEN_ORIENT,
                "Qatar"
        );
        player.addProperty(product);
        int initMoney = player.getMoney();
        payCondition.apply(player);
        assertEquals(initMoney - 4000000, player.getMoney());
    }

    @Test()
    @DisplayName("PayCard : Si un joueur ne possède pas la ressource indiquée sur la carte, il paie moins cher.")
    void testPayWithConditionWhenPlayerDoesntGetResource() {
        int initMoney = player.getMoney();
        payCondition.apply(player);
        assertEquals(initMoney - 2000000, player.getMoney());
    }

    @Test()
    @DisplayName("ReceiveCard : Si un joueur possède la ressource indiquée sur la carte, il reçoit moins d'argent.")
    void testReceveWithConditionWhenPlayerGetResource() {
        IProduct product = new Product(
                Resource.PETROLE,
                15,
                42,
                Continent.ASIA_OCEANIA,
                Region.MOYEN_ORIENT,
                "Qatar"
        );
        player.addProperty(product);
        int initMoney = player.getMoney();
        receiveCondition.apply(player);
        assertEquals(initMoney + 4000000, player.getMoney());
    }

    @Test()
    @DisplayName("ReceiveCard : Si un joueur ne possède pas la ressource indiquée sur la carte, il reçoit moins d'argent.")
    void testReceiveWithConditionWhenPlayerDoesntGetResource() {
        int initMoney = player.getMoney();
        receiveCondition.apply(player);
        assertEquals(initMoney + 2000000, player.getMoney());
    }

    @Test()
    @DisplayName("GiftCard : le joueur reçoit 1000000 € de la part des autres joueurs")
    void testGiftCard() {
        Player bob = new Player("Bob");
        Player charlie = new Player("Charlie");
        GameManager.getInstance().addPlayer(player);
        GameManager.getInstance().addPlayer(bob);
        GameManager.getInstance().addPlayer(charlie);
        int initialMoneyP1 = player.getMoney();
        int initialMoneyP2 = bob.getMoney();
        int initialMoneyP3 = charlie.getMoney();
        gift.apply(player);
        assertEquals(initialMoneyP1 + 2000000, player.getMoney());
        assertEquals(initialMoneyP2 - 1000000, bob.getMoney());
        assertEquals(initialMoneyP3 - 1000000, charlie.getMoney());
    }
}
