package test.products;

import core.enums.Resource;
import core.products.IProduct;
import core.products.ProductFactory;
import core.products.Shop;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ProductFactoryTest {

    @Test()
    @DisplayName("createShop doit retourner une instance de Shop.")
    void testCreateShopReturnsShopInstance() {
        assertInstanceOf(Shop.class, ProductFactory.createShop());
    }

    @Test()
    @DisplayName("Toutes les ressources du magasin doivent être supérieur à 90 %")
    void testAllResourcesPercentage100AtInitialization() {
        Shop shop = ProductFactory.createShop();
        Map<Resource, Integer> percentagesByResource =
                shop.getProducts(null, null)
                        .values()
                        .stream()
                        .flatMap(List::stream)
                        .collect(Collectors.groupingBy(
                                IProduct::getResource,
                                Collectors.summingInt(IProduct::getPercentage)
                        ));
        percentagesByResource.forEach((resource, total) ->
                assertTrue(
                        total >= 90
                ));
    }
}
