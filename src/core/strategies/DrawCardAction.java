package core.strategies;

import core.commands.DrawCardCommand;
import core.enums.CardType;
import core.manager.GameManager;
import core.models.Player;

import java.util.logging.Logger;

public class DrawCardAction implements ISquareAction {

    private final CardType type;
    private static final Logger logger = Logger.getLogger(DrawCardAction.class.getName());

    public DrawCardAction(CardType type) {
        this.type = type;
    }

    @Override
    public String getDescription() {
        return "Tirer une carte" + type.getName();
    }

    @Override
    public void execute(Player player) {
        logger.info(getDescription());
        GameManager
                .getInstance()
                .getInvoker()
                .executeCommand(new DrawCardCommand(player, type));
    }

    public CardType getType() {
        return type;
    }
}
