package test.strategies;

import core.enums.CardType;
import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.manager.GameManager;
import core.models.Player;
import core.products.IProduct;
import core.products.Product;
import core.products.ProductFactory;
import core.products.Shop;
import core.strategies.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SquareActionTest {

    private ISquareAction action;
    private Player player;
    private Shop shop;

    @BeforeEach()
    void setUp() {
        GameManager.getInstance().getPlayers().clear();
        GameManager.getInstance().reset();
        player = new Player("Alice");
        GameManager.getInstance().addPlayer(player);
        shop = GameManager.getInstance().getShop();
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

    @Test
    @DisplayName("Le joueur peut annuler la vente")
    void executeCanBeCancelled() {
        IProduct product1 =
                new Product(
                        Resource.PETROLE,
                        10,
                        4_000_000,
                        Continent.ASIA_OCEANIA,
                        Region.MOYEN_ORIENT,
                        "Qatar"
                );

        IProduct product2 =
                new Product(
                        Resource.PETROLE,
                        15,
                        5_000_000,
                        Continent.ASIA_OCEANIA,
                        Region.MOYEN_ORIENT,
                        "Arabie Saoudite"
                );

        player.addProperty(product1);
        player.addProperty(product2);
        String input = "-1\n";

        action =
                new SellResourceAction(
                        new Scanner(
                                new ByteArrayInputStream(
                                        input.getBytes()
                                )
                        )
                );

        int propertyCountBefore =
                player.getProperties()
                        .values()
                        .stream()
                        .mapToInt(List::size)
                        .sum();

        action.execute(player);

        int propertyCountAfter =
                player.getProperties()
                        .values()
                        .stream()
                        .mapToInt(List::size)
                        .sum();

        assertEquals(
                propertyCountBefore,
                propertyCountAfter
        );
    }

    @Test
    @DisplayName("Le joueur peut sélectionner un lot valide")
    void executeAcceptsValidChoice() {
        IProduct product1 =
                new Product(
                        Resource.PETROLE,
                        10,
                        4_000_000,
                        Continent.ASIA_OCEANIA,
                        Region.MOYEN_ORIENT,
                        "Qatar"
                );

        IProduct product2 =
                new Product(
                        Resource.PETROLE,
                        15,
                        5_000_000,
                        Continent.ASIA_OCEANIA,
                        Region.MOYEN_ORIENT,
                        "Arabie Saoudite"
                );

        player.addProperty(product1);
        player.addProperty(product2);
        String input = "0\n";

        SellResourceAction action =
                new SellResourceAction(
                        new Scanner(
                                new ByteArrayInputStream(
                                        input.getBytes()
                                )
                        )
                );

        assertDoesNotThrow(
                () -> action.execute(player)
        );
    }

    @Test
    @DisplayName("Les produits doivent être regroupés par ressource")
    void playerPropertiesAreGroupedByResource() {
        IProduct product1 =
                new Product(
                        Resource.PETROLE,
                        10,
                        4_000_000,
                        Continent.ASIA_OCEANIA,
                        Region.MOYEN_ORIENT,
                        "Qatar"
                );

        IProduct product2 =
                new Product(
                        Resource.PETROLE,
                        15,
                        5_000_000,
                        Continent.ASIA_OCEANIA,
                        Region.MOYEN_ORIENT,
                        "Arabie Saoudite"
                );

        player.addProperty(product1);
        player.addProperty(product2);
        Map<Resource, List<IProduct>> properties =
                player.getProperties();

        assertTrue(
                properties.containsKey(Resource.PETROLE)
        );

        assertEquals(
                2,
                properties.get(Resource.PETROLE).size()
        );
    }

    @Test
    @DisplayName("Le type NEWS doit être correctement stocké")
    void testNewsTypeStored() {
        DrawCardAction action =
                new DrawCardAction(CardType.NEWS);

        assertEquals(CardType.NEWS, action.getType());
    }

    @Test
    @DisplayName("Le type JOKER doit être correctement stocké")
    void testJokerTypeStored() {
        DrawCardAction action =
                new DrawCardAction(CardType.JOKER);

        assertEquals(CardType.JOKER, action.getType());
    }

    @Test
    @DisplayName("La description doit être correcte")
    void testDescription() {
        DrawCardAction action =
                new DrawCardAction(CardType.NEWS);

        assertEquals(
                "Tirer une carte",
                action.getDescription()
        );
    }

    @Test
    @DisplayName("execute ne doit pas lever d'exception avec une carte NEWS")
    void testExecuteNews() {
        DrawCardAction action =
                new DrawCardAction(CardType.NEWS);

        assertDoesNotThrow(() ->
                action.execute(player)
        );
    }

    @Test
    @DisplayName("Le joueur peut acheter un produit puis arrêter")
    void executeCanBuyOneProduct() {

        List<IProduct> products =
                shop.getProducts(
                                Continent.AFRICA,
                                null
                        )
                        .values()
                        .stream()
                        .flatMap(List::stream)
                        .toList();

        int propertiesBefore =
                player.getProperties()
                        .values()
                        .stream()
                        .mapToInt(List::size)
                        .sum();

        Scanner scanner =
                new Scanner(new StringReader("0\n-1\n"));

        BuyProductAction action =
                new BuyProductAction(
                        Continent.AFRICA,
                        null,
                        scanner
                );

        action.execute(player);

        int propertiesAfter =
                player.getProperties()
                        .values()
                        .stream()
                        .mapToInt(List::size)
                        .sum();

        assertEquals(
                propertiesBefore + 1,
                propertiesAfter
        );
    }

    @Test
    @DisplayName("Le joueur peut annuler immédiatement")
    void executeCanStopImmediately() {

        int propertiesBefore =
                player.getProperties()
                        .values()
                        .stream()
                        .mapToInt(List::size)
                        .sum();

        Scanner scanner =
                new Scanner(new StringReader("-1\n"));

        BuyProductAction action =
                new BuyProductAction(
                        Continent.AFRICA,
                        null,
                        scanner
                );

        action.execute(player);

        int propertiesAfter =
                player.getProperties()
                        .values()
                        .stream()
                        .mapToInt(List::size)
                        .sum();

        assertEquals(
                propertiesBefore,
                propertiesAfter
        );
    }

    @Test
    @DisplayName("Un choix invalide ne provoque aucun achat")
    void executeRejectsInvalidChoice() {

        int propertiesBefore =
                player.getProperties()
                        .values()
                        .stream()
                        .mapToInt(List::size)
                        .sum();

        Scanner scanner =
                new Scanner(
                        new StringReader("999\n-1\n")
                );

        BuyProductAction action =
                new BuyProductAction(
                        Continent.AFRICA,
                        null,
                        scanner
                );

        action.execute(player);

        int propertiesAfter =
                player.getProperties()
                        .values()
                        .stream()
                        .mapToInt(List::size)
                        .sum();

        assertEquals(
                propertiesBefore,
                propertiesAfter
        );
    }

    @Test
    @DisplayName("Le joueur ne peut pas acheter plus de 6 produits")
    void executeStopsAfterSixPurchases() {

        Scanner scanner =
                new Scanner(
                        new StringReader(
                                "0\n0\n0\n0\n0\n0\n0\n"
                        )
                );

        BuyProductAction action =
                new BuyProductAction(
                        null,
                        null,
                        scanner
                );

        action.execute(player);

        int ownedProducts =
                player.getProperties()
                        .values()
                        .stream()
                        .mapToInt(List::size)
                        .sum();

        assertEquals(6, ownedProducts);
    }
}
