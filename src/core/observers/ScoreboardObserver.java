package core.observers;

import core.cards.CardDeck;
import core.cards.ICard;
import core.enums.Resource;
import core.manager.GameManager;
import core.models.Board;
import core.products.IProduct;
import core.models.Player;

import java.util.List;
import java.util.logging.Logger;

public class ScoreboardObserver implements IGameObserver {

    public static final String SCOREBOARD = "[SCOREBOARD] ";
    private static final Logger logger = Logger.getLogger(ScoreboardObserver.class.getName());

    @Override
    public void onPlayerMoved(Player player, int position) {
        Board board = GameManager.getInstance().getBoard();
        logger.info(SCOREBOARD + player.getName() + " est maintenant sur la case " + board.getSquare(position).getName());
    }

    @Override
    public void onProductBought(Player player, IProduct product) {
        updatePlayerScore(player);
        logger.info(SCOREBOARD + player.getName() + " a acheté " + product.getResource().getName() + " : " + product.getPercentage()+ "% - Provenance : " + product.getRegion() + " pour " + product.getPrice() + " €");
    }

    @Override
    public void onPlayerBankrupt(Player player) {
        logger.info(SCOREBOARD + player.getName() + " a fait faillite");
        displayFinalScore(player);
    }

    @Override
    public void onGameStarted() {
        logger.info("[SCOREBOARD] ========== DÉBUT DE LA PARTIE ==========");
    }

    @Override
    public void onGameOver(Player winner) {
        logger.info("[SCOREBOARD] ========== FIN DE LA PARTIE ==========");
        logger.info("[SCOREBOARD] Vaiqueur : " + winner.getName());
        displayFinalScore(winner);
    }

    private void updatePlayerScore(Player player) {
        int totalValue = player.getMoney() + calculatePropertiesValue(player);
        logger.info("[SCOREBOARD] Valeur totale de " + player.getName() + ": " + totalValue + "€");
    }

    private void displayFinalScore(Player player) {
        logger.info("[SCOREBOARD] Score final:");
        logger.info("[SCOREBOARD]   - Argent: " + player.getMoney() + "€");
        logger.info("[SCOREBOARD]   - Propriétés: " + player.getProperties().size());
        logger.info("[SCOREBOARD]   - Valeur totale: " +
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
        logger.info("[SCOREBOARD] Au tour de " + player.getName());
    }

    @Override
    public void onProductSold(Player seller, Player buyer, Resource resource, int productsSize, int price) {
        updatePlayerScore(seller);
        updatePlayerScore(buyer);
        logger.info(SCOREBOARD + seller.getName() + " a vendu " + resource.getName() + "(" + productsSize + " produit(s))" + " pour " + price + " €");
        logger.info(SCOREBOARD + buyer.getName() + " remporte l'enchère");
    }

    @Override
    public void onCardDrawn(Player player, ICard card) {
        logger.info(SCOREBOARD + player.getName() + " a tirer une carte " + card.getType().getName());
    }

    @Override
    public void onJokerUsed(Player player) {
        logger.info(SCOREBOARD + player.getName() + " a utilisé un joker");
    }
}
