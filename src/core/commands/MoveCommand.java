package core.commands;

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
        return "Déplacement du joueur";
    }

    @Override
    public void execute() {
        System.out.println(getDescription());
        player.move(steps);
    }

    @Override
    public boolean canExecute() {
        return true;
    }
}
