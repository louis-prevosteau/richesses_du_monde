package core.models;

import core.commands.PayCommand;
import core.commands.ReceiveCommand;
import core.commands.RollDiceCommand;
import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.manager.GameManager;
import core.products.IProduct;
import core.strategies.BuyProductAction;
import core.strategies.ISquareAction;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ProductSquare implements ISquare {

    private final String name;
    private final int position;
    private final ISquareAction action;
    private Resource royaltiesResource;


    public ProductSquare(String name, int position, Continent continent, Region region) {
        this.name = name;
        this.position = position;
        this.action = new BuyProductAction(continent, region);
        this.royaltiesResource = (position == 46 || position == 60) ? null : getRandomResource();
    }

    public ProductSquare(
            String name,
            int position,
            ISquareAction action
    ) {
        this.name = name;
        this.position = position;
        this.royaltiesResource = (position == 46 || position == 60) ? null : getRandomResource();
        this.action = action;
    }

    private Resource getRandomResource() {
        List<Resource> availableResources = Arrays.stream(Resource.values()).toList();
        if (availableResources.isEmpty()) {
            throw new IllegalStateException(
                    "Toutes les ressources ont déjà été attribuées 2 fois."
            );
        }
        Resource resource = availableResources.get(
                ThreadLocalRandom.current().nextInt(availableResources.size())
        );
        return resource;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void landOn(Player player) {
        System.out.println(player.getName() + " arrive sur la case " + name);
        if (royaltiesResource != null) payRoyalties(player);
        action.execute(player);
    }

    private int calculateRoyalties(Player player, Resource resource) {
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

    private void payRoyalties(Player player) {
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
}
