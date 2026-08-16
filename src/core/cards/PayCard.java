package core.cards;

import core.commands.PayCommand;
import core.commands.ReceiveCommand;
import core.enums.CardType;
import core.enums.Resource;
import core.manager.GameManager;
import core.models.Player;

public class PayCard extends Card {

    private final int[] amounts;
    private final Resource resource;

    public PayCard(String description, CardType type, int[] amounts, Resource resource) {
        super(description, type);
        this.amounts = amounts;
        this.resource = resource;
    }

    @Override
    public void executeEffect(Player player) {
        System.out.println(getDescription());
        if (resource != null) {
            if (!player.getPropertiesByResource(resource).isEmpty())
                GameManager.getInstance().getInvoker().executeCommand(new PayCommand(player, amounts[0]));
            else
                GameManager.getInstance().getInvoker().executeCommand(new PayCommand(player, amounts[1]));
        }
        else {
            GameManager.getInstance().getInvoker().executeCommand(new PayCommand(player, amounts[0]));
        }
    }
}
