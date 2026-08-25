package test.factories;

import core.enums.CardType;
import core.factories.*;
import core.models.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class SquareFactoryTest {

    private SquareFactory factory;
    private ISquare square;

    @Test()
    @DisplayName("GoSquareFactory doit créer une instance de GoSquare")
    void testGoSquareFactoryCreateGoSquareInstance() {
        factory = new GoSquareFactory();
        square = factory.createSquare(0);
        assertInstanceOf(GoSquare.class, square);
    }

    @Test()
    @DisplayName("CardSquareFactory doit créer une instance de CardSquare")
    void testCardSquareFactoryCreateCardSquareInstance() {
        factory = new CardSquareFactory(CardType.NEWS);
        square = factory.createSquare(0);
        assertInstanceOf(CardSquare.class, square);
    }

    @Test()
    @DisplayName("CollectSquareFactory doit créer une instance de CollectSquare")
    void testCollectSquareFactoryCreateCollectSquareInstance() {
        factory = new CollectSquareFactory();
        square = factory.createSquare(0);
        assertInstanceOf(CollectSquare.class, square);
    }

    @Test()
    @DisplayName("ProductSquareFactory doit créer une instance de ProductSquare")
    void testProductSquareFactoryCreateProductSquareInstance() {
        factory = new ProductSquareFactory();
        int[] positions = {1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 19, 20, 21, 22, 23, 24, 25, 26, 27, 31, 32, 33, 37, 38, 39, 40, 41, 42, 43, 46, 47, 49, 50, 51, 52, 53, 54, 55, 60, 61};
        for (int p : positions) {
            square = factory.createSquare(p);
            assertInstanceOf(ProductSquare.class, square);
        }
    }

    @Test()
    @DisplayName("ProductSquareFactory doit lever une IllegalArgumentException pour la position 0")
    void testProductSquareFactoryThrowsExceptionForPosition0() {
        factory = new ProductSquareFactory();
        assertThrows(IllegalArgumentException.class, () -> factory.createSquare(0));
    }

    @Test()
    @DisplayName("SaleSquareFactory doit créer une instance de SaleSquare")
    void testSaleSquareFactoryCreateSaleSquareInstance() {
        factory = new SaleSquareFactory();
        square = factory.createSquare(0);
        assertInstanceOf(SaleSquare.class, square);
    }
}
