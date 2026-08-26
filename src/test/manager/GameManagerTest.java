package test.manager;

import core.enums.GameState;
import core.manager.GameManager;
import core.models.Player;
import core.observers.IGameObserver;
import core.products.IProduct;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameManagerTest {

    private GameManager manager;

    @BeforeEach
    void setUp() {
        manager = GameManager.getInstance();
        manager.reset();
    }

    @Test
    @DisplayName("getInstance() doit retourner la même instance")
    void testSingleton() {
        GameManager gm2 = GameManager.getInstance();
        assertSame(manager, gm2);
    }

    @Test
    @DisplayName("addPlayer() ajoute un joueur en état WAITING")
    void testAddPlayer() {
        Player p = new Player("Alice");
        manager.addPlayer(p);
        assertEquals(1, manager.getPlayers().size());
    }

    @Test
    @DisplayName("addPlayer() échoue si le jeu est lancé")
    void testAddPlayerFailsWhenStarted() {
        manager.addPlayer(new Player("A"));
        manager.addPlayer(new Player("B"));
        manager.startGame();

        assertThrows(IllegalStateException.class,
                () -> manager.addPlayer(new Player("C")));
    }

    @Test
    @DisplayName("startGame() échoue si moins de 2 joueurs")
    void testStartGameFails() {
        manager.addPlayer(new Player("A"));
        assertThrows(IllegalStateException.class,
                () -> manager.startGame());
    }

    @Test
    @DisplayName("startGame() passe l'état à PLAYING")
    void testStartGame() {
        manager.addPlayer(new Player("A"));
        manager.addPlayer(new Player("B"));

        manager.startGame();

        assertEquals(GameState.PLAYING, manager.getCurrentState());
    }

    @Test
    @DisplayName("nextPlayer() change le joueur courant")
    void testNextPlayer() {
        Player p1 = new Player("A");
        Player p2 = new Player("B");

        manager.addPlayer(p1);
        manager.addPlayer(p2);

        assertEquals(p1, manager.getCurrentPlayer());

        manager.nextPlayer();

        assertEquals(p2, manager.getCurrentPlayer());
    }

    @Test
    @DisplayName("nextPlayer() boucle sur les joueurs")
    void testNextPlayerLoop() {
        Player p1 = new Player("A");
        Player p2 = new Player("B");

        manager.addPlayer(p1);
        manager.addPlayer(p2);

        manager.nextPlayer();
        manager.nextPlayer();

        assertEquals(p1, manager.getCurrentPlayer());
    }

    @Test
    @DisplayName("notifyGameStarted() appelle les observers")
    void testObserverGameStarted() {
        TestObserver obs = new TestObserver();
        manager.addObserver(obs);

        manager.addPlayer(new Player("A"));
        manager.addPlayer(new Player("B"));

        manager.startGame();

        assertTrue(obs.gameStarted);
    }

    @Test
    @DisplayName("notifyPlayerMoved() appelle les observers")
    void testNotifyPlayerMoved() {
        TestObserver obs = new TestObserver();
        manager.addObserver(obs);

        Player p = new Player("A");
        manager.notifyPlayerMoved(p, 5);

        assertTrue(obs.playerMoved);
    }

    @Test
    @DisplayName("notifyPlayerBankrupt() retire le joueur")
    void testPlayerBankruptRemovesPlayer() {
        Player p1 = new Player("A");
        Player p2 = new Player("B");

        manager.addPlayer(p1);
        manager.addPlayer(p2);

        manager.notifyPlayerBankrupt(p1);

        assertEquals(1, manager.getPlayers().size());
    }

    @Test
    @DisplayName("notifyPlayerBankrupt() déclenche game over si 1 joueur restant")
    void testGameOver() {
        TestObserver obs = new TestObserver();
        manager.addObserver(obs);

        Player p1 = new Player("A");
        Player p2 = new Player("B");

        manager.addPlayer(p1);
        manager.addPlayer(p2);

        manager.startGame();

        manager.notifyPlayerBankrupt(p1);

        assertEquals(GameState.OVER, manager.getCurrentState());
        assertTrue(obs.gameOver);
    }

    @Test
    @DisplayName("reset() remet le jeu à zéro")
    void testReset() {
        manager.addPlayer(new Player("A"));
        manager.addObserver(new TestObserver());

        manager.reset();

        assertEquals(0, manager.getPlayers().size());
        assertEquals(GameState.WAITING, manager.getCurrentState());
    }

    private static class TestObserver implements IGameObserver {
        boolean gameStarted = false;
        boolean gameOver = false;
        boolean playerMoved = false;
        boolean productBought = false;
        boolean playerBankrupt = false;

        @Override
        public void onGameStarted() {
            gameStarted = true;
        }

        @Override
        public void onGameOver(Player winner) {
            gameOver = true;
        }

        @Override
        public void onPlayerMoved(Player player, int position) {
            playerMoved = true;
        }

        @Override
        public void onProductBought(Player player, IProduct product) {
            productBought = true;
        }

        @Override
        public void onPlayerBankrupt(Player player) {
            playerBankrupt = true;
        }
    }
}
