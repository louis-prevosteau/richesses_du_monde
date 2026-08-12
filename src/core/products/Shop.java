package core.products;

import core.enums.Continent;
import core.enums.Region;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {

    private Map<Region, List<IProduct>> products;

    public Shop() {
        this.products = new HashMap<>();
    }

    public Map<Region, List<IProduct>> getProducts(Continent continent, Region region) {
        return products;
    }

    public void addProduct(IProduct product) {}

    public void returnsProducts(IProduct product) {}
}
