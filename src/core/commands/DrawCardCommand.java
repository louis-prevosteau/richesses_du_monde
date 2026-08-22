package core.commands;

import core.cards.ICard;
import core.enums.CardType;
import core.manager.GameManager;
import core.models.Player;

public class DrawCardCommand implements ICommand {

    private final Player player;
    private final CardType type;

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

        GameManager manager = GameManager.getInstance();

        ICard card = CardType.NEWS.equals(type)
                ? manager.getNews().draw()
                : manager.getJokers().draw();

        if (card != null) {
            card.apply(player);
        }
    }

    @Override
    public boolean canExecute() {
        GameManager manager = GameManager.getInstance();
        return manager.getNews() != null
                && manager.getJokers() != null
                && !manager.getNews().isEmpty()
                && !manager.getJokers().isEmpty();
    }
}
