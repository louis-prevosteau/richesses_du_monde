package core.commands;

import core.models.Player;

public class UseJokerCommand implements ICommand {

    private Player player;

    public UseJokerCommand(Player player) {
        this.player = player;
    }

    @Override
    public String getDescription() {
        return player.getName() + " utilise une carte joker";
    }

    @Override
    public void execute() {
        System.out.println(getDescription());
        player.useJoker();
    }

    @Override
    public boolean canExecute() {
        return !player.getJokers().isEmpty();
    }
}
