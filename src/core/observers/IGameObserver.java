package core.observers;

import core.cards.ICard;
import core.enums.Resource;
import core.products.IProduct;
import core.models.Player;

public interface IGameObserver {

    void onPlayerTurnStarted(Player player);

    void onPlayerMoved(Player player, int position);

    void onProductBought(Player player, IProduct product);

    void onProductSold(Player seller, Player buyer, Resource resource, int productsSize, int price);

    void onCardDrawn(Player player, ICard card);

    void onJokerUsed(Player player);

    void onPlayerBankrupt(Player player);

    void onGameStarted();

    void onGameOver(Player winner);
}
