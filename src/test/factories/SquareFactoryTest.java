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
        square = factory.createSquare(1);
        assertInstanceOf(ProductSquare.class, square);
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
