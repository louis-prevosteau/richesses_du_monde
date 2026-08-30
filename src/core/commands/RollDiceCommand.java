package core.commands;

import core.cards.CardDeck;
import core.models.Player;

import java.util.logging.Logger;

public class RollDiceCommand implements ICommand {

    private final Player player;
    private int result;
    private boolean isDouble;
    private static final Logger logger = Logger.getLogger(RollDiceCommand.class.getName());

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
        if (isDouble) {
            logger.info(player.getName() + " a fait un double " + player.getDice2() + ". Il paie " + player.getDice2() * 1000000 + " €.");
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
