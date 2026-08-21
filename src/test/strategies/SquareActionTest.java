package test.strategies;

import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.models.Player;
import core.products.IProduct;
import core.products.Product;
import core.products.ProductFactory;
import core.strategies.BuyProductAction;
import core.strategies.ISquareAction;
import core.strategies.ReceiveMoneyAction;
import core.strategies.SellResourceAction;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SquareActionTest {

    private ISquareAction action;
    private Player player;

    @BeforeEach()
    void setUp() {
        player = new Player("Alice");
    }

    @Test()
    @DisplayName("BuyProductAction doit permettre d'acheter les produits d'une région indiquée.")
    void testBuyProductActionCanBuyProductsOfRegion() {
        BuyProductAction action = new BuyProductAction(null, Region.MAGHREB);

        List<IProduct> availableProducts = action.getAvailableProducts();

        assertFalse(availableProducts.isEmpty());

        assertTrue(
                player.getProperties().values().stream()
                        .flatMap(List::stream)
                        .allMatch(p -> p.getRegion() == Region.MAGHREB)
        );
    }

    @Test()
    @DisplayName("BuyProctAction doit permettre d'acheter les produits d'un continent indiqué.")
    void testBuyProductActionCanBuyProductsOfContinent() {
        BuyProductAction action = new BuyProductAction(Continent.AFRICA, null);

        List<IProduct> availableProducts = action.getAvailableProducts();

        assertFalse(availableProducts.isEmpty());

        assertTrue(
                player.getProperties().values().stream()
                        .flatMap(List::stream)
                        .allMatch(p -> p.getContinent() == Continent.AFRICA)
        );
    }

    @Test
    @DisplayName("Le continent doit être correctement initialisé")
    void testBuyProductActionStoresContinent() {
        BuyProductAction action =
                new BuyProductAction(Continent.AFRICA, null);
        assertEquals(
                Continent.AFRICA,
                action.getContinent()
        );
        assertNull(action.getRegion());
    }

    @Test
    @DisplayName("La région doit être correctement initialisée")
    void testBuyProductActionStoresRegion() {
        BuyProductAction action =
                new BuyProductAction(null, Region.MAGHREB);
        assertEquals(
                Region.MAGHREB,
                action.getRegion()
        );
        assertNull(action.getContinent());
    }

    @Test
    @DisplayName("Le filtre région ne doit contenir qu'une seule région")
    void testOnlyRequestedRegionIsReturned() {
        Map<Region, List<IProduct>> products =
                ProductFactory.createShop()
                        .getProducts(null, Region.MAGHREB);

        assertEquals(
                Set.of(Region.MAGHREB),
                products.keySet()
        );
    }

    @Test
    @DisplayName("Le filtre continent ne doit contenir que des produits du continent demandé")
    void testOnlyRequestedContinentProductsAreReturned() {
        Map<Region, List<IProduct>> products =
                ProductFactory.createShop()
                        .getProducts(Continent.AFRICA, null);

        assertTrue(
                products.values().stream()
                        .flatMap(List::stream)
                        .allMatch(p ->
                                p.getContinent() == Continent.AFRICA)
        );
    }

    @Test()
    @DisplayName("ReceiveMoneyAction doit donner de l'argent au joueur")
    void testReceiveMoneyActionIncreasePlayerMoney() {
        int initMoney = player.getMoney();
        action = new ReceiveMoneyAction(player, 1000000);
        action.execute(player);
        assertEquals(initMoney + 1000000, player.getMoney());
    }

    @Test
    @DisplayName("La description doit être correcte")
    void testGetDescription() {
        action = new SellResourceAction();
        assertEquals("Vente aux enchères", action.getDescription());
    }

    @Test
    @DisplayName("Execute ne doit pas lancer d'exception sans propriété")
    void testExecuteWithoutProperties() {
        action = new SellResourceAction();
        assertDoesNotThrow(() -> action.execute(player));
    }

    @Test
    @DisplayName("Un joueur sans propriété ne peut pas créer de lot")
    void testPlayerWithoutProperties() {
        assertTrue(player.getProperties().isEmpty());
    }

    @Test
    @DisplayName("Un joueur peut posséder plusieurs produits d'une même ressource")
    void testPlayerOwnsProductsOfSameResource() {
        IProduct p1 = new Product(Resource.PETROLE, 10, 1_000_000, Continent.AFRICA, Region.MAGHREB, "Maroc");
        IProduct p2 = new Product(Resource.PETROLE, 20, 2_000_000, Continent.AFRICA, Region.MAGHREB, "Algérie");

        player.addProperty(p1);
        player.addProperty(p2);

        assertEquals(
                2,
                player.getProperties().get(Resource.PETROLE).size()
        );
    }

    @Test
    @DisplayName("Le prix total d'un lot correspond à la somme des produits")
    void testLotTotalPriceCalculation() {

        IProduct p1 = new Product(Resource.PETROLE, 10, 1_000_000, Continent.AFRICA, Region.MAGHREB, "Maroc");
        IProduct p2 = new Product(Resource.PETROLE, 20, 2_000_000, Continent.AFRICA, Region.MAGHREB, "Algérie");

        player.addProperty(p1);
        player.addProperty(p2);

        int totalPrice = player.getProperties()
                .get(Resource.PETROLE)
                .stream()
                .mapToInt(IProduct::getPrice)
                .sum();

        assertEquals(3000000, totalPrice);
        assertEquals(1500000, totalPrice / 2);
    }
}
