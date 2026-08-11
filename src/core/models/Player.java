package core.models;

import core.cards.JokerCard;
import core.enums.Resource;
import core.states.IPlayerState;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

public class Player {

    private String name;
    private int money, position, dice1, dice2, boardTour;
    private Map<Resource, List<IProduct>> properties;
    private List<JokerCard> jokers;
    private IPlayerState state;

    private static final SecureRandom random = new SecureRandom();

    public Player(String name) {
        this.name = name;
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

    public Map<Resource, List<IProduct>> getProperties() {
        return properties;
    }

    public Map<Resource, List<IProduct>> getPropertiesByResource(Resource resource) {
        return null;
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

    public void addProperty(IProduct product) {}

    public void removeProperty(IProduct product) {}

    public int roll() {
        return 0;
    }

    public boolean isDouble() {
        return false;
    }

    public void move(int steps) {}

    public void addJoker(JokerCard card) {}

    public void useJoker() {}

    public void pay(int amount) {}

    public void receive(int amount) {}

    public boolean canAfford(int amount) {
        return false;
    }

    public void displayProfile() {}
}
