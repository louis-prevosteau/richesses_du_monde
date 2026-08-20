package test.strategies;

import core.enums.Continent;
import core.enums.Region;
import core.models.Player;
import core.products.IProduct;
import core.products.ProductFactory;
import core.strategies.BuyProductAction;
import core.strategies.ISquareAction;
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
    @DisplayName("BuyProctAction doit permettre d'acheter les produits d'une région indiquée.")
    void testBuyProductActionCanBuyProductsOfRegion() {
        action = new BuyProductAction(null, Region.MAGHREB);
        action.execute(player);
    }

    @Test()
    @DisplayName("BuyProctAction doit permettre d'acheter les produits d'un continent indiqué.")
    void testBuyProductActionCanBuyProductsOfContinent() {
        action = new BuyProductAction(Continent.AFRICA, null);
        action.execute(player);
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
}
