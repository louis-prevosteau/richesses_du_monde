import core.commands.ICommand;
import core.manager.GameManager;
import core.models.Player;
import core.products.IProduct;

import java.util.Scanner;

public class Main {

    private final GameManager manager = GameManager.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    private record Action(String label, Runnable run) {}

    private interface CommandFactory {
        ICommand create(IProduct product);
    }

    public void start() {
        initGame();
        gameLoop();
    }

    private void gameLoop() {
    }

    private void initGame() {
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
