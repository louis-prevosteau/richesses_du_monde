package core.commands;

import core.manager.GameManager;
import core.models.Player;
import core.products.IProduct;

import java.util.List;
import java.util.Scanner;

public class SellResourceCommand implements ICommand {

    private final Player seller;
    private final List<IProduct> products;
    private final int startingPrice;

    public SellResourceCommand(
            Player seller,
            List<IProduct> products,
            int startingPrice
    ) {
        this.seller = seller;
        this.products = products;
        this.startingPrice = startingPrice;
    }

    @Override
    public String getDescription() {
        return "Vente aux enchères de "
                + products.size()
                + " produit(s)";
    }

    @Override
    public void execute() {
        List<Player> players =
                GameManager.getInstance().getPlayers();
        int currentPrice = startingPrice;
        Player highestBidder = null;
        Scanner scanner = new Scanner(System.in);
        boolean bidPlaced;

        do {
            bidPlaced = false;
            for (Player player : players) {
                if (player.equals(seller)) {
                    continue;
                }
                System.out.println(
                        "\n" + player.getName()
                                + ", voulez-vous enchérir ?"
                );
                System.out.println(
                        "Prix actuel : " + currentPrice + " €"
                );
                System.out.print(
                        "Montant (> "
                                + currentPrice
                                + ", 0 pour passer) : "
                );

                int bid;

                try {
                    bid = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    bid = 0;
                }

                if (bid > currentPrice && player.getMoney() >= bid) {
                    currentPrice = bid;
                    highestBidder = player;
                    bidPlaced = true;

                    System.out.println(
                            player.getName()
                                    + " enchérit à "
                                    + bid
                                    + " €"
                    );
                }
            }
        } while (bidPlaced);

        if (highestBidder == null) {
            System.out.println(
                    "Aucune enchère. Les produits retournent au magasin."
            );
            products.forEach(
                    p -> GameManager.getInstance()
                            .getShop()
                            .returnsProducts(p)
            );
            products.forEach(seller::removeProperty);
            return;
        }

        highestBidder.pay(currentPrice);
        seller.receive(currentPrice);

        for (IProduct product : products) {
            seller.removeProperty(product);
            highestBidder.addProperty(product);
        }

        System.out.println(
                "\n"
                        + highestBidder.getName()
                        + " remporte l'enchère pour "
                        + currentPrice
                        + " €"
        );
    }

    @Override
    public boolean canExecute() {
        return !products.isEmpty();
    }
}