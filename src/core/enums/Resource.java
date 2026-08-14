package core.enums;

public enum Resource {

    COBALT("Cobalt", 600000),
    OR("Or", 1000000),
    RIZ("Riz", 700000),
    BLE("Blé", 1000000),
    CACAO("Cacao", 400000),
    THE("Thé", 500000),
    CAFE("Café", 400000),
    PETROLE("Pétrole", 1200000),
    HOUILLE("Houille", 1100000),
    ACIER("Acier", 1100000),
    COTON_BRUT("Coton brut", 500000),
    LAINE_BRUTE("Laine brute", 500000),
    CUIVRE("Cuivre", 900000),
    SUCRE("Sucre", 90000),
    ARGENT("Argent", 700000),
    CONSTRUCTION_AUTOMOBILE("Construction automobile", 800000),
    CONSTRUCTION_NAVALE("Construction navale", 700000),
    ALUMINIUM("Aluminium", 900000),
    URANIUM("Uranium", 800000),
    FER("Fer", 1000000),
    NICKEL("Nickel", 600000),
    GAZ_NATUREL("Gaz naturel", 800000),
    CAOUTCHOUC_NATUREL("Caoutchouc naturel", 400000),
    PLOMB("Plomb", 600000);

    private final String name;
    private final int rapportBase;

    Resource(String name, int rapportBase) {
        this.name = name;
        this.rapportBase = rapportBase;
    }

    public String getName() {
        return name;
    }

    public int getRapportBase() {
        return rapportBase;
    }
}