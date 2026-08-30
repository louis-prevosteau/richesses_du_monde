package core.manager;

import core.enums.GameState;
import core.enums.Resource;
import core.models.Player;
import core.observers.ScoreboardObserver;
import core.products.IProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class Launcher {

    private static final GameManager manager = GameManager.getInstance();
    private static Scanner scanner = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(Launcher.class.getName());

    public static void setScanner(Scanner scanner) {
        Launcher.scanner = scanner;
    }

    private record Action(String label, Runnable run) {}

    public static void start(boolean runLoop) {
        initGame();
        if (runLoop) gameLoop();
    }

    private static void gameLoop() {
        while (manager.getCurrentState() != GameState.OVER) {
            playTurn();
        }
        logger.info("Partie terminée");
    }

    public static void playTurn() {
        Player player = manager.getCurrentPlayer();

        GameManager
                .getInstance()
                .notifyTurnStarted(player);

        List<Action> actions = buildActions(player);

        showActions(actions);

        int choice = Integer.parseInt(scanner.nextLine());

        if (choice < 0 || choice >= actions.size()) {
            return;
        }

        actions.get(choice).run().run();
    }

    private static void showActions(List<Action> actions) {
        logger.info("\nActions possibles :");

        for (int i = 0; i < actions.size(); i++) {
            logger.info(i + " - " + actions.get(i).label());
        }

        logger.info("> ");
    }

    private static List<Action> buildActions(Player player) {
        List<Action> actions = new ArrayList<>();
        if (!player.hasPlayed())
            actions.add(new Action("Lancer les dés",
                    () -> {
                        player.getState().takeTurn(player);
                        player.setHasPlayed(true);
                        manager.getBoard()
                                .getSquares()
                                .get(player.getPosition())
                                .landOn(player);
                    }));
        else
            actions.add(new Action("Joueur suivant", () -> {
            player.setHasPlayed(false);
            manager.nextPlayer();
        }));
        actions.add(new Action("Voir propriétés", () -> showProperties(player)));
        actions.add(new Action("Voir l'historique", () -> manager.getInvoker().showHistory()));
        return actions;
    }

    private static void showProperties(Player player) {
        logger.info("\nPropriétés :");
        for (Map.Entry<Resource, List<IProduct>> entry : player.getProperties().entrySet()) {
            logger.info("- " + entry.getValue());
        }
    }

    private static void initGame() {
        logger.fine("======= RICHESSES DU MONDE" +
                " ========");
        logger.info("Nombre de joueurs : ");
        int nbPlayers = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0 ; i < nbPlayers ; i++) {
            logger.info("Nom du joueur " + (i + 1) + " : ");
            String name = scanner.nextLine();
            manager.addPlayer(new Player(name));
        }
        manager.addObserver(new ScoreboardObserver());
        manager.startGame();
    }
}
