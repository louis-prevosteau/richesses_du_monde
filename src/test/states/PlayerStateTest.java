package test.states;

import core.models.Player;
import core.states.BankruptState;
import core.states.IPlayerState;
import core.states.NormalState;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerStateTest {

    private Player player;

    @BeforeEach()
    void setUp() {
        player = new Player("Alice");
    }

    @Test
    @DisplayName("Un joueur devrait commencer dans l'état Normal")
    void testInitialState() {
        assertEquals("Normal", player.getState().getName());
    }

    @Test
    @DisplayName("NormalState devrait permettre d'acheter des produits")
    void testNormalStateCanBuy() {
        IPlayerState normalState = new NormalState();
        assertTrue(normalState.canBuy());
        assertTrue(normalState.canPayRoyalties());
    }

    @Test
    @DisplayName("BankruptState ne devrait rien permettre")
    void testBankruptStateCannotDoAnything() {
        IPlayerState bankruptState = new BankruptState();
        assertFalse(bankruptState.canBuy());
        assertFalse(bankruptState.canPayRoyalties());
    }

    @Test
    @DisplayName("La transition vers BankruptState devrait être possible")
    void testTransitionToBankrupt() {
        player.declareBankruptcy();
        assertEquals("Faillite", player.getState().getName());
    }

    @Test
    @DisplayName("L'état devrait affecter les capacités du joueur")
    void testStateAffectsCapabilities() {
        assertTrue(player.getState().canBuy());
        player.setState(new BankruptState());
        assertFalse(player.getState().canBuy());
        assertFalse(player.getState().canPayRoyalties());
    }
}
