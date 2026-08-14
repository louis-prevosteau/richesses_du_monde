package test.models;

import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.products.IProduct;
import core.models.Player;
import core.products.Product;
import core.states.NormalState;
import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;

    @BeforeEach()
    void setUp() {
        player = new Player("Alice");
    }

    @Test()
    @DisplayName("Un joueur doit :\n" +
            "- avoir 60000000 €\n" +
            "- être à la position 0\n" +
            "- état normal\n" +
            "- pas de titre d'exploitation (products)\n" +
            "- pas de jokers\n" +
            "- être à son premier tour deplateau")
    void initPlayer() {
        assertEquals(60000000, player.getMoney());
        assertEquals(0, player.getPosition());
        assertInstanceOf(NormalState.class, player.getState());
        assertTrue(player.getProperties().isEmpty());
        assertTrue(player.getJokers().isEmpty());
        assertEquals(1, player.getBoardTour());
    }

    @Test()
    @DisplayName("addProduct() doit ajouter un titre d'exploitation")
    void testAddProductPropertiesContainsProduct() {
        IProduct product = new Product(Resource.PETROLE, 15, 42, Continent.ASIA_OCEANIA, Region.MOYEN_ORIENT, "Qatar");
        player.addProperty(product);
        assertTrue(player.getProperties().getOrDefault(Resource.PETROLE, Collections.emptyList()).contains(product));
    }

    @Test()
    @DisplayName("removeProduct() doit returer un titre d'exploitation")
    void testRemoveProductPropertiesNotContainsProduct() {
        IProduct product = new Product(Resource.PETROLE, 15, 42, Continent.ASIA_OCEANIA, Region.MOYEN_ORIENT, "Qatar");
        player.addProperty(product);
        player.removeProperty(product);
        assertFalse(player.getProperties().getOrDefault(Resource.PETROLE, Collections.emptyList()).contains(product));
    }

    @Test
    @DisplayName("getPropertiesByResource(resource) doit retourner les propriétés d'une ressource donnée en paramètre")
    void testGetPropertiesByResourcePetroleReturnsTrue() {
        IProduct product = new Product(Resource.PETROLE, 15, 42, Continent.ASIA_OCEANIA, Region.MOYEN_ORIENT, "Qatar");
        player.addProperty(product);
        Map<Resource, List<IProduct>> result =
                player.getPropertiesByResource(Resource.PETROLE);
        assertEquals(1, result.size());
        assertTrue(result.containsKey(Resource.PETROLE));
        assertEquals(1, result.get(Resource.PETROLE).size());
        assertSame(product, result.get(Resource.PETROLE).get(0));
    }

    @Test()
    @DisplayName("roll() doit lancer les dés (compris entre 1 et 12)")
    void testRollReturnBetween1And12() {
        int result = player.roll();
        assertTrue(result >= 1 && result <= 12);
    }

    @Test()
    @DisplayName("isDouble() retourne true avec 2 dés égaux")
    void testIsDoubleWith4And4ReturnsTrue() {
        player.setDice1(4);
        player.setDice2(4);
        assertTrue(player.isDouble());
    }

    @Test()
    @DisplayName("isDouble() retourne false avec 2 dés différents")
    void testIsDoubleWith4And1ReturnsTrue() {
        player.setDice1(4);
        player.setDice2(1);
        assertFalse(player.isDouble());
    }

    @Test()
    @DisplayName("move() doit changer la position du joueur sur le plateau")
    void testMoveChangesPlayerPositionWith4Steps() {
        int initPosition = player.getPosition();
        player.move(4);
        assertEquals(initPosition + 4, player.getPosition());
    }

    @Test()
    @DisplayName("pay() diminue la monnaie d'un joueur")
    void testPay() {
        player.pay(1000000);
        assertEquals(59000000, player.getMoney());
    }

    @Test()
    @DisplayName("receive() augmente la monnaie d'un joueur")
    void testReceive() {
        player.receive(1000000);
        assertEquals(61000000, player.getMoney());
    }

    @Test()
    @DisplayName("canAfford() vérifie la capacité de paiement du joueur. Il retourne true si le solde est supérieur au montant à payer")
    void testCanAffordWithAmount6000000ReturnsTrue() {
        assertTrue(player.canAfford(6000000));
    }

    @Test()
    @DisplayName("canAfford() vérifie la capacité de paiement du joueur. Il retourne false si le solde est inférieur au montant à payer")
    void testCanAffordWithAmount600000000ReturnsFalse() {
        assertFalse(player.canAfford(600000000));
    }
}
