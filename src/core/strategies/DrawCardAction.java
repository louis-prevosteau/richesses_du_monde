package core.strategies;

import core.commands.DrawCardCommand;
import core.enums.CardType;
import core.manager.GameManager;
import core.models.Player;

public class DrawCardAction implements ISquareAction {

    private final CardType type;

    public DrawCardAction(CardType type) {
        this.type = type;
    }

    @Override
    public String getDescription() {
        return "Tirer une carte" + type.getName();
    }

    @Override
    public void execute(Player player) {
        System.out.println(getDescription());
        GameManager
                .getInstance()
                .getInvoker()
                .executeCommand(new DrawCardCommand(player, type));
    }

    public CardType getType() {
        return type;
    }
}
