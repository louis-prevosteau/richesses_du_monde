package core.cards;

import core.commands.AddJokerCardCommand;
import core.commands.PayCommand;
import core.enums.CardType;
import core.manager.GameManager;
import core.models.Player;

import java.util.Scanner;
import java.util.logging.Logger;

public class JokerCard extends Card {

    private final Scanner scanner;
    private static final Logger logger = Logger.getLogger(JokerCard.class.getName());

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
        logger.info(getDescription());
        logger.info(player.getName() + ", souhaitez-vous prendre une carte joker pour 3 000 000 € ? (1 : Oui, 0 : Non)");
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
            logger.info(player.getName() + " prends une carte joker.");
        }
        else
            logger.info(player.getName() + " ne prend pas de carte joker.");
    }
}
