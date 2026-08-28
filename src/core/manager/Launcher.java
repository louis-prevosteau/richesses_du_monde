package core.manager;

import core.commands.ICommand;
import core.enums.GameState;
import core.enums.Resource;
import core.models.Player;
import core.products.IProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Launcher {

    private static final GameManager manager = GameManager.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void setScanner(Scanner scanner) {
        Launcher.scanner = scanner;
    }

    private record Action(String label, Runnable run) {}

    private interface CommandFactory {
        ICommand create(IProduct product);
    }

    public static void start(boolean runLoop) {
        initGame();
        if (runLoop) gameLoop();
    }

    private static void gameLoop() {
        while (manager.getCurrentState() != GameState.OVER) {
            playTurn();
        }
        System.out.println("Partie terminée");
    }

    public static void playTurn() {
        Player player = manager.getCurrentPlayer();

        List<Action> actions = buildActions(player);

        showActions(actions);

        int choice = Integer.parseInt(scanner.nextLine());

        if (choice < 0 || choice >= actions.size()) {
            return;
        }

        actions.get(choice).run().run();
    }

    private static void showActions(List<Action> actions) {
        System.out.println("\nActions possibles :");

        for (int i = 0; i < actions.size(); i++) {
            System.out.println(i + " - " + actions.get(i).label());
        }

        System.out.print("> ");
    }

    private static List<Action> buildActions(Player player) {
        List<Action> actions = new ArrayList<>();
        actions.add(new Action("Lancer les dés",
                () -> {
                    player.getState().takeTurn(player);

                    System.out.println("Nouvelle position : " +
                            manager.getBoard().getSquare(player.getPosition()).getName());

                    manager.getBoard()
                            .getSquares()
                            .get(player.getPosition())
                            .landOn(player);
                }));
        actions.add(new Action("Joueur suivant", () -> manager.nextPlayer()));
        actions.add(new Action("Voir propriétés", () -> showProperties(player)));
        return actions;
    }

    private static void showProperties(Player player) {
        System.out.println("\nPropriétés :");
        for (Map.Entry<Resource, List<IProduct>> entry : player.getProperties().entrySet()) {
            System.out.println("- " + entry.getValue());
        }
    }

    private static void initGame() {
        System.out.println("======= RICHESSES DU MONDE" +
                " ========");
        System.out.print("Nombre de joueurs : ");
        int nbPlayers = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0 ; i < nbPlayers ; i++) {
            System.out.print("Nom du joueur " + (i + 1) + " : ");
            String name = scanner.nextLine();
            manager.addPlayer(new Player(name));
        }
        manager.startGame();
    }
}
