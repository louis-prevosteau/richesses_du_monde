package core.products;

import core.enums.Continent;
import core.enums.Region;
import core.enums.Resource;

public class ProductFactory {

    public static Shop createShop() { return null; }

    public ShopBuilder builder() { return null; }

    public static class ShopBuilder {

        private Shop shop;

        public ShopBuilder() {
        }

        public ShopBuilder addProduct(Resource resource, int percentage, int price, Continent continent, Region region, String country) { return null; }
    }
}
