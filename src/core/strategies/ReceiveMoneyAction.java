package core.strategies;

import core.commands.ReceiveCommand;
import core.commands.SellResourceCommand;
import core.manager.GameManager;
import core.models.Player;

import java.util.logging.Logger;

public class ReceiveMoneyAction implements ISquareAction {

    private final int amount;
    private static final Logger logger = Logger.getLogger(ReceiveMoneyAction.class.getName());

    public ReceiveMoneyAction(int amount) {
        this.amount = amount;
    }

    @Override
    public String getDescription() {
        return "Vous recevez " + amount + " €";
    }

    @Override
    public void execute(Player player) {
        logger.info(getDescription());
        GameManager.getInstance().getInvoker().executeCommand(new ReceiveCommand(player, amount * player.getTotalDice()));
    }
}
