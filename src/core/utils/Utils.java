package core.utils;

import core.commands.PayCommand;
import core.commands.ReceiveCommand;
import core.enums.Resource;
import core.manager.GameManager;
import core.models.Player;
import core.products.IProduct;

import java.util.List;

public class Utils {

    public static void payRoyalties(Player player, Resource royaltiesResource) {
        List<Player> otherPlayers = GameManager.getInstance().getPlayers().stream()
                .filter(p -> !player.equals(p))
                .toList();
        for (Player p : otherPlayers) {
            int royalties = calculateRoyalties(p, royaltiesResource);
            if (royalties > 0) {
                List<IProduct> ownedProducts = p.getPropertiesByResource(royaltiesResource);
                int percentage = ownedProducts == null ? 0 : ownedProducts.stream().mapToInt(IProduct::getPercentage).sum();
                System.out.println(p.getName() + " possède la ressource " + royaltiesResource.getName() + " (" + percentage + "%)");
                GameManager.getInstance().getInvoker().executeCommand(new PayCommand(player, royalties));
                GameManager.getInstance().getInvoker().executeCommand(new ReceiveCommand(p, royalties));
            }
        }
    }

    public static int calculateRoyalties(Player player, Resource resource) {
        List<IProduct> playerProducts = player.getPropertiesByResource(resource);
        if (playerProducts == null || playerProducts.isEmpty()) {
            return 0;
        }
        int totalResource = playerProducts.stream()
                .mapToInt(IProduct::getPercentage)
                .sum();
        if (totalResource >= 90) {
            return resource.getRapportBase() * 20;
        }
        if (totalResource >= 70) {
            return resource.getRapportBase() * 10;
        }
        if (totalResource >= 50) {
            return resource.getRapportBase() * 5;
        }
        if (totalResource >= 30) {
            return resource.getRapportBase();
        }
        return 0;
    }
}
