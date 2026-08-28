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

public class BuyProductAction implements ISquareAction {

    private final Continent continent;
    private final Region region;
    private final Scanner scanner;

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
            System.out.println(
                    player.getName()
                            + " ne peut pas acheter."
            );
            return;
        }
        else System.out.println(getDescription());

        int purchasesThisTurn = 0;

        while (purchasesThisTurn < 6) {

            List<IProduct> availableProducts =
                    getAvailableProducts();

            if (availableProducts.isEmpty()) {
                System.out.println(
                        "Aucun produit disponible."
                );
                return;
            }

            int response =
                    getPlayerChoice(availableProducts);

            if (response == -1) {
                System.out.println(
                        player.getName()
                                + " arrête ses achats."
                );
                return;
            }

            if (
                    response < 0
                            || response >= availableProducts.size()
            ) {
                System.out.println("Choix invalide.");
                continue;
            }

            IProduct product =
                    availableProducts.get(response);

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

            System.out.println(
                    player.getName()
                            + " achète "
                            + product.getResource()
                            + " ("
                            + product.getPercentage()
                            + "%) pour "
                            + product.getPrice()
                            + " €"
            );

            System.out.println(
                    "Achats ce tour : "
                            + purchasesThisTurn
                            + "/6"
            );
        }

        System.out.println(
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

        System.out.println(
                "\nProduits disponibles :"
        );

        for (
                int i = 0;
                i < products.size();
                i++
        ) {

            IProduct product =
                    products.get(i);

            System.out.println(
                    i
                            + " - "
                            + product.getResource()
                            + " | "
                            + product.getPercentage()
                            + "%"
                            + " | Prix : "
                            + product.getPrice()
                            + " €"
            );
        }

        System.out.println(
                "-1 - Terminer les achats"
        );

        System.out.print("> ");

        try {

            return Integer.parseInt(
                    scanner.nextLine()
            );

        } catch (
                NumberFormatException e
        ) {

            System.out.println(
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
