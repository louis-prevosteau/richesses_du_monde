package core.commands;

import core.models.Player;

public class PayCommand implements ICommand {

    private Player player;
    private int amount;

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
        System.out.println(getDescription());
        player.pay(amount);
    }

    @Override
    public boolean canExecute() {
        return player.canAfford(amount);
    }
}
