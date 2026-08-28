package core.commands;

import core.manager.GameManager;
import core.models.Player;

public class MoveCommand implements ICommand {

    private final Player player;
    private final int steps;

    public MoveCommand(Player player, int steps) {
        this.player = player;
        this.steps = steps;
    }

    @Override
    public String getDescription() {
        return "Déplacement de " + player.getName() + " vers la case " + GameManager.getInstance().getBoard().getSquare(player.getPosition()).getName();
    }

    @Override
    public void execute() {
        player.move(steps);
    }

    @Override
    public boolean canExecute() {
        return true;
    }
}
