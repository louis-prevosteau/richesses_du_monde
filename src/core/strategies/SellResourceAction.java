package core.strategies;

import core.commands.SellResourceCommand;
import core.commands.UseJokerCommand;
import core.manager.GameManager;
import core.models.Player;
import core.products.IProduct;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class SellResourceAction implements ISquareAction {

    private final Scanner scanner;
    private static final Logger logger = Logger.getLogger(SellResourceAction.class.getName());

    public SellResourceAction() {
        this(new Scanner(System.in));
    }

    public SellResourceAction(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getDescription() {
        return "Vente aux enchères";
    }

    @Override
    public void execute(Player player) {
        if (!player.getJokers().isEmpty()) {
            GameManager.getInstance()
                    .getInvoker()
                    .executeCommand(new UseJokerCommand(player));
            GameManager
                    .getInstance()
                    .notifyJokerUsed(player);
            return;
        }

        List<AuctionLot> lots = getLots(player);

        if (lots.isEmpty()) {
            logger.info("Aucune ressource à vendre.");
            return;
        }

        logger.info("\n=== VENTE AUX ENCHÈRES ===");
        logger.info("Choisissez la ressource à vendre :");

        showLots(lots);

        int choice = getPlayerChoice(lots.size());

        if (choice == -1) {
            logger.warning("Vente annulée.");
            return;
        }

        AuctionLot selectedLot = lots.get(choice);

        GameManager.getInstance()
                .getInvoker()
                .executeCommand(
                        new SellResourceCommand(
                                player,
                                selectedLot.products(),
                                selectedLot.startingPrice()
                        )
                );

        logger.info(
                selectedLot.products().size()
                        + " produit(s) "
                        + selectedLot.resource()
                        + " mis en vente pour "
                        + selectedLot.startingPrice()
                        + " €"
        );
    }

    private int getPlayerChoice(int maxChoice) {
        while (true) {
            logger.info(
                    "Choisissez un lot à vendre (0-"
                            + (maxChoice - 1)
                            + ", -1 pour annuler) : "
            );

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == -1) {
                    return -1;
                }

                if (choice >= 0 && choice < maxChoice) {
                    return choice;
                }

                logger.info("Choix invalide.");

            } catch (NumberFormatException e) {
                logger.info("Veuillez saisir un nombre.");
            }
        }
    }

    private void showLots(List<AuctionLot> lots) {
        for (int i = 0; i < lots.size(); i++) {
            AuctionLot lot = lots.get(i);
            logger.info(
                    i + " - " + lot.resource() + " (" + lot.products().size() + " produits) - Mise à prix : %, " + lot.startingPrice() + " €"
            );
        }
    }

    private List<AuctionLot> getLots(Player player) {
        return player.getProperties().entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .map(entry -> {
                    int totalPrice = entry.getValue()
                            .stream()
                            .mapToInt(IProduct::getPrice)
                            .sum();
                    return new AuctionLot(
                            entry.getKey(),
                            entry.getValue(),
                            totalPrice / 2
                    );
                })
                .toList();
    }
}
