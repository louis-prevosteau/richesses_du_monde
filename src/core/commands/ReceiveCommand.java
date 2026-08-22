package core.commands;

import core.models.Player;

public class ReceiveCommand implements ICommand {

    private final Player player;
    private final int amount;

    public ReceiveCommand(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    @Override
    public String getDescription() {
        return player.getName() + " reçoit " + amount + " €";
    }

    @Override
    public void execute() {
        System.out.println(getDescription());
        player.receive(amount);
    }

    @Override
    public boolean canExecute() {
        return true;
    }
}
