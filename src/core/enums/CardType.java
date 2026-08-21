package core.enums;

public enum CardType {
    NEWS("Actualité"),
    JOKER("Joker"),
    ;

    private final String name;

    CardType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


