package test.models;

import core.models.Board;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach()
    void setUp() {
        board = new Board();
    }

    @Test
    @DisplayName("Un board devrait avoir 65 cases")
    void testBoardSize() {
        assertEquals(65, board.getSize());
    }
}
