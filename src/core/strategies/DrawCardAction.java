package core.strategies;

import core.commands.DrawCardCommand;
import core.enums.CardType;
import core.manager.GameManager;
import core.models.Player;

public class DrawCardAction implements ISquareAction {

    private CardType type;

    public DrawCardAction(CardType type) {
        this.type = type;
    }

    @Override
    public String getDescription() {
        return "Tirer une carte";
    }

    @Override
    public void execute(Player player) {
        GameManager
                .getInstance()
                .getInvoker()
                .executeCommand(new DrawCardCommand(player, type));
    }
}
