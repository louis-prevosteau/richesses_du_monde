package core.observers;

import core.cards.ICard;
import core.enums.Resource;
import core.manager.GameManager;
import core.models.Board;
import core.products.IProduct;
import core.models.Player;

import java.util.List;

public class ScoreboardObserver implements IGameObserver {

    @Override
    public void onPlayerMoved(Player player, int position) {
        Board board = GameManager.getInstance().getBoard();
        System.out.println("[SCOREBOARD] " + player.getName() + " est maintenant sur la case " + board.getSquare(position).getName());
    }

    @Override
    public void onProductBought(Player player, IProduct product) {
        updatePlayerScore(player);
        System.out.println("[SCOREBOARD] " + player.getName() + " a acheté " + product.getResource().getName() + " : " + product.getPercentage()+ "% - Provenance : " + product.getRegion() + " pour " + product.getPrice() + " €");
    }

    @Override
    public void onPlayerBankrupt(Player player) {
        System.out.println("[SCOREBOARD] " + player.getName() + " a fait faillite");
        displayFinalScore(player);
    }

    @Override
    public void onGameStarted() {
        System.out.println("[SCOREBOARD] ========== DÉBUT DE LA PARTIE ==========");
    }

    @Override
    public void onGameOver(Player winner) {
        System.out.println("[SCOREBOARD] ========== FIN DE LA PARTIE ==========");
        System.out.println("[SCOREBOARD] Vaiqueur : " + winner.getName());
        displayFinalScore(winner);
    }

    private void updatePlayerScore(Player player) {
        int totalValue = player.getMoney() + calculatePropertiesValue(player);
        System.out.println("[SCOREBOARD] Valeur totale de " + player.getName() + ": " + totalValue + "€");
    }

    private void displayFinalScore(Player player) {
        System.out.println("[SCOREBOARD] Score final:");
        System.out.println("[SCOREBOARD]   - Argent: " + player.getMoney() + "€");
        System.out.println("[SCOREBOARD]   - Propriétés: " + player.getProperties().size());
        System.out.println("[SCOREBOARD]   - Valeur totale: " +
                (player.getMoney() + calculatePropertiesValue(player)) + "€");
    }

    private int calculatePropertiesValue(Player player) {
        return player.getProperties()
                .values()
                .stream()
                .flatMap(List::stream)
                .mapToInt(IProduct::getPrice)
                .sum();
    }

    @Override
    public void onPlayerTurnStarted(Player player) {
        System.out.println("[SCOREBOARD] Au tour de " + player.getName());
    }

    @Override
    public void onProductSold(Player seller, Player buyer, Resource resource, int productsSize, int price) {
        updatePlayerScore(seller);
        updatePlayerScore(buyer);
        System.out.println("[SCOREBOARD] " + seller.getName() + " a vendu " + resource.getName() + "(" + productsSize + " produit(s))" + " pour " + price + " €");
        System.out.println("[SCOREBOARD] " + buyer.getName() + " remporte l'enchère");
    }

    @Override
    public void onCardDrawn(Player player, ICard card) {
        System.out.println("[SCOREBOARD] " + player.getName() + " a tirer une carte " + card.getType().getName());
    }

    @Override
    public void onJokerUsed(Player player) {
        System.out.println("[SCOREBOARD] " + player.getName() + " a utilisé un joker");
    }
}
