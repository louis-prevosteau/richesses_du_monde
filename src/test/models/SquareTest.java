package test.models;

import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.manager.GameManager;
import core.models.GoSquare;
import core.models.ISquare;
import core.models.Player;
import core.models.ProductSquare;
import core.products.IProduct;
import core.products.Product;
import core.strategies.ISquareAction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class SquareTest {

    private Player player;
    private Player owner;
    private ISquare square;

    private static class DummyAction implements ISquareAction {

        @Override
        public String getDescription() {
            return "Dummy";
        }

        @Override
        public void execute(Player player) {
            // Ne rien faire
        }
    }

    @BeforeEach
    void setUp() {
        GameManager.getInstance().getPlayers().clear();

        player = new Player("Alice");
        owner = new Player("Bob");

        GameManager.getInstance().addPlayer(player);
        GameManager.getInstance().addPlayer(owner);
    }

    @Test
    @DisplayName("GoSquare : le joueur est sur la case départ")
    void testGoSquare() {
        square = new GoSquare();

        square.landOn(player);

        assertEquals(square.getPosition(), player.getPosition());
        assertTrue(square.getName().contains("Départ"));
    }

    @Test
    @DisplayName("Aucune royalty n'est payée si personne ne possède 30%")
    void testNoRoyaltyIfNoOwnerHas30Percent() {

        square = new ProductSquare(
                "Maghreb",
                1,
                Resource.PETROLE,
                new DummyAction()
        );

        owner.addProperty(
                new Product(
                        Resource.PETROLE,
                        20,
                        1_000_000,
                        Continent.AFRICA,
                        Region.MAGHREB,
                        "Algérie"
                )
        );

        int moneyBefore = player.getMoney();

        square.landOn(player);

        assertEquals(moneyBefore, player.getMoney());
    }

    @Test
    @DisplayName("Le joueur paie des royalties si un autre joueur possède au moins 30%")
    void testRoyaltyPaidWhenAnotherPlayerOwns30Percent() {

        square = new ProductSquare(
                "Maghreb",
                1,
                Resource.PETROLE,
                new DummyAction()
        );

        owner.addProperty(
                new Product(
                        Resource.PETROLE,
                        35,
                        1_000_000,
                        Continent.AFRICA,
                        Region.MAGHREB,
                        "Algérie"
                )
        );

        int royalties = Resource.PETROLE.getRapportBase();

        int playerMoneyBefore = player.getMoney();
        int ownerMoneyBefore = owner.getMoney();

        square.landOn(player);

        assertEquals(
                playerMoneyBefore - royalties,
                player.getMoney()
        );

        assertEquals(
                ownerMoneyBefore + royalties,
                owner.getMoney()
        );
    }

    @Test
    @DisplayName("Le joueur ne paie pas de royalties à lui-même")
    void testNoRoyaltyPaidToSelf() {

        square = new ProductSquare(
                "Maghreb",
                1,
                Resource.PETROLE,
                new DummyAction()
        );

        player.addProperty(
                new Product(
                        Resource.PETROLE,
                        40,
                        1_000_000,
                        Continent.AFRICA,
                        Region.MAGHREB,
                        "Algérie"
                )
        );

        int moneyBefore = player.getMoney();

        square.landOn(player);

        assertEquals(moneyBefore, player.getMoney());
    }

    @Test
    @DisplayName("Les royalties sont dues dès 30% exactement")
    void testRoyaltyAtThirtyPercent() {

        square = new ProductSquare(
                "Maghreb",
                1,
                Resource.PETROLE,
                new DummyAction()
        );

        owner.addProperty(
                new Product(
                        Resource.PETROLE,
                        30,
                        1_000_000,
                        Continent.AFRICA,
                        Region.MAGHREB,
                        "Algérie"
                )
        );

        int royalties = Resource.PETROLE.getRapportBase();

        int playerMoneyBefore = player.getMoney();

        square.landOn(player);

        assertEquals(
                playerMoneyBefore - royalties,
                player.getMoney()
        );
    }

    @Test
    @DisplayName("Royalties x5 à partir de 50%")
    void testRoyaltyAtFiftyPercent() {

        square = new ProductSquare(
                "Maghreb",
                1,
                Resource.PETROLE,
                new DummyAction()
        );

        owner.addProperty(
                new Product(
                        Resource.PETROLE,
                        50,
                        1_000_000,
                        Continent.AFRICA,
                        Region.MAGHREB,
                        "Algérie"
                )
        );

        int royalties = Resource.PETROLE.getRapportBase() * 5;

        int playerMoneyBefore = player.getMoney();

        square.landOn(player);

        assertEquals(
                playerMoneyBefore - royalties,
                player.getMoney()
        );
    }

    @Test
    @DisplayName("Royalties x10 à partir de 70%")
    void testRoyaltyAtSeventyPercent() {

        square = new ProductSquare(
                "Maghreb",
                1,
                Resource.PETROLE,
                new DummyAction()
        );

        owner.addProperty(
                new Product(
                        Resource.PETROLE,
                        70,
                        1_000_000,
                        Continent.AFRICA,
                        Region.MAGHREB,
                        "Algérie"
                )
        );

        int royalties = Resource.PETROLE.getRapportBase() * 10;

        int playerMoneyBefore = player.getMoney();

        square.landOn(player);

        assertEquals(
                playerMoneyBefore - royalties,
                player.getMoney()
        );
    }

    @Test
    @DisplayName("Royalties x20 à partir de 90%")
    void testRoyaltyAtNinetyPercent() {

        square = new ProductSquare(
                "Maghreb",
                1,
                Resource.PETROLE,
                new DummyAction()
        );

        owner.addProperty(
                new Product(
                        Resource.PETROLE,
                        90,
                        1_000_000,
                        Continent.AFRICA,
                        Region.MAGHREB,
                        "Algérie"
                )
        );

        int royalties = Resource.PETROLE.getRapportBase() * 20;

        int playerMoneyBefore = player.getMoney();

        square.landOn(player);

        assertEquals(
                playerMoneyBefore - royalties,
                player.getMoney()
        );
    }
}
