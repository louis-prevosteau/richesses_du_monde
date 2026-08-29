package test.observers;

import core.cards.ICard;
import core.cards.PayCard;
import core.enums.CardType;
import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.manager.GameManager;
import core.models.ISquare;
import core.models.Player;
import core.observers.IGameObserver;
import core.observers.ScoreboardObserver;
import core.products.IProduct;
import core.products.Product;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameObserverTest {

    private GameManager gameManager;
    private TestObserver testObserver;

    @BeforeEach
    void setUp() {
        GameManager.getInstance().reset();
        gameManager = GameManager.getInstance();
        testObserver = new TestObserver();
    }

    @Test
    @DisplayName("Devrait pouvoir ajouter un observateur")
    void testAddObserver() {
        assertDoesNotThrow(() -> gameManager.addObserver(testObserver));
    }

    @Test
    @DisplayName("Devrait notifier quand un produit est achetée")
    void testNotifyPropertyBought() {
        gameManager.addObserver(testObserver);

        Player player = new Player("Alice");
        IProduct product = new Product(Resource.PETROLE, 15, 42, Continent.ASIA_OCEANIA, Region.MOYEN_ORIENT, "Qatar");

        gameManager.notifyPlayerBought(player, product);

        assertTrue(testObserver.productBoughtCalled);
        assertSame(player, testObserver.lastPlayer);
        assertSame(product, testObserver.lastProduct);
    }

    @Test
    @DisplayName("Devrait notifier quand un joueur se déplace")
    void testNotifyPlayerMoved() {
        gameManager.addObserver(testObserver);

        Player player = new Player("Bob");
        int position = 15;

        gameManager.notifyPlayerMoved(player, position);

        assertTrue(testObserver.playerMovedCalled);
        assertEquals(position, testObserver.lastPosition);
    }

    @Test
    @DisplayName("Devrait notifier quand un joueur fait faillite")
    void testNotifyPlayerBankrupt() {
        gameManager.addObserver(testObserver);
        gameManager.addPlayer(new Player("Alice"));
        gameManager.addPlayer(new Player("Bob"));

        Player bankruptPlayer = new Player("Charlie");
        gameManager.addPlayer(bankruptPlayer);
        gameManager.startGame();

        gameManager.notifyPlayerBankrupt(bankruptPlayer);

        assertTrue(testObserver.playerBankruptCalled);
    }

    @Test
    @DisplayName("Devrait notifier quand un joueur commence son tour")
    void tsNotifyPlayerTurnStarted() {
        gameManager.addObserver(testObserver);
        Player alice = new Player("Alice");
        gameManager.addPlayer(alice);
        gameManager.addPlayer(new Player("Bob"));
        gameManager.startGame();
        gameManager.notifyTurnStarted(alice);
        assertTrue(testObserver.turnStartedCalled);
    }

    @Test
    @DisplayName("Devrait notifier quand un joueur pioche une carte")
    void testNotifyCardDrawn() {
        gameManager.addObserver(testObserver);
        Player alice = new Player("Alice");
        ICard card = new PayCard("test", CardType.NEWS, new int[] {1000000}, null);
        gameManager.addPlayer(alice);
        gameManager.addPlayer(new Player("Bob"));
        gameManager.startGame();
        gameManager.notifyCardDrawn(alice, card);
        assertTrue(testObserver.cardDrawnCalled);
    }

    @Test
    @DisplayName("Devrait notifier quand un joueur vend un produit")
    void testNotifyProductSold() {
        gameManager.addObserver(testObserver);
        Player alice = new Player("Alice");
        Player bob = new Player("Bob");
        IProduct product = new Product(
                Resource.PETROLE,
                10,
                1_000_000,
                Continent.ASIA_OCEANIA,
                Region.MOYEN_ORIENT,
                "Qatar"
        );
        gameManager.addPlayer(alice);
        gameManager.addPlayer(bob);
        gameManager.startGame();
        gameManager.notifyPlayerSold(alice, bob, product.getResource(), 1, product.getPrice());
        assertTrue(testObserver.productSoldCalled);
    }

    @Test
    @DisplayName("Devrait notifier quand un joueur utilise une carte joker")
    void testNotifyJokerUsed() {
        gameManager.addObserver(testObserver);
        Player alice = new Player("Alice");
        gameManager.addPlayer(alice);
        gameManager.addPlayer(new Player("Bob"));
        gameManager.startGame();
        gameManager.notifyJokerUsed(alice);
        assertTrue(testObserver.jokerUsedCalled);
    }

    @Test
    @DisplayName("Plusieurs observateurs devraient tous être notifiés")
    void testMultipleObservers() {
        TestObserver observer1 = new TestObserver();
        TestObserver observer2 = new TestObserver();

        gameManager.addObserver(observer1);
        gameManager.addObserver(observer2);

        Player player = new Player("Alice");
        IProduct product = new Product(Resource.PETROLE, 15, 42, Continent.ASIA_OCEANIA, Region.MOYEN_ORIENT, "Qatar");

        gameManager.notifyPlayerBought(player, product);

        assertTrue(observer1.productBoughtCalled);
        assertTrue(observer2.productBoughtCalled);
    }

    @Test
    @DisplayName("Devrait pouvoir retirer un observateur")
    void testRemoveObserver() {
        gameManager.addObserver(testObserver);
        gameManager.removeObserver(testObserver);

        Player player = new Player("Alice");
        IProduct product = new Product(Resource.PETROLE, 15, 42, Continent.ASIA_OCEANIA, Region.MOYEN_ORIENT, "Qatar");

        gameManager.notifyPlayerBought(player, product);

        assertFalse(testObserver.productBoughtCalled);
    }

    @Test
    @DisplayName("ScoreboardObserver devrait compiler sans erreur")
    void testScoreboardObserverExists() {
        IGameObserver scoreboard = new ScoreboardObserver();

        assertNotNull(scoreboard);

        assertDoesNotThrow(() -> {
            Player player = new Player("Test");
            IProduct product = new Product(Resource.PETROLE, 15, 42, Continent.ASIA_OCEANIA, Region.MOYEN_ORIENT, "Qatar");

            scoreboard.onPlayerMoved(player, 5);
            scoreboard.onProductBought(player, product);
            scoreboard.onPlayerBankrupt(player);
            scoreboard.onGameStarted();
            scoreboard.onGameOver(player);
        });
    }

    private static class TestObserver implements IGameObserver {

        boolean playerMovedCalled = false;
        boolean productBoughtCalled = false;
        boolean playerBankruptCalled = false;
        boolean gameStartedCalled = false;
        boolean gameOverCalled = false;
        boolean turnStartedCalled = false;
        boolean productSoldCalled = false;
        boolean cardDrawnCalled = false;
        boolean jokerUsedCalled = false;

        Player lastPlayer = null;
        IProduct lastProduct = null;
        ICard lastCard = null;
        int lastPosition = -1;
        int lastPrice = 0;
        int lastProductsSize = 0;
        Resource lastResource = null;

        @Override
        public void onPlayerMoved(Player player, int position) {
            playerMovedCalled = true;
            lastPlayer = player;
            lastPosition = position;
        }

        @Override
        public void onProductBought(Player player, IProduct product) {
            productBoughtCalled = true;
            lastPlayer = player;
            lastProduct = product;
        }

        @Override
        public void onPlayerBankrupt(Player player) {
            playerBankruptCalled = true;
            lastPlayer = player;
        }

        @Override
        public void onGameStarted() {
            gameStartedCalled = true;
        }

        @Override
        public void onGameOver(Player winner) {
            gameOverCalled = true;
        }

        @Override
        public void onPlayerTurnStarted(Player player) {
            lastPlayer = player;
            turnStartedCalled = true;
        }

        @Override
        public void onProductSold(Player seller, Player buyer, Resource resource, int productsSize, int price) {
            productSoldCalled = true;
            lastPlayer = seller;
            lastPrice = price;
            lastResource = resource;
            lastProductsSize = productsSize;
        }

        @Override
        public void onCardDrawn(Player player, ICard card) {
            lastCard = card;
            lastPlayer = player;
            cardDrawnCalled = true;
        }

        @Override
        public void onJokerUsed(Player player) {
            lastPlayer = player;
            jokerUsedCalled = true;
        }
    }
}
