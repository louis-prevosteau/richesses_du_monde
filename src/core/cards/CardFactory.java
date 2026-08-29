package core.cards;

import core.enums.CardType;
import core.enums.Resource;

public class CardFactory {

    public static CardDeck createNewsDeck() {
        return builder()
                .addReceiveCard(
                        "Excellente récolte de cahoutchouc naturel. Recevez 4 millions si vous possédez du caoutchouc naturel. Sinon recevez 2 millions.",
                        Resource.CAOUTCHOUC_NATUREL,
                        new int[]{4000000, 2000000})
                .addReceiveCard(
                        "Extended Boom : La Bethleem Steel et la United Steel sont bénéficiares. Recevez 9 millions si vous possédez de l'acier. Sinon recevez 7 millions.",
                        Resource.ACIER,
                        new int[]{9000000, 7000000})
                .addReceiveCard(
                        "Grand succès de courses automobiles. Votre marque est toujours en tête. Recevez 6 millions si vous possédez de la construction automobile. Sinon recevez 4 millions.",
                        Resource.CONSTRUCTION_AUTOMOBILE,
                        new int[]{6000000, 4000000})
                .addReceiveCard(
                        "Tensions internationales : bond en avant de la très fluctuante extraction des oxydes d'uranium. Recevez 6 millions si vous possédez de l'uranium. Sinon recevez 4 millions.",
                        Resource.URANIUM,
                        new int[]{6000000, 4000000})
                .addReceiveCard(
                        "Splendides récoltes de blé. Recevez 7 millions si vous possédez du blé. Sinon recevez 5 millions.",
                        Resource.BLE,
                        new int[]{7000000, 5000000})
                .addReceiveCard(
                        "Votre entreprise de plongée sous-marine découvre une galère antique contenant un important trésor. Cela vous rapporte 3 milllions.",
                        null,
                        new int[]{3000000})
                .addReceiveCard(
                        "Vous revendez vos terrains d'Afrique du Sud sur lesquelles on a découvert un gisement d'uranium. Encaissez 5 milllions.",
                        null,
                        new int[]{5000000})
                .addReceiveCard(
                        "Vous héritez d'un oncle d'Amérique qui a découvert du pétrole dans son champ du Texas. Vous recevez 6 milllions.",
                        null,
                        new int[]{6000000})
                .addReceiveCard(
                        "Votre dernier livre est couronné d'un prix littéraire. Le public se l'arrache. Vos droits d'auteur vous rapporte 4 millions.",
                        null,
                        new int[]{4000000})
                .addGiftCard("Vous fêtez vos noces de diamant. Chaque joueur est un ami qui vous donne 1 million.",1000000)
                .addPayCard(
                        "Incidents en RD Congo : les réserves de cuivre du Katanga sont inexploités. Payez 6 milions si vous possédez du cuivre. Sinon payez 4 millions.",
                        Resource.CUIVRE,
                        new int[] {6000000, 4000000})
                .addPayCard(
                        "Un violent cyclone fait rage dans le sud des Etats-Unis. Des champs de coton brîûlent. Payez 5 milions si vous possédez du coton. Sinon payez 3 millions.",
                        Resource.COTON_BRUT,
                        new int[] {5000000, 3000000})
                .addPayCard(
                        "Afflux de capitaux en Europe. L'or s'affaisse. Payez 7 milions si vous possédez de l'or. Sinon payez 5 millions.",
                        Resource.OR,
                        new int[] {7000000, 5000000})
                .addPayCard(
                        "Surproduction de café. Payez 4 milions si vous possédez du café. Sinon payez 2 millions.",
                        Resource.CAFE,
                        new int[] {4000000, 2000000})
                .addPayCard(
                        "Un tremblement de terre détruit les chantiers navales d'Osaka, Kobe et Yokohama. Payez 5 milions si vous possédez de la construction navales. Sinon payez 3 millions.",
                        Resource.CONSTRUCTION_NAVALE,
                        new int[] {5000000, 3000000})
                .addPayCard(
                        "Votre installation d'extraction de minerai de fer ne présenterait pas les normes de sécurité exigées. Un accident est survenu. Payez une amende 7 milions si vous possédez du fer.",
                        Resource.FER,
                        new int[] {7000000, 0})
                .addPayCard(
                        "À la suite d'un encombrement sur l'autoroute, vous étiez en retard pour prendre votre avion. Dans la précipitation, vous égarez votre serviette contenant 3 millions. Payez à la banque.",
                        null,
                        new int[] {3000000})
                .addPayCard(
                        "Une de vos usines brûle. Vous étiez mal assuré. Payez 1 million pour la rééquiper.",
                        null,
                        new int[] {1000000})
                .build();

    }

    public static CardDeck createJokerDeck() {
        CardDeckBuilder builder = builder();
        for (int i = 0; i < 9 ; i++) {
            builder.addJokerCard(
                    "Joker : Evitez les enchères. Valeur : 3 000 000 €"
            );
        }
        return builder.build();
    }

    public static CardDeckBuilder builder() {
        return new CardDeckBuilder();
    }

    public static class CardDeckBuilder {
        private final CardDeck deck;

        public CardDeckBuilder() {
            this.deck = new CardDeck();
        }

        public CardDeckBuilder addPayCard(String description, Resource resource, int[] amounts) {
            deck.addCard(new PayCard(description, CardType.NEWS, amounts, resource));
            return this;
        }

        public CardDeckBuilder addReceiveCard(String description, Resource resource, int[] amounts) {
            deck.addCard(new ReceiveCard(description, CardType.NEWS, amounts, resource));
            return this;
        }

        public CardDeckBuilder addGiftCard(String description, int amountPerPlayer) {
            deck.addCard(new GiftCard(description, CardType.NEWS, amountPerPlayer));
            return this;
        }

        public void addJokerCard(String description) {
            deck.addCard(new JokerCard(description, CardType.JOKER));
        }

        public CardDeck build() {
            deck.shuffle();
            return deck;
        }
    }
}
