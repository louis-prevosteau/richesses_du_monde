package core.observers;

import core.products.IProduct;
import core.models.Player;

public interface IGameObserver {

    void onPlayerMoved(Player player, int position);

    void onProductBought(Player player, IProduct product);

    void onPlayerBankrupt(Player player);

    void onGameStarted();

    void onGameOver(Player winner);
}
