package core.manager;

import core.cards.CardDeck;
import core.commands.CommandInvoker;
import core.enums.GameState;
import core.models.Board;
import core.products.IProduct;
import core.models.Player;
import core.observers.IGameObserver;
import core.products.ProductFactory;
import core.products.Shop;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static GameManager instance;
    private CommandInvoker invoker;
    private Board board;
    private List<Player> players;
    private GameState currentState;
    private List<IGameObserver> observers;
    private int currentPlayerIndex;
    private CardDeck news, jokers;
    private Shop shop;

    private GameManager() {
        this.players = new ArrayList<>();
        this.invoker = new CommandInvoker();
        this.shop = ProductFactory.createShop();
    }

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();
        return instance;
    }

    public Shop getShop() {
        return shop;
    }

    public void addPlayer(Player player) { players.add(player); }

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

    public List<Player> getPlayers() { return players; }

    public GameState getCurrentState() { return null; }

    public CommandInvoker getInvoker() { return invoker; }

    public CardDeck getNews() { return news; }

    public CardDeck getJokers() { return jokers; }

    public void reset() {}
}
