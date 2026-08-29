package core.strategies;

import core.commands.ReceiveCommand;
import core.manager.GameManager;
import core.models.Player;

public class ReceiveMoneyAction implements ISquareAction {

    private final int amount;

    public ReceiveMoneyAction(int amount) {
        this.amount = amount;
    }

    @Override
    public String getDescription() {
        return "Vous recevez " + amount + " €";
    }

    @Override
    public void execute(Player player) {
        System.out.println(getDescription());
        GameManager.getInstance().getInvoker().executeCommand(new ReceiveCommand(player, amount * player.getTotalDice()));
    }
}
