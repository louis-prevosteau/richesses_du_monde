package core.states;

import core.models.Player;

import java.util.logging.Logger;

public class BankruptState implements IPlayerState {

    private static final Logger logger = Logger.getLogger(BankruptState.class.getName());

    @Override
    public void takeTurn(Player player) {
        logger.warning(player.getName() + " est en faillite et ne peut plus jouer");
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
        return "Faillite";
    }
}
