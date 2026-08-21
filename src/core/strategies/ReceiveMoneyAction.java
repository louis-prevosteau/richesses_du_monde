package core.strategies;

import core.commands.ReceiveCommand;
import core.manager.GameManager;
import core.models.Player;

public class ReceiveMoneyAction implements ISquareAction {

    private Player player;
    private int amount;

    public ReceiveMoneyAction(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    @Override
    public String getDescription() {
        return player.getName() + " a fait un lancer : " + player.getTotalDice() + " et reçoit " + amount + " €";
    }

    @Override
    public void execute(Player player) {
        System.out.println(getDescription());
        GameManager.getInstance().getInvoker().executeCommand(new ReceiveCommand(player, amount));
    }
}
