package core.models;

import core.cards.JokerCard;
import core.enums.Resource;
import core.products.IProduct;
import core.states.IPlayerState;
import core.states.NormalState;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    private String name;
    private int money, position, dice1, dice2, totalDice, boardTour;
    private Map<Resource, List<IProduct>> properties;
    private List<JokerCard> jokers;
    private IPlayerState state;

    private static final SecureRandom random = new SecureRandom();

    public Player(String name) {
        this.name = name;
        this.money = 60000000;
        this.position = 0;
        this.boardTour = 1;
        this.properties = new HashMap<>();
        this.jokers = new ArrayList<>();
        this.state = new NormalState();
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public int getPosition() {
        return position;
    }

    public int getDice1() {
        return dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }

    public void addProperty(IProduct product) {
        properties
                .computeIfAbsent(product.getResource(), k -> new ArrayList<>())
                .add(product);
    }

    public Map<Resource, List<IProduct>> getProperties() {
        Map<Resource, List<IProduct>> copy = new HashMap<>();
        for (Map.Entry<Resource, List<IProduct>> entry : properties.entrySet()) {
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }

    public Map<Resource, List<IProduct>> getPropertiesByResource(Resource resource) {
        List<IProduct> produits = properties.get(resource);
        if (produits == null || produits.isEmpty()) {
            return new HashMap<>();
        }
        Map<Resource, List<IProduct>> result = new HashMap<>();
        result.put(resource, new ArrayList<>(produits));
        return result;
    }

    public List<JokerCard> getJokers() {
        return jokers;
    }

    public IPlayerState getState() {
        return state;
    }

    public int getBoardTour() {
        return boardTour;
    }

    public void removeProperty(IProduct product) {
        properties
                .computeIfAbsent(product.getResource(), k -> new ArrayList<>())
                .remove(product);
    }

    public int getTotalDice() {
        return totalDice;
    }

    public int roll() {
        dice1 = random.nextInt(6) + 1;
        dice2 = random.nextInt(6) + 1;
        totalDice = dice1 + dice2;
        return totalDice;
    }

    public boolean isDouble() {
        return dice1 == dice2;
    }

    public void move(int steps) {
        position = (position + steps) % 66;
    }

    public void addJoker(JokerCard card) {
        jokers.add(card);
    }

    public void useJoker() {
        jokers.removeFirst();
    }

    public void pay(int amount) { money -= amount; }

    public void receive(int amount) { money += amount; }

    public boolean canAfford(int amount) {
        return money >= amount;
    }

    public void displayProfile() {}
}
