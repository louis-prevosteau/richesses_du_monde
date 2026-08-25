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
import core.utils.Utils;

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
        if (royaltiesResource != null) Utils.payRoyalties(player, royaltiesResource);
        action.execute(player);
    }

    public void setRoyaltiesResource(Resource royaltiesResource) {
        this.royaltiesResource = royaltiesResource;
    }
}
