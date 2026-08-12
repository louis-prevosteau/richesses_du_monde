package core.manager;

import core.cards.CardDeck;
import core.commands.CommandInvoker;
import core.enums.GameState;
import core.models.Board;
import core.products.IProduct;
import core.models.Player;
import core.observers.IGameObserver;

import java.util.List;

public class GameManager {

    private GameManager() {
    }

    public GameManager getInstance() { return null; }

    public void addPlayer(Player player) {}

    public void startGame() {}

    public void nextPlayer() {}

    public Player getCurrentPlayer() { return null; }

    public List<IGameObserver> getObservers() { return null; }

    public void addObserver(IGameObserver observer) {}

    public void removeObserver(IGameObserver observer) {}

    public void notifyGameStarted() {}

    public void notifyPlayerMoved(Player player, int position) {}

    public void notifyPlayerBought(Player player, IProduct product) {}

    public void notifyPlayerBankrupt(Player player) {}

    private void checkGameOver() {}

    public Board getBoard() { return null; }

    public List<Player> getPlayers() { return null; }

    public GameState getCurrentState() { return null; }

    public CommandInvoker getInvoker() { return null; }

    public CardDeck getNews() { return null; }

    public CardDeck getJokers() { return null; }

    public void reset() {}
}
