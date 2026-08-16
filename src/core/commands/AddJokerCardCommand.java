package core.commands;

import core.cards.JokerCard;
import core.models.Player;

public class AddJokerCardCommand implements ICommand {

    private Player player;
    private JokerCard card;

    public AddJokerCardCommand(Player player, JokerCard card) {
        this.player = player;
        this.card = card;
    }

    @Override
    public String getDescription() {
        return player.getName() + " prend une carte joker";
    }

    @Override
    public void execute() {
        System.out.println(getDescription());
        player.addJoker(card);
    }

    @Override
    public boolean canExecute() {
        return card != null;
    }
}
