package core.strategies;

import core.models.Player;

public interface ISquareAction {

    String getDescription();

    void execute(Player player);
}
