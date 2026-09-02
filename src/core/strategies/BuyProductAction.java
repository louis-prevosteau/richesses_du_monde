package core.strategies;

import core.commands.BuyProductCommand;
import core.enums.Continent;
import core.enums.Region;
import core.manager.GameManager;
import core.models.Player;
import core.products.IProduct;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class BuyProductAction implements ISquareAction {

    private final Continent continent;
    private final Region region;
    private final Scanner scanner;
    private static final Logger logger = Logger.getLogger(BuyProductAction.class.getName());

    public BuyProductAction(
            Continent continent,
            Region region
    ) {
        this(
                continent,
                region,
                new Scanner(System.in)
        );
    }

    public BuyProductAction(
            Continent continent,
            Region region,
            Scanner scanner
    ) {
        this.continent = continent;
        this.region = region;
        this.scanner = scanner;
    }

    @Override
    public String getDescription() {
        return "Vous pouvez acheter des produits.";
    }

    @Override
    public void execute(Player player) {
        if (!player.getState().canBuy()) {
            logger.severe(
                    player.getName()
                            + " ne peut pas acheter."
            );
            return;
        }
        else logger.info(getDescription());

        int purchasesThisTurn = 0;

        while (purchasesThisTurn < 6) {
            List<IProduct> availableProducts = getAvailableProducts();
            if (availableProducts.isEmpty()) {
                logger.info(
                        "Aucun produit disponible."
                );
                return;
            }

            int response = getPlayerChoice(availableProducts);

            if (response == -1) {
                logger.info(
                        player.getName()
                                + " arrête ses achats."
                );
                return;
            }

            if (
                    response < 0
                            || response >= availableProducts.size()
            ) {
                logger.info("Choix invalide.");
                continue;
            }

            IProduct product = availableProducts.get(response);
            GameManager.getInstance()
                    .getShop()
                    .removeProduct(product);
            GameManager.getInstance()
                    .getInvoker()
                    .executeCommand(
                            new BuyProductCommand(
                                    player,
                                    product
                            )
                    );

            purchasesThisTurn++;

            GameManager
                    .getInstance()
                    .notifyPlayerBought(player, product);

            logger.info(
                    "Achats ce tour : "
                            + purchasesThisTurn
                            + "/6"
            );
        }

        logger.warning(
                "Limite de 6 achats atteinte pour ce tour."
        );
    }

    public List<IProduct> getAvailableProducts() {

        Map<Region, List<IProduct>> products;

        if (region != null) {

            products = GameManager.getInstance()
                    .getShop()
                    .getProducts(
                            null,
                            region
                    );

        } else if (continent != null) {

            products = GameManager.getInstance()
                    .getShop()
                    .getProducts(
                            continent,
                            null
                    );

        } else {

            products = GameManager.getInstance()
                    .getShop()
                    .getProducts(
                            null,
                            null
                    );
        }

        return products.values()
                .stream()
                .flatMap(List::stream)
                .toList();
    }

    private int getPlayerChoice(
            List<IProduct> products
    ) {

        logger.info(
                "\nProduits disponibles :"
        );

        for (
                int i = 0;
                i < products.size();
                i++
        ) {

            IProduct product =
                    products.get(i);

            logger.info(
                    i
                            + " - "
                            + product.getResource()
                            + " | "
                            + product.getPercentage()
                            + "%"
                            + " | "
                            + product.getRegion()
                            + " | Prix : "
                            + product.getPrice()
                            + " €"
            );
        }

        logger.info(
                "-1 - Terminer les achats"
        );

        logger.info("> ");

        try {

            return Integer.parseInt(
                    scanner.nextLine()
            );

        } catch (
                NumberFormatException e
        ) {

            logger.info(
                    "Veuillez saisir un nombre."
            );

            return -2;
        }
    }

    public Continent getContinent() {
        return continent;
    }

    public Region getRegion() {
        return region;
    }
}
