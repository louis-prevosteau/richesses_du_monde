package core.enums;

public enum Region {
    USA("USA"),
    RUSSIE_CEI("Russie (et CEI)"),
    JAPON("Japon"),
    CHINE("Chine"),
    MOYEN_ORIENT("Moyen-Orient"),
    PENINSULE_INDIENNE("Péninsule Indienne"),
    PENINSULE_INDOCHINOISE("Péninsule Indochinoise"),
    INDONESIE("Indonésie"),
    BRESIL("Brésil"),
    CANADA("Canada"),
    MEXIQUE("Mexique"),
    PAYS_ANDINS("Pays Andins"),
    ARGENTINE("Argentine"),
    ANTILLES("Antilles"),
    VENEZUELA("Venezuela"),
    OCEANIE("Océanie"),
    AFRIQUE_OCCIDENTALE("Afrique Occidentale"),
    AFRIQUE_NORD_EST("Afrique du Nord-Est"),
    AFRIQUE_CENTRALE("Afrique Centrale"),
    AFRIQUE_GRANDS_LACS("Afrique des Grands Lacs"),
    MAGHREB("Maghreb"),
    AFRIQUE_SUD("Afrique du Sud"),
    ALLEMAGNE("Allemagne"),
    FRANCE("France"),
    SCANDINAVIE("Scandinavie"),
    BENELUX("Benelux"),
    EUROPE_MEDITERRANEENNE("Europe Méditerranéenne"),
    EUROPE_BALKANIQUE("Europe Balkanique"),
    EUROPE_DANUBIENNE("Europe Danubienne"),
    ROYAUME_UNI("Royaume-Uni");

    private String name;

    Region(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
