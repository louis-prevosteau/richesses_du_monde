package core.observers;

import core.models.IProduct;
import core.models.Player;

public class ScoreboardObserver implements IGameObserver {

    @Override
    public void onPlayerMoved(Player player, int position) {

    }

    @Override
    public void onProductBought(Player player, IProduct product) {

    }

    @Override
    public void onPlayerBankrupt(Player player) {

    }

    @Override
    public void onGameStarted() {

    }

    @Override
    public void onGameOver(Player winner) {

    }
}
