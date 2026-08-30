package core.commands;

import core.manager.GameManager;
import core.models.Player;
import core.products.IProduct;
import core.strategies.AuctionResult;

import java.util.List;
import java.util.Scanner;

public class SellResourceCommand implements ICommand {

    private final Player seller;
    private final List<IProduct> products;
    private final int startingPrice;
    private final Scanner scanner;

    public SellResourceCommand(
            Player seller,
            List<IProduct> products,
            int startingPrice
    ) {
        this(
                seller,
                products,
                startingPrice,
                new Scanner(System.in)
        );
    }

    public SellResourceCommand(
            Player seller,
            List<IProduct> products,
            int startingPrice,
            Scanner scanner
    ) {
        this.seller = seller;
        this.products = products;
        this.startingPrice = startingPrice;
        this.scanner = scanner;
    }

    @Override
    public String getDescription() {
        return "Vente aux enchères de "
                + products.size()
                + " produit(s)";
    }

    @Override
    public void execute() {
        AuctionResult result = runAction();

        if (result.highestBidder() == null) {
            returnProductsToShop();
            return;
        }
        transferProducts(result.highestBidder(), result.finalPrice());
    }

    private void transferProducts(Player player, int price) {
        player.pay(price);
        seller.receive(price);

        for (IProduct product : products) {
            seller.removeProperty(product);
            player.addProperty(product);
            product.setOwner(player);
        }

        GameManager
                .getInstance()
                .notifyPlayerSold(seller, player, products.getFirst().getResource(), products.size(), price);
    }

    private void returnProductsToShop() {
        System.out.println(
                "Aucune enchère. Les produits retournent au magasin."
        );

        for (IProduct product : products) {
            seller.removeProperty(product);
            product.setOwner(null);
            GameManager.getInstance()
                    .getShop()
                    .addProduct(product);
        }
    }

    private AuctionResult runAction() {
        List<Player> players =
                GameManager.getInstance().getPlayers();

        int currentPrice = startingPrice;
        Player highestBidder = null;
        boolean bidPlaced;

        do {

            bidPlaced = false;

            for (Player player : players) {
                int bid = askBid(player, currentPrice);

                if (isValidBid(player, bid, currentPrice)) {

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

        return new AuctionResult(highestBidder, currentPrice);
    }

    private boolean isValidBid(Player player, int bid, int currentPrice) {
        return bid > currentPrice
                && player.getMoney() >= bid;

    }

    private int askBid(Player player, int price) {
        if (player.equals(seller)) {
            return 0;
        }

        System.out.println(
                "\n" + player.getName()
                        + ", voulez-vous enchérir ?"
        );

        System.out.println(
                "Prix actuel : " + price + " €"
        );

        System.out.print(
                "Montant (> "
                        + price
                        + ", 0 pour passer) : "
        );

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public boolean canExecute() {
        return !products.isEmpty();
    }
}