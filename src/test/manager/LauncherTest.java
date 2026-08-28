package test.manager;

import core.manager.GameManager;
import core.manager.Launcher;
import core.models.Player;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class LauncherTest {

    @BeforeEach
    void setUp() {
        GameManager.getInstance().reset();
    }

    @AfterEach
    void tearDown() {
        GameManager.getInstance().reset();
    }

    @Test
    @DisplayName("start(false) doit créer les joueurs et démarrer la partie")
    void testStart() {

        String input =
                "2\n" +
                        "Alice\n" +
                        "Bob\n";

        Launcher.setScanner(
                new Scanner(
                        new ByteArrayInputStream(
                                input.getBytes(StandardCharsets.UTF_8)
                        )
                )
        );

        assertDoesNotThrow(
                () -> Launcher.start(false)
        );

        assertEquals(
                2,
                GameManager.getInstance()
                        .getPlayers()
                        .size()
        );

        assertEquals(
                "Alice",
                GameManager.getInstance()
                        .getPlayers()
                        .get(0)
                        .getName()
        );

        assertEquals(
                "Bob",
                GameManager.getInstance()
                        .getPlayers()
                        .get(1)
                        .getName()
        );
    }

    @Test
    @DisplayName("start(false) avec un seul joueur doit lever une exception")
    void testStartWithOnePlayer() {

        String input =
                "1\n" +
                        "Alice\n";

        Launcher.setScanner(
                new Scanner(
                        new ByteArrayInputStream(
                                input.getBytes(StandardCharsets.UTF_8)
                        )
                )
        );

        assertThrows(
                IllegalStateException.class,
                () -> Launcher.start(false)
        );
    }

    @Test
    @DisplayName("start(false) avec zéro joueur doit lever une exception")
    void testStartWithZeroPlayer() {

        String input = "0\n";

        Launcher.setScanner(
                new Scanner(
                        new ByteArrayInputStream(
                                input.getBytes(StandardCharsets.UTF_8)
                        )
                )
        );

        assertThrows(
                IllegalStateException.class,
                () -> Launcher.start(false)
        );
    }

    @Test
    @DisplayName("Le premier joueur créé devient le joueur courant")
    void testCurrentPlayerAfterStart() {

        String input =
                "2\n" +
                        "Alice\n" +
                        "Bob\n";

        Launcher.setScanner(
                new Scanner(
                        new ByteArrayInputStream(
                                input.getBytes(StandardCharsets.UTF_8)
                        )
                )
        );

        Launcher.start(false);

        assertEquals(
                "Alice",
                GameManager.getInstance()
                        .getCurrentPlayer()
                        .getName()
        );
    }

    @Test
    @DisplayName("nextPlayer() fonctionne après initialisation via Launcher")
    void testNextPlayerAfterStart() {

        String input =
                "2\n" +
                        "Alice\n" +
                        "Bob\n";

        Launcher.setScanner(
                new Scanner(
                        new ByteArrayInputStream(
                                input.getBytes(StandardCharsets.UTF_8)
                        )
                )
        );

        Launcher.start(false);

        GameManager.getInstance().nextPlayer();

        assertEquals(
                "Bob",
                GameManager.getInstance()
                        .getCurrentPlayer()
                        .getName()
        );
    }

    @Test
    @DisplayName("start(false) ne doit pas lancer la boucle de jeu")
    void testStartWithoutGameLoop() {

        String input =
                "2\n" +
                        "Alice\n" +
                        "Bob\n";

        Launcher.setScanner(
                new Scanner(
                        new ByteArrayInputStream(
                                input.getBytes(StandardCharsets.UTF_8)
                        )
                )
        );

        assertDoesNotThrow(
                () -> Launcher.start(false)
        );
    }

    @Test
    @DisplayName("Les joueurs sont correctement ajoutés au GameManager")
    void testPlayersAddedToManager() {

        String input =
                "3\n" +
                        "Alice\n" +
                        "Bob\n" +
                        "Charlie\n";

        Launcher.setScanner(
                new Scanner(
                        new ByteArrayInputStream(
                                input.getBytes(StandardCharsets.UTF_8)
                        )
                )
        );

        Launcher.start(false);

        assertEquals(
                3,
                GameManager.getInstance()
                        .getPlayers()
                        .size()
        );

        assertTrue(
                GameManager.getInstance()
                        .getPlayers()
                        .stream()
                        .map(Player::getName)
                        .toList()
                        .containsAll(
                                List.of(
                                        "Alice",
                                        "Bob",
                                        "Charlie"
                                )
                        )
        );
    }

    @Test
    @DisplayName("playTurn() ignore un choix invalide")
    void testPlayTurnInvalidChoice() {

        GameManager.getInstance().addPlayer(new Player("Alice"));
        GameManager.getInstance().addPlayer(new Player("Bob"));
        GameManager.getInstance().startGame();

        Launcher.setScanner(
                new Scanner(
                        new ByteArrayInputStream(
                                "99\n".getBytes(StandardCharsets.UTF_8)
                        )
                )
        );

        Player current =
                GameManager.getInstance()
                        .getCurrentPlayer();

        assertDoesNotThrow(
                Launcher::playTurn
        );

        assertEquals(
                current,
                GameManager.getInstance()
                        .getCurrentPlayer()
        );
    }

    @Test
    @DisplayName("playTurn() permet de passer au joueur suivant")
    void testPlayTurnNextPlayer() {

        GameManager.getInstance().addPlayer(new Player("Alice"));
        GameManager.getInstance().addPlayer(new Player("Bob"));
        GameManager.getInstance().startGame();

        Launcher.setScanner(
                new Scanner(
                        new ByteArrayInputStream(
                                "1\n".getBytes(StandardCharsets.UTF_8)
                        )
                )
        );

        Launcher.playTurn();

        assertEquals(
                "Bob",
                GameManager.getInstance()
                        .getCurrentPlayer()
                        .getName()
        );
    }

    @Test
    @DisplayName("buildActions() retourne les 3 actions attendues")
    void testBuildActions() throws Exception {

        Player player = new Player("Alice");

        Method method =
                Launcher.class.getDeclaredMethod(
                        "buildActions",
                        Player.class
                );

        method.setAccessible(true);

        Object result =
                method.invoke(
                        null,
                        player
                );

        assertInstanceOf(
                List.class,
                result
        );

        List<?> actions =
                (List<?>) result;

        assertEquals(
                3,
                actions.size()
        );
    }
}