package test.products;

import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;
import core.products.IProduct;
import core.products.Product;
import core.products.Shop;
import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {

    private Shop shop;
    private IProduct qatar, france, usa;

    @BeforeEach()
    void setUp() {
        shop = new Shop();
        qatar = new Product(
                Resource.PETROLE,
                15,
                42,
                Continent.ASIA_OCEANIA,
                Region.MOYEN_ORIENT,
                "Qatar"
        );

        france = new Product(
                Resource.BLE,
                20,
                50,
                Continent.EUROPE,
                Region.FRANCE,
                "France"
        );

        usa = new Product(
                Resource.GAZ_NATUREL,
                25,
                60,
                Continent.AMERICA,
                Region.USA,
                "USA"
        );
        shop.addProduct(qatar);
        shop.addProduct(france);
        shop.addProduct(usa);
    }

    @Test()
    @DisplayName("Le magasin doit contenir le produit ajouté, à la bonne région.")
    void testShopContainsProductAdded() {
        IProduct product = new Product(Resource.PETROLE, 15, 42, Continent.ASIA_OCEANIA, Region.MOYEN_ORIENT, "Qatar");
        shop.addProduct(product);
        assertTrue(shop.getProducts(null, null).getOrDefault(Region.MOYEN_ORIENT, Collections.emptyList()).contains(product));
        assertTrue(shop.getProducts(null, null).containsKey(product.getRegion()));
    }

    @Test()
    @DisplayName("returnsProduct remet un titre dans le magasin.")
    void testReturnsProductShopContainsProductReturned() {
        shop.returnsProducts(qatar);
        assertTrue(shop.getProducts(null, null).getOrDefault(Region.MOYEN_ORIENT, Collections.emptyList()).contains(qatar));
        assertTrue(shop.getProducts(null, null).containsKey(qatar.getRegion()));
    }

    @Test
    @DisplayName("getProducts(null, null) retourne tous les produits")
    void getProductsWithoutFilterReturnsAllProducts() {
        Map<Region, List<IProduct>> result = shop.getProducts(null, null);

        assertEquals(3,
                result.values().stream().mapToInt(List::size).sum());

        assertTrue(result.get(Region.MOYEN_ORIENT).contains(qatar));
        assertTrue(result.get(Region.FRANCE).contains(france));
        assertTrue(result.get(Region.USA).contains(usa));
    }

    @Test
    @DisplayName("getProducts(continent, null) filtre sur le continent")
    void getProductsByContinentReturnsOnlyMatchingProducts() {
        Map<Region, List<IProduct>> result =
                shop.getProducts(Continent.ASIA_OCEANIA, null);

        assertEquals(1, result.size());
        assertTrue(result.containsKey(Region.MOYEN_ORIENT));
        assertTrue(result.get(Region.MOYEN_ORIENT).contains(qatar));

        assertFalse(result.containsKey(Region.FRANCE));
        assertFalse(result.containsKey(Region.USA));
    }

    @Test
    @DisplayName("getProducts(null, region) filtre sur la région")
    void getProductsByRegionReturnsOnlyMatchingProducts() {
        Map<Region, List<IProduct>> result =
                shop.getProducts(null, Region.FRANCE);

        assertEquals(1, result.size());
        assertTrue(result.containsKey(Region.FRANCE));
        assertTrue(result.get(Region.FRANCE).contains(france));
    }

    @Test
    @DisplayName("getProducts retourne une map vide si aucun produit ne correspond au continent")
    void getProductsByUnknownContinentReturnsEmptyMap() {
        Map<Region, List<IProduct>> result =
                shop.getProducts(Continent.AFRICA, null);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("getProducts retourne une map vide si aucun produit ne correspond à la région")
    void getProductsByUnknownRegionReturnsEmptyMap() {
        Map<Region, List<IProduct>> result =
                shop.getProducts(null, Region.AFRIQUE_CENTRALE);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Le filtre continent est prioritaire sur le filtre région")
    void continentFilterHasPriorityOverRegionFilter() {
        Map<Region, List<IProduct>> result =
                shop.getProducts(Continent.ASIA_OCEANIA, Region.FRANCE);

        assertEquals(1, result.size());
        assertTrue(result.containsKey(Region.MOYEN_ORIENT));
        assertFalse(result.containsKey(Region.FRANCE));
    }
}
