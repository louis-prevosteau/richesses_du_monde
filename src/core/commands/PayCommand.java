package core.commands;

import core.models.Player;

public class PayCommand implements ICommand {

    private final Player player;
    private final int amount;

    public PayCommand(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    @Override
    public String getDescription() {
        return player.getName() + " paie " + amount + " €";
    }

    @Override
    public void execute() {
        player.pay(amount);
    }

    @Override
    public boolean canExecute() {
        return player.canAfford(amount);
    }
}
