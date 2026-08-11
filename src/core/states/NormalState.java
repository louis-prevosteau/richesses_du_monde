package core.states;

import core.models.Player;

public class NormalState implements IPlayerState {

    @Override
    public void takeTurn(Player player) {

    }

    @Override
    public boolean canBuy() {
        return false;
    }

    @Override
    public boolean canPayRoyalties() {
        return false;
    }

    @Override
    public String getName() {
        return "";
    }
}
