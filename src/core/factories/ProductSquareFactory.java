package core.factories;

import core.enums.Continent;
import core.enums.Region;
import core.models.ISquare;
import core.models.ProductSquare;

public class ProductSquareFactory extends SquareFactory {

    @Override
    public ISquare createSquare(int position) {

        return switch (position) {

            case 1 -> square("Scandinavie", position, Continent.EUROPE, Region.SCANDINAVIE);
            case 2 -> square("Allemagne", position, Continent.EUROPE, Region.ALLEMAGNE);
            case 3, 7 -> europeChoice(position);
            case 4 -> square("Royaume-Uni", position, Continent.EUROPE, Region.ROYAUME_UNI);
            case 5 -> square("France", position, Continent.EUROPE, Region.FRANCE);
            case 6 -> square("Europe Méditerranéenne", position, Continent.EUROPE, Region.EUROPE_MEDITERRANEENNE);
            case 8 -> square("Europe Danubienne", position, Continent.EUROPE, Region.EUROPE_DANUBIENNE);
            case 9 -> square("Europe Balkanique", position, Continent.EUROPE, Region.EUROPE_BALKANIQUE);
            case 13, 14, 15 -> russia(position);

            case 19 -> square("Canada", position, Continent.AMERICA, Region.CANADA);
            case 20 -> square("Mexique", position, Continent.AMERICA, Region.MEXIQUE);
            case 21, 25 -> americaChoice(position);
            case 22 -> square("Antilles", position, Continent.AMERICA, Region.ANTILLES);
            case 23 -> square("Venezuela", position, Continent.AMERICA, Region.VENEZUELA);
            case 24 -> square("Pays Andins", position, Continent.AMERICA, Region.PAYS_ANDINS);
            case 26 -> square("Brésil", position, Continent.AMERICA, Region.BRESIL);
            case 27 -> square("Argentine", position, Continent.AMERICA, Region.ARGENTINE);
            case 31, 32, 33 -> usa(position);

            case 37 -> square("Maghreb", position, Continent.AFRICA, Region.MAGHREB);
            case 38 -> square("Afrique du Nord-Est", position, Continent.AFRICA, Region.AFRIQUE_NORD_EST);
            case 39 -> square("Afrique Occidentale", position, Continent.AFRICA, Region.AFRIQUE_OCCIDENTALE);
            case 40 -> africaChoice(position);
            case 41 -> square("Afrique Centrale", position, Continent.AFRICA, Region.AFRIQUE_CENTRALE);
            case 42 -> square("Afrique des Grands Lacs", position, Continent.AFRICA, Region.AFRIQUE_GRANDS_LACS);
            case 43 -> square("Afrique du Sud", position, Continent.AFRICA, Region.AFRIQUE_SUD);

            case 46, 60 -> worldChoice(position);
            case 47, 61 -> oceania(position);
            case 51 -> square("Moyen-Orient", position, Continent.ASIA_OCEANIA, Region.MOYEN_ORIENT);
            case 52 -> square("Péninsule Indienne", position, Continent.ASIA_OCEANIA, Region.PENINSULE_INDIENNE);
            case 53 -> square("Péninsule Indochinoise", position, Continent.ASIA_OCEANIA, Region.PENINSULE_INDOCHINOISE);
            case 54 -> asiaChoice(position);
            case 55 -> square("Japon", position, Continent.ASIA_OCEANIA, Region.JAPON);
            case 56 -> square("Chine", position, Continent.ASIA_OCEANIA, Region.CHINE);
            case 57 -> square("Indonésie", position, Continent.ASIA_OCEANIA, Region.INDONESIE);

            default -> throw new IllegalArgumentException(
                    "Position " + position + " n'est pas une propriété"
            );
        };
    }

    private ProductSquare square(
            String name,
            int position,
            Continent continent,
            Region region
    ) {
        return new ProductSquare(name, position, continent, region);
    }

    private ProductSquare europeChoice(int position) {
        return square(
                "Choix Europe sauf Russie et CEI",
                position,
                Continent.EUROPE,
                null
        );
    }

    private ProductSquare americaChoice(int position) {
        return square(
                "Choix Amérique sauf USA",
                position,
                Continent.AMERICA,
                null
        );
    }

    private ProductSquare africaChoice(int position) {
        return square(
                "Choix Afrique",
                position,
                Continent.AFRICA,
                null
        );
    }

    private ProductSquare asiaChoice(int position) {
        return square(
                "Choix Asie-Océanie",
                position,
                Continent.ASIA_OCEANIA,
                null
        );
    }

    private ProductSquare worldChoice(int position) {
        return square(
                "Choix Mondial",
                position,
                null,
                null
        );
    }

    private ProductSquare russia(int position) {
        return square(
                "Russie et CEI",
                position,
                null,
                Region.RUSSIE_CEI
        );
    }

    private ProductSquare usa(int position) {
        return square(
                "USA",
                position,
                null,
                Region.USA
        );
    }

    private ProductSquare oceania(int position) {
        return square(
                "Océanie",
                position,
                Continent.ASIA_OCEANIA,
                Region.OCEANIE
        );
    }
}