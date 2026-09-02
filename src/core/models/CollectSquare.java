package core.models;

import core.enums.Resource;
import core.strategies.ISquareAction;
import core.strategies.ReceiveMoneyAction;
import core.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CollectSquare implements ISquare {

    private final int position;
    private final ISquareAction action;
    private final Resource royaltiesResource;

    public CollectSquare(int position) {
        this.position = position;
        this.action = new ReceiveMoneyAction(500000);
        this.royaltiesResource = getRandomResource();
    }

    private Resource getRandomResource() {
        List<Resource> availableResources = Arrays.stream(Resource.values()).toList();
        return availableResources.get(
                ThreadLocalRandom.current().nextInt(availableResources.size())
        );
    }

    @Override
    public String getName() {
        return "Recevez 500 000 € par point réalisé";
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void landOn(Player player) {
        if (royaltiesResource != null) Utils.payRoyalties(player, royaltiesResource);
        action.execute(player);
    }
}
