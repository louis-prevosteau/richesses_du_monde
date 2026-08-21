package core.commands;

import core.cards.ICard;
import core.enums.CardType;
import core.manager.GameManager;
import core.models.Player;

public class DrawCardCommand implements ICommand {

    private Player player;
    private CardType type;

    public DrawCardCommand(Player player, CardType type) {
        this.player = player;
        this.type = type;
    }

    @Override
    public String getDescription() {
        return player.getName() + " tire une carte " + type.getName();
    }

    @Override
    public void execute() {
        System.out.println(getDescription());
        ICard card;
        if (type.equals(CardType.NEWS))
            card = GameManager.getInstance().getNews().draw();
        else
            card = GameManager.getInstance().getJokers().draw();
        card.apply(player);
    }

    @Override
    public boolean canExecute() {
        return !GameManager.getInstance().getJokers().isEmpty() && !GameManager.getInstance().getNews().isEmpty();
    }
}
