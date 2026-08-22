package core.commands;

import core.models.Player;
import core.products.IProduct;

public class BuyProductCommand implements ICommand {

    private final Player player;
    private final IProduct product;

    public BuyProductCommand(Player player, IProduct product) {
        this.player = player;
        this.product = product;
    }

    @Override
    public String getDescription() {
        return player.getName() + " achète " + product.getResource() + " - " + product.getPercentage() + " % - (" + product.getRegion() + ") pour " + product.getPrice() + " €";
    }

    @Override
    public void execute() {
        System.out.println(getDescription());
        player.pay(product.getPrice());
        player.addProperty(product);
        product.setOwner(player);
    }

    @Override
    public boolean canExecute() {
        return player.canAfford(product.getPrice());
    }
}
