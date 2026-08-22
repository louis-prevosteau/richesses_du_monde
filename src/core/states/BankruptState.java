package core.states;

import core.models.Player;

public class BankruptState implements IPlayerState {

    @Override
    public void takeTurn(Player player) {
        System.out.println(player.getName() + " est en faillite et ne peut plus jouer");
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
