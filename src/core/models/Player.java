package core.models;

import core.cards.JokerCard;
import core.enums.Resource;
import core.manager.GameManager;
import core.products.IProduct;
import core.products.Shop;
import core.states.BankruptState;
import core.states.IPlayerState;
import core.states.NormalState;
import core.utils.Utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    private final String name;
    private int money;
    private int position;
    private int dice1;
    private int dice2;
    private int totalDice;
    private final int boardTour;
    private final Map<Resource, List<IProduct>> properties;
    private final List<JokerCard> jokers;
    private IPlayerState state;
    private boolean hasPlayed;

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

    public List<IProduct> getPropertiesByResource(Resource resource) {
        return properties.get(resource);
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
        position = (position + steps) % 65;
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

    public void displayProfile() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("Profil de " + name);
        System.out.println("═══════════════════════════════════════");
        System.out.println("Argent: " + money + "€");
        System.out.println("Ressources: ");

        if (!properties.isEmpty()) {
            System.out.println("\nDétail des propriétés:");
            for (Map.Entry<Resource, List<IProduct>> entry : properties.entrySet()) {

                int totalPercentage = entry.getValue()
                        .stream()
                        .mapToInt(IProduct::getPercentage)
                        .sum();

                System.out.println(
                        "- " + entry.getKey().getName()
                                + " : " + totalPercentage
                                + "% (" + entry.getValue().size()
                                + " produits) --- Royalties : "
                                + Utils.calculateRoyalties(this, entry.getKey())
                                + " €"
                );
            }
        }
        System.out.println("═══════════════════════════════════════\n");
    }

    public void declareBankruptcy() {
        Shop shop = GameManager.getInstance().getShop();

        properties.values().stream()
                .flatMap(List::stream)
                .forEach(product -> {
                    product.setOwner(null);
                    shop.returnsProducts(product);
                });
        properties.clear();
        for (JokerCard card : jokers) {
            card.returnToDeck();
        }
        jokers.clear();
        state = new BankruptState();
        GameManager.getInstance()
                .notifyPlayerBankrupt(this);
    }

    public void setState(IPlayerState state) {
        this.state = state;
    }

    public boolean hasPlayed() {
        return hasPlayed;
    }

    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }
}
