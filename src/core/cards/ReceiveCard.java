package core.cards;

import core.commands.ReceiveCommand;
import core.enums.CardType;
import core.enums.Resource;
import core.manager.GameManager;
import core.models.Player;
import core.products.IProduct;

import java.util.List;
import java.util.logging.Logger;

public class ReceiveCard extends Card {

    private final int[] amounts;
    private final Resource resource;
    private static final Logger logger = Logger.getLogger(ReceiveCard.class.getName());

    public ReceiveCard(String description, CardType type, int[] amounts, Resource resource) {
        super(description, type);
        this.amounts = amounts;
        this.resource = resource;
    }

    @Override
    public void executeEffect(Player player) {
        logger.info(getDescription());
        if (resource != null) {
            List<IProduct> playerProducts = player.getPropertiesByResource(resource);
            if (playerProducts != null && !playerProducts.isEmpty())
                GameManager.getInstance().getInvoker().executeCommand(new ReceiveCommand(player, amounts[0]));
            else
                GameManager.getInstance().getInvoker().executeCommand(new ReceiveCommand(player, amounts[1]));
        }
        else {
            GameManager.getInstance().getInvoker().executeCommand(new ReceiveCommand(player, amounts[0]));
        }
    }
}
