package core.factories;

import core.enums.Continent;
import core.enums.Region;
import core.models.ISquare;
import core.models.ProductSquare;


public class ProductSquareFactory extends SquareFactory {

    @Override
    public ISquare createSquare(int position) {
        return switch (position) {
            case 1 -> new ProductSquare("Scandinavie", position, Continent.EUROPE, Region.SCANDINAVIE);
            case 2 -> new ProductSquare("Allemagne", position, Continent.EUROPE, Region.ALLEMAGNE);
            case 3, 7 -> new ProductSquare("Choix Europe sauf Russie et CEI", position, Continent.EUROPE, null);
            case 4 -> new ProductSquare("Royaume-Uni", position, Continent.EUROPE, Region.ROYAUME_UNI);
            case 5 -> new ProductSquare("France", position, Continent.EUROPE, Region.FRANCE);
            case 6 -> new ProductSquare("Europe Méditerranéenne", position, Continent.EUROPE, Region.EUROPE_MEDITERRANEENNE);
            case 8 -> new ProductSquare("Europe Danubienne", position, Continent.EUROPE, Region.EUROPE_DANUBIENNE);
            case 9 -> new ProductSquare("Europe Balkanique", position, Continent.EUROPE, Region.EUROPE_BALKANIQUE);
            case 13, 14, 15 -> new ProductSquare("Russie et CEI", position, null, Region.RUSSIE_CEI);
            case 19 -> new ProductSquare("Canada", position, Continent.AMERICA, Region.CANADA);
            case 20 -> new ProductSquare("Mexique", position, Continent.AMERICA, Region.MEXIQUE);
            case 21, 25 -> new ProductSquare("Choix Amérique sauf USA", position, Continent.AMERICA, null);
            case 22 -> new ProductSquare("Antilles", position, Continent.AMERICA, Region.ANTILLES);
            case 23 -> new ProductSquare("Venezuela", position, Continent.AMERICA, Region.VENEZUELA);
            case 24 -> new ProductSquare("Pays Andins", position, Continent.AMERICA, Region.PAYS_ANDINS);
            case 26 -> new ProductSquare("Brésil", position, Continent.AMERICA, Region.BRESIL);
            case 27 -> new ProductSquare("Argentine", position, Continent.AMERICA, Region.ARGENTINE);
            case 31, 32, 33 -> new ProductSquare("USA", position, null, Region.USA);
            case 37 -> new ProductSquare("Maghreb", position, Continent.AFRICA, Region.MAGHREB);
            case 38 -> new ProductSquare("Afrique du Nord-Est", position, Continent.AFRICA, Region.AFRIQUE_NORD_EST);
            case 39 -> new ProductSquare("Afrique Occidentale", position, Continent.AFRICA, Region.AFRIQUE_OCCIDENTALE);
            case 40 -> new ProductSquare("Choix Afrique", position, Continent.AFRICA, null);
            case 41 -> new ProductSquare("Afrique Centrale", position, Continent.AFRICA, Region.AFRIQUE_CENTRALE);
            case 42 -> new ProductSquare("Afrique des Grands Lacs", position, Continent.AFRICA, Region.AFRIQUE_GRANDS_LACS);
            case 43 -> new ProductSquare("Afrique du Sud", position, Continent.AFRICA, Region.AFRIQUE_SUD);
            case 47, 61 -> new ProductSquare("Océanie", position, Continent.ASIA_OCEANIA, Region.OCEANIE);
            case 46, 60 -> new ProductSquare("Choix Mondial", position, null, null);
            case 51 -> new ProductSquare("Moyen-Orient", position, Continent.ASIA_OCEANIA, Region.MOYEN_ORIENT);
            case 52 -> new ProductSquare("Péninsule Indienne", position, Continent.ASIA_OCEANIA, Region.PENINSULE_INDIENNE);
            case 53 -> new ProductSquare("Péninsule Indochinoise", position, Continent.ASIA_OCEANIA, Region.PENINSULE_INDOCHINOISE);
            case 54 -> new ProductSquare("Choix Asie-Océanie", position, Continent.ASIA_OCEANIA, null);
            case 55 -> new ProductSquare("Japon", position, Continent.ASIA_OCEANIA, Region.JAPON);
            case 56 -> new ProductSquare("Chine", position, Continent.ASIA_OCEANIA, Region.CHINE);
            case 57 -> new ProductSquare("Indonésie", position, Continent.ASIA_OCEANIA, Region.INDONESIE);
            default -> throw new IllegalArgumentException("Position " + position + " n'est pas une propriété");
        };
    }
}
