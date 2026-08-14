package test.products;

import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.products.IProduct;
import core.products.Product;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private IProduct product;

    @BeforeEach()
    void setUp() {
        product = new Product(Resource.PETROLE, 15, 42, Continent.ASIA_OCEANIA, Region.MOYEN_ORIENT, "Qatar");
    }

    @Test()
    @DisplayName("Lors de l'initialisation, un produit n'a pas de propriétaire")
    void testProductHasNotOwnerWhenInitialized() {
        assertNull(product.getOwner());
    }
}
