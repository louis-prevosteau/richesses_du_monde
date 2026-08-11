package core.states;

import core.models.Player;

public interface IPlayerState {

    void takeTurn(Player player);

    boolean canBuy();

    boolean canPayRoyalties();

    String getName();
}
