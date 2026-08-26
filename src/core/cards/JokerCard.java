package core.cards;

import core.commands.AddJokerCardCommand;
import core.commands.PayCommand;
import core.enums.CardType;
import core.manager.GameManager;
import core.models.Player;

import java.util.Scanner;

public class JokerCard extends Card {

    private final Scanner scanner;

    public JokerCard(String description, CardType type) {
        super(description, type);
        this.scanner = new Scanner(System.in);
    }

    public JokerCard(
            String description,
            CardType type,
            Scanner scanner
    ) {
        super(description, type);
        this.scanner = scanner;
    }

    @Override
    public void executeEffect(Player player) {
        System.out.println(getDescription());
        System.out.println(player.getName() + ", souhaitez-vous prendre une carte joker pour 3 000 000 € ? (1 : Oui, 0 : Non)");
        int choice = scanner.nextInt();
        if (choice == 1) {
            GameManager
                    .getInstance()
                    .getInvoker()
                    .executeCommand(new PayCommand(player, 3000000));
            GameManager
                    .getInstance()
                    .getInvoker()
                    .executeCommand(new AddJokerCardCommand(player, this));
        }
        else
            System.out.println(player.getName() + " ne prend pas de carte joker.");
    }
}
