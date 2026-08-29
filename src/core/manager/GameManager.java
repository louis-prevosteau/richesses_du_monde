package core.manager;

import core.cards.CardDeck;
import core.cards.CardFactory;
import core.cards.ICard;
import core.commands.CommandInvoker;
import core.enums.CardType;
import core.enums.GameState;
import core.enums.Resource;
import core.factories.*;
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
    private final Board board;
    private final List<Player> players;
    private GameState currentState;
    private final List<IGameObserver> observers;
    private int currentPlayerIndex;
    private CardDeck news, jokers;
    private Shop shop;

    private GameManager() {
        this.board = new Board();

        int[] productPositions = {1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 19, 20, 21, 22, 23, 24, 25, 26, 27, 31, 32, 33, 37, 38, 39, 40, 41, 42, 43, 46, 47, 51, 52, 53, 54, 55, 56, 57, 60, 61};
        int[] salePositions = {10, 28, 36, 50, 64};
        int[] collectPositions = {11, 17, 29, 35, 45, 49, 59, 63};
        int[] newsPositions = {12, 16, 30, 34, 48, 62};
        int[] jokerPositions = {18, 44, 58};

        SquareFactory productSquareFactory = new ProductSquareFactory();
        SquareFactory goSquareFactory = new GoSquareFactory();
        SquareFactory saleSquareFactory = new SaleSquareFactory();
        SquareFactory collectSquareFactory = new CollectSquareFactory();
        SquareFactory newsSquareFactory = new CardSquareFactory(CardType.NEWS);
        SquareFactory jokerSquareFactory = new CardSquareFactory(CardType.JOKER);

        this.board.setSquare(0, goSquareFactory.createSquare(0));
        for (int p : productPositions) this.board.setSquare(p, productSquareFactory.createSquare(p));
        for (int p : salePositions) this.board.setSquare(p, saleSquareFactory.createSquare(p));
        for (int p : collectPositions) this.board.setSquare(p, collectSquareFactory.createSquare(p));
        for (int p : newsPositions) this.board.setSquare(p, newsSquareFactory.createSquare(p));
        for (int p : jokerPositions) this.board.setSquare(p, jokerSquareFactory.createSquare(p));

        this.players = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.invoker = new CommandInvoker();
        this.shop = ProductFactory.createShop();
        this.news = CardFactory.createNewsDeck();
        this.jokers = CardFactory.createJokerDeck();
        this.currentState = GameState.WAITING;
        this.currentPlayerIndex = 0;
    }

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();
        return instance;
    }

    public Shop getShop() {
        return shop;
    }

    public void addPlayer(Player player) {
        if (currentState == GameState.WAITING) {
            players.add(player);
        } else {
            throw new IllegalStateException("Impossible d'ajouter un joueur après le démarrage du jeu");
        }
    }

    public void startGame() {
        if (players.size() < 2) {
            throw new IllegalStateException("Il faut au moins 2 joueurs pour commencer");
        }
        currentState = GameState.PLAYING;
        notifyGameStarted();
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public Player getCurrentPlayer() { return players.get(currentPlayerIndex); }

    public void addObserver(IGameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IGameObserver observer) {
        observers.remove(observer);
    }

    public void notifyGameStarted() {
        for (IGameObserver observer : observers)
            observer.onGameStarted();
    }

    public void notifyPlayerMoved(Player player, int position) {
        for (IGameObserver observer : observers)
            observer.onPlayerMoved(player, position);
    }

    public void notifyPlayerBought(Player player, IProduct product) {
        for (IGameObserver observer : observers)
            observer.onProductBought(player, product);
    }

    public void notifyPlayerSold(Player seller, Player buyer, Resource resource, int productsSize, int price) {
        for (IGameObserver observer : observers)
            observer.onProductSold(seller, buyer, resource, productsSize, price);
    }

    public void notifyTurnStarted(Player player) {
        for (IGameObserver observer : observers)
            observer.onPlayerTurnStarted(player);
    }

    public void notifyCardDrawn(Player player, ICard card) {
        for (IGameObserver observer : observers)
            observer.onCardDrawn(player, card);
    }

    public void notifyJokerUsed(Player player) {
        for (IGameObserver observer : observers)
            observer.onJokerUsed(player);
    }

    public void notifyPlayerBankrupt(Player player) {
        for (IGameObserver observer : observers)
            observer.onPlayerBankrupt(player);
        players.remove(player);
        checkGameOver();
    }

    private void checkGameOver() {
        if (players.size() == 1) {
            currentState = GameState.OVER;
            for (IGameObserver observer : observers) {
                observer.onGameOver(players.get(0));
            }
        }
    }

    public Board getBoard() { return board; }

    public List<Player> getPlayers() { return players; }

    public GameState getCurrentState() { return currentState; }

    public CommandInvoker getInvoker() { return invoker; }

    public CardDeck getNews() { return news; }

    public CardDeck getJokers() { return jokers; }

    public void reset() {
        this.players.clear();
        this.observers.clear();
        this.currentState = GameState.WAITING;
        this.invoker = new CommandInvoker();
        this.currentPlayerIndex = 0;
        this.shop = ProductFactory.createShop();
        this.news = CardFactory.createNewsDeck();
        this.jokers = CardFactory.createJokerDeck();
    }
}
