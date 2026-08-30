package core.cards;

import core.commands.PayCommand;
import core.commands.ReceiveCommand;
import core.enums.CardType;
import core.manager.GameManager;
import core.models.Player;

import java.util.List;
import java.util.logging.Logger;

public class GiftCard extends Card {

    private final int amountPerPlayer;
    private static final Logger logger = Logger.getLogger(GiftCard.class.getName());

    public GiftCard(String description, CardType type, int amountPerPlayer) {
        super(description, type);
        this.amountPerPlayer = amountPerPlayer;
    }

    @Override
    public void executeEffect(Player player) {
        logger.info(getDescription());
        collectFromAllPlayers(player, GameManager.getInstance().getPlayers());
        returnToDeck();
    }

    private void collectFromAllPlayers(Player player, List<Player> all) {
        int totalReceived = 0;
        int playersWhoCouldPay = 0;

        for (Player otherPlayer : all) {
            if (otherPlayer == player) {
                continue;
            }

            GameManager.getInstance().getInvoker().executeCommand(new PayCommand(otherPlayer, amountPerPlayer));
            GameManager.getInstance().getInvoker().executeCommand(new ReceiveCommand(player, amountPerPlayer));

            totalReceived += amountPerPlayer;
            playersWhoCouldPay++;

            logger.info(otherPlayer.getName() +
                    " donne " + amountPerPlayer + "€");
        }

        logger.info(player.getName() + " reçoit " +
                totalReceived + "€ au total (" +
                playersWhoCouldPay + " joueur(s))");
    }
}
