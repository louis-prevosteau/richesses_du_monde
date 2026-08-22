package core.commands;

import core.models.Player;

public class RollDiceCommand implements ICommand {

    private final Player player;
    private int result;
    private boolean isDouble;

    public RollDiceCommand(Player player) {
        this.player = player;
    }

    @Override
    public String getDescription() {
        return player.getName() +
                " lance les dés : " + result +
                (isDouble ? " (double)" : "");
    }

    @Override
    public void execute() {
        result = player.roll();
        isDouble = player.isDouble();
        System.out.println(getDescription());
        if (isDouble) {
            System.out.println(player.getName() + " a fait un double " + player.getDice2() + ". Il paie " + player.getDice2() + " €.");
            player.pay(player.getDice2() * 1000000);
        }
    }

    @Override
    public boolean canExecute() {
        return true;
    }

    public int getResult() {
        return player.getTotalDice();
    }
}
