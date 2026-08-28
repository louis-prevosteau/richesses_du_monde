package core.states;

import core.commands.MoveCommand;
import core.commands.RollDiceCommand;
import core.manager.GameManager;
import core.models.Player;

public class NormalState implements IPlayerState {

    @Override
    public void takeTurn(Player player) {
        RollDiceCommand rollCommand = new RollDiceCommand(player);
        GameManager
                .getInstance()
                .getInvoker()
                .executeCommand(rollCommand);
        GameManager
                .getInstance()
                .getInvoker()
                .executeCommand(new MoveCommand(player, rollCommand.getResult()));
        GameManager
                .getInstance()
                .notifyPlayerMoved(player, rollCommand.getResult());
    }

    @Override
    public boolean canBuy() {
        return true;
    }

    @Override
    public boolean canPayRoyalties() {
        return true;
    }

    @Override
    public String getName() {
        return "Normal";
    }
}
